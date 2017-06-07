package com.soapboxrace.core.bo;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.soapboxrace.core.api.util.Config;
import com.soapboxrace.core.dao.RecoveryPasswordDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.RecoveryPasswordEntity;
import com.soapboxrace.core.jpa.UserEntity;

import org.apache.commons.codec.digest.DigestUtils;

@Stateless
public class RecoveryPasswordBO {
	
	@EJB
	private RecoveryPasswordDAO recoveryPasswordDao;
	
	@EJB
	private UserDAO userDao;
	
	@Resource(mappedName = "java:jboss/mail/Gmail")
	private Session mailSession;
	
	public String sendRecoveryPassword(String password, String passwordconf, String randomKey) {
		RecoveryPasswordEntity recoveryPasswordEntity = recoveryPasswordDao.findByRandomKey(randomKey);
		if(recoveryPasswordEntity == null || randomKey == null || randomKey.isEmpty())
			return "";
		
		if(password.isEmpty() || passwordconf.isEmpty())
			return "Be sure to all field are valid";
		
		if(!password.equals(passwordconf))
			return "Password typed are not equals to the confirm password";
		
		Long currentTime = new Date().getTime();
		Long recoveryTime = recoveryPasswordEntity.getExpirationDate().getTime();
		if(recoveryPasswordEntity.getIsClose() || currentTime > recoveryTime)
			return "This ticket is closed or no longer available";
		
		UserEntity userEntity = userDao.findById(recoveryPasswordEntity.getUserId());
		userEntity.setPassword(DigestUtils.sha1Hex(password));
		userDao.update(userEntity);
		
		recoveryPasswordEntity.setIsClose(true);
		recoveryPasswordDao.update(recoveryPasswordEntity);
		
		return "Password successfully changed with key[" + randomKey + "] for eMail[" + userEntity.getEmail() + "] new password[" + DigestUtils.sha1Hex(password) + "]";
	}
	
	public String createRecoveryPassword(Long userId) {
		UserEntity userEntity = userDao.findById(userId);
		if(userEntity == null)
			return "";
		
		List<RecoveryPasswordEntity> listRecoveryPasswordEntity = recoveryPasswordDao.findAllByUserId(userEntity.getId());
		for(RecoveryPasswordEntity entity : listRecoveryPasswordEntity) {
			if(!entity.getIsClose())
				return "Already existing";
		}
		
		String randomKey = createSecretKey(userEntity.getId());
		if(!sendEmail(randomKey, userEntity))
			return "A problem has occured with sending email, please be sure your email are valid !";
		
		RecoveryPasswordEntity recoveryPasswordEntity = new RecoveryPasswordEntity();
		recoveryPasswordEntity.setRandomKey(randomKey);
		recoveryPasswordEntity.setExpirationDate(getHours(24));
		recoveryPasswordEntity.setUserId(userEntity.getId());
		recoveryPasswordEntity.setIsClose(false);
		recoveryPasswordDao.insert(recoveryPasswordEntity);
		
		return "Recovery Password are successfully created for userId[" + userEntity.getId() + "]";
	}
	
	private String createSecretKey(Long userId) {
		return (Long.toHexString(Double.doubleToLongBits(Math.random())) + userId.toString());
	}
	
	private Boolean sendEmail(String randomKey, UserEntity userEntity) {
		try {
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(Config.getEmailFrom()));
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(userEntity.getEmail()));
			message.setSubject("Recovery Password Email");
			message.setText(Config.getServerAddress() + "/soapbox-race-core/password.jsp?randomKey=" + randomKey);
			Transport.send(message);
			return true;
		} catch(MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
	}
	
	private Date getHours(int hours) {
		Long time = new Date().getTime();
		time += hours * 60000 * 60;
		return new Date(time);
	}

}
