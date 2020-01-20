/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

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
