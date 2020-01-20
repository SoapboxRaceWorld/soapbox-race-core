/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.LoginAnnouncementEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class LoginAnnouncementDAO extends BaseDAO<LoginAnnouncementEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<LoginAnnouncementEntity> findAll() {
        TypedQuery<LoginAnnouncementEntity> query = entityManager.createNamedQuery("LoginAnnouncementEntity.findAll",
                LoginAnnouncementEntity.class);
        return query.getResultList();
    }
}
