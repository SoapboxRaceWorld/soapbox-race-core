package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.UserDAO;
import com.soapboxrace.core.jpa.UserEntity;

@Stateless
public class UserBO {

	@EJB
	private UserDAO dao;

	public void createUser(String email, String passwd) {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(email);
		userEntity.setPassword(passwd);
		dao.insert(userEntity);
	}

	public void selectAll() {
		dao.selectAll();
	}
}
