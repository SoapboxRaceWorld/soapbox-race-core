package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.api.util.BanUtil;
import com.soapboxrace.core.dao.BanDAO;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.login.LoginStatusVO;

@Stateless
public class AuthenticationBO {
	@EJB
	private BanDAO banDAO;

	public BanEntity checkUserBan(UserEntity userEntity) {
		return banDAO.findByUser(userEntity);
	}
}
