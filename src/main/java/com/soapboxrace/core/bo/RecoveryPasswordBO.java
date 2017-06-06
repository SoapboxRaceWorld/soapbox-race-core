package com.soapboxrace.core.bo;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
	
	public String sendRecoveryPassword(String password, String passwordconf, String randomKey) {
		RecoveryPasswordEntity recoveryPasswordEntity = recoveryPasswordDao.findByRandomKey(randomKey);
		if(recoveryPasswordEntity == null || randomKey == null || randomKey.isEmpty())
			return "";
		
		if(password.isEmpty() || passwordconf.isEmpty())
			return "Be sure to all field are valid";
		
		if(!password.equals(passwordconf))
			return "Password typed are not equals to the confirm password";
		
		UserEntity userEntity = userDao.findById(recoveryPasswordEntity.getUserId());
		userEntity.setPassword(DigestUtils.sha1Hex(password));
		userDao.update(userEntity);
		
		return "Password successfully changed with key[" + randomKey + "] for eMail[" + userEntity.getEmail() + "] new password[" + DigestUtils.sha1Hex(password) + "]";
	}
	
	public String createRecoveryPassword(Long userId) {
		UserEntity userEntity = userDao.findById(userId);
		if(userEntity == null)
			return "";
		
		RecoveryPasswordEntity recoveryPasswordEntity = recoveryPasswordDao.findByUserId(userEntity.getId());
		if(recoveryPasswordEntity != null)
			return "Already existing";
		
		recoveryPasswordEntity = new RecoveryPasswordEntity();
		recoveryPasswordEntity.setRandomKey(createSecretKey(userEntity.getId()));
		recoveryPasswordEntity.setTime(60);
		recoveryPasswordEntity.setUserId(userEntity.getId());
		recoveryPasswordDao.insert(recoveryPasswordEntity);
		
		//Send email
		sendEmail(recoveryPasswordEntity.getRandomKey(), userEntity);
		
		return "Recovery Password are successfully created for userId[" + userEntity.getId() + "]";
	}
	
	private String createSecretKey(Long userId) {
		return (Long.toHexString(Double.doubleToLongBits(Math.random())) + userId.toString());
	}
	
	private void sendEmail(String randomKey, UserEntity userEntity) {
		// Assuming you are sending email from localhost
		final String username = "user@gmail.com";// Change Username Here
		final String password = "password"; // Change Password Here
		String from = "from@gmail.com"; // Change From Mail ID
		String to = userEntity.getEmail();// Change To Mail ID
		
		// Get system properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		// Get the default Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
    			});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			message.setSubject("Recovery Password Email");
			message.setText("http://localhost:8080/soapbox-race-core/password.jsp?randomKey=" + randomKey);
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
