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

	public LoginStatusVO login(String email, String password) {
		UserEntity userEntity = userDAO.findByEmail(email);
		LoginStatusVO loginStatusVO = new LoginStatusVO(0L, "", false);
		if (password.equals(userEntity.getPassword())) {
			TokenSessionEntity tokenSessionEntity = new TokenSessionEntity();
			long time = new Date().getTime();
			time = time + (15 * 60000);
			Date date = new Date(time);
			tokenSessionEntity.setExpirationDate(date);
			String randomUUID = UUIDGen.getRandomUUID();
			tokenSessionEntity.setSecurityToken(randomUUID);
			tokenDAO.insert(tokenSessionEntity);
			loginStatusVO = new LoginStatusVO(1L, randomUUID, true);
			return loginStatusVO;
		}
		loginStatusVO.setDescription("LOGIN ERROR");
		return loginStatusVO;
	}

}
