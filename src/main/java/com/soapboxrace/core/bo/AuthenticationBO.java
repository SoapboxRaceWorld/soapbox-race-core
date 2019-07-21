package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.BanDAO;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.UserEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AuthenticationBO {
    @EJB
    private BanDAO banDAO;

    public BanEntity checkUserBan(UserEntity userEntity) {
        return banDAO.findByUser(userEntity);
    }
}
