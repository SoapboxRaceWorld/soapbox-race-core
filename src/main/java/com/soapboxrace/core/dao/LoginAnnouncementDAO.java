/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.LoginAnnouncementEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class LoginAnnouncementDAO extends BaseDAO<LoginAnnouncementEntity, Integer> {

    @Override
    public LoginAnnouncementEntity find(Integer key) {
        return entityManager.find(LoginAnnouncementEntity.class, key);
    }

    public List<LoginAnnouncementEntity> findAll() {
        TypedQuery<LoginAnnouncementEntity> query = entityManager.createNamedQuery("LoginAnnouncementEntity.findAll",
                LoginAnnouncementEntity.class);
        return query.getResultList();
    }

    public List<LoginAnnouncementEntity> findAllByLanguage(String language) {
        TypedQuery<LoginAnnouncementEntity> query = entityManager.createNamedQuery("LoginAnnouncementEntity.findAllByLanguage",
                LoginAnnouncementEntity.class);
        query.setParameter("language", language);
        return query.getResultList();
    }
}
