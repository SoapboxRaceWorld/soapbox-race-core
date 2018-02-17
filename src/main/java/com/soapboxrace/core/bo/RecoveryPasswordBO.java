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

import org.apache.commons.codec.digest.DigestUtils;

import com.soapboxrace.core.dao.RecoveryPasswordDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.RecoveryPasswordEntity;
import com.soapboxrace.core.jpa.UserEntity;

@Stateless
public class RecoveryPasswordBO {

	@EJB
	private RecoveryPasswordDAO recoveryPasswordDao;

	@EJB
	private UserDAO userDao;

	@EJB
	private ParameterBO parameterBO;

	@Resource(mappedName = "java:jboss/mail/Gmail")
	private Session mailSession;

	public String resetPassword(String password, String passwordconf, String randomKey) {
		RecoveryPasswordEntity recoveryPasswordEntity = recoveryPasswordDao.findByRandomKey(randomKey);
		if (recoveryPasswordEntity == null) {
			return "ERROR: invalid randomKey!";
		}

		Long currentTime = new Date().getTime();
		Long recoveryTime = recoveryPasswordEntity.getExpirationDate().getTime();
		if (recoveryPasswordEntity.getIsClose() || currentTime > recoveryTime) {
			return "ERROR: randomKey expired";
		}

		UserEntity userEntity = userDao.findById(recoveryPasswordEntity.getUserId());
		userEntity.setPassword(DigestUtils.sha1Hex(password));
		userDao.update(userEntity);

		recoveryPasswordEntity.setIsClose(true);
		recoveryPasswordDao.update(recoveryPasswordEntity);

		return "Password changed!";
	}

	private String createRecoveryPassword(UserEntity userEntity) {
		List<RecoveryPasswordEntity> listRecoveryPasswordEntity = recoveryPasswordDao.findAllOpenByUserId(userEntity.getId());
		if (!listRecoveryPasswordEntity.isEmpty()) {
			return "ERROR: Recovery password link already sent, please check your spam mail box or try again in 1 hour.";
		}

		String randomKey = createSecretKey(userEntity.getId());
		if (!sendEmail(randomKey, userEntity)) {
			return "ERROR: Can't send email!";
		}

		RecoveryPasswordEntity recoveryPasswordEntity = new RecoveryPasswordEntity();
		recoveryPasswordEntity.setRandomKey(randomKey);
		recoveryPasswordEntity.setExpirationDate(getHours(1));
		recoveryPasswordEntity.setUserId(userEntity.getId());
		recoveryPasswordEntity.setIsClose(false);
		recoveryPasswordDao.insert(recoveryPasswordEntity);

		return "Link to reset password sent to: [" + userEntity.getEmail() + "]";
	}

	private String createSecretKey(Long userId) {
		return (Long.toHexString(Double.doubleToLongBits(Math.random())) + userId.toString());
	}

	private Boolean sendEmail(String randomKey, UserEntity userEntity) {
		try {
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(parameterBO.getStrParam("EMAIL_FROM")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEntity.getEmail()));
			message.setSubject("Recovery Password Email");
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Dear user,\n\n");
			stringBuilder.append("Someone requested to recover forgotten password in our soapbox race world server.\n\n");
			stringBuilder.append("If wasn't you, just ignore this email.\n\n");
			stringBuilder.append("You can click this link to reset your password:\n\n");
			stringBuilder.append(parameterBO.getStrParam("SERVER_ADDRESS"));
			stringBuilder.append("/soapbox-race-core/password.jsp?randomKey=");
			stringBuilder.append(randomKey);
			stringBuilder.append("\n\nThanks for playing!\n\n");
			stringBuilder.append("\n\nSBRW Team.\n");
			message.setText(stringBuilder.toString());
			Transport.send(message);
			return true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
	}

	private Date getHours(int hours) {
		Long time = new Date().getTime();
		time += hours * 60000 * 60;
		return new Date(time);
	}

	public String forgotPassword(String email) {
		UserEntity userEntity = userDao.findByEmail(email);
		if (userEntity == null) {
			return "ERROR: Invalid email!";
		}
		return createRecoveryPassword(userEntity);
	}

}
