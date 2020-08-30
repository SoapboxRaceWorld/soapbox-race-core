/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserDAO extends LongKeyedDAO<UserEntity> {

    public UserDAO() {
        super(UserEntity.class);
    }

    public UserEntity findByEmail(String email) {
        TypedQuery<UserEntity> query = entityManager.createNamedQuery("UserEntity.findByEmail", UserEntity.class);
        query.setParameter("email", email);

        List<UserEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

    public Long countUsers() {
        return entityManager.createNamedQuery("UserEntity.countUsers", Long.class).getSingleResult();
    }
}
