package com.soapboxrace.core.bo;

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
import com.soapboxrace.core.dao.PromoCodeDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.PromoCodeEntity;
import com.soapboxrace.core.jpa.UserEntity;

import org.apache.commons.codec.digest.DigestUtils;

@Stateless
public class PromoCodeBO {

	@EJB
	private PromoCodeDAO promoCodeDao;
	
	@EJB
	private UserDAO userDao;
	
	@Resource(mappedName = "java:jboss/mail/Gmail")
	private Session mailSession;
	
	public String createPromoCode(Long userId, Boolean isPremium) {
		UserEntity userEntity = userDao.findById(userId);
		if(userEntity == null)
			return "";
		
		if(isPremium && userEntity.isPremium())
			return "Already premium";
		
		String promoCode = createSecretKey(userEntity.getId(), isPremium);
		if(!sendEmail(promoCode, userEntity))
			return "A problem has occured with sending email, please be sure your email are valid !";
		
		PromoCodeEntity promoCodeEntity = new PromoCodeEntity();
		promoCodeEntity.setIsUsed(false);
		promoCodeEntity.setPromoCode(promoCode);
		promoCodeEntity.setUser(userEntity);
		promoCodeDao.insert(promoCodeEntity);
		
		return "PromoCode [" + promoCode + "] are successfully created for user [" + userEntity.getEmail() + "]";
	}
	
	public String sendPromoCode(String promoCode, String email, String password) {
		PromoCodeEntity promoCodeEntity = promoCodeDao.findByCode(promoCode);
		if(promoCodeEntity == null)
			return "";
		
		UserEntity userEntity = userDao.findById(promoCodeEntity.getUser().getId());
		if(userEntity == null)
			return "";
		
		if(promoCodeEntity.getIsUsed())
			return "Promo code already used";
		
		if(promoCode.isEmpty() || email.isEmpty() || password.isEmpty())
			return "Be sure to all field are valid";
		
		if(!promoCodeEntity.getUser().getEmail().equals(email) || !promoCodeEntity.getUser().getPassword().equals(DigestUtils.sha1Hex(password)))
			return "Wrong email or password";
		
		userEntity.setPremium(true);
		userDao.update(userEntity);
		
		promoCodeEntity.setIsUsed(true);
		promoCodeDao.update(promoCodeEntity);
		return "Enjoy with your premium account !";
	}
	
	private String createSecretKey(Long userId, Boolean isPremium) {
		String premium = isPremium ? "-PREMIUM-" : "-";
		String randomKey = (Long.toHexString(Double.doubleToLongBits(Math.random()))).toUpperCase();
		return  randomKey.substring(0, randomKey.length() / 2) + premium + randomKey.substring(randomKey.length() / 2) + "-" + userId.toString();
	}
	
	private Boolean sendEmail(String promoCode, UserEntity userEntity) {
		try {
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(Config.getEmailFrom()));
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(userEntity.getEmail()));
			message.setSubject("Recovery Password Email");
			message.setText(Config.getServerAddress() + "/soapbox-race-core/promo.jsp?promoCode=" + promoCode);
			Transport.send(message);
			return true;
		} catch(MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
	}

}
