package com.soapboxrace.core.bo;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.api.util.UUIDGen;
import com.soapboxrace.core.dao.TokenSessionDAO;
import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.TokenSessionEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.login.LoginStatusVO;

@Stateless
public class TokenSessionBO {

	@EJB
	private TokenSessionDAO tokenDAO;

	@EJB
	private UserDAO userDAO;

	public boolean verifyToken(Long userId, String securityToken) {
		TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
		if (tokenSessionEntity == null || !tokenSessionEntity.getUserId().equals(userId)) {
			return false;
		}
		long time = new Date().getTime();
		long tokenTime = tokenSessionEntity.getExpirationDate().getTime();
		if (time > tokenTime) {
			return false;
		}
		tokenSessionEntity.setExpirationDate(getMinutes(3));
		tokenDAO.update(tokenSessionEntity);
		return true;
	}

	public void updateToken(String securityToken) {
		TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
		Date expirationDate = getMinutes(3);
		tokenSessionEntity.setExpirationDate(expirationDate);
		tokenDAO.update(tokenSessionEntity);
	}

	public String createToken(Long userId) {
		TokenSessionEntity tokenSessionEntity = new TokenSessionEntity();
		Date expirationDate = getMinutes(15);
		tokenSessionEntity.setExpirationDate(expirationDate);
		String randomUUID = UUIDGen.getRandomUUID();
		tokenSessionEntity.setSecurityToken(randomUUID);
		tokenSessionEntity.setUserId(userId);
		tokenDAO.insert(tokenSessionEntity);
		return randomUUID;
	}

	public void deleteByUserId(Long userId) {
		tokenDAO.deleteByUserId(userId);
	}

	private Date getMinutes(int minutes) {
		long time = new Date().getTime();
		time = time + (minutes * 60000);
		Date date = new Date(time);
		return date;
	}

	public LoginStatusVO login(String email, String password) {
		LoginStatusVO loginStatusVO = new LoginStatusVO(0L, "", false);
		if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
			UserEntity userEntity = userDAO.findByEmail(email);
			if (password.equals(userEntity.getPassword())) {
				Long userId = userEntity.getId();
				deleteByUserId(userId);
				String randomUUID = createToken(userId);
				loginStatusVO = new LoginStatusVO(userId, randomUUID, true);
				return loginStatusVO;
			}
		}
		loginStatusVO.setDescription("LOGIN ERROR");
		return loginStatusVO;
	}

	public Long getActivePersonaId(String securityToken) {
		TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
		return tokenSessionEntity.getActivePersonaId();
	}

	public void setActivePersonaId(String securityToken, Long personaId) {
		TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
		tokenSessionEntity.setActivePersonaId(personaId);
		tokenDAO.update(tokenSessionEntity);
	}

	public String getActiveRelayCryptoTicket(String securityToken) {
		TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
		return tokenSessionEntity.getRelayCryptoTicket();
	}

}
