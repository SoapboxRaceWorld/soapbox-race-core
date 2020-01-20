/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class BanDAO extends BaseDAO<BanEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BanEntity findByUser(UserEntity userEntity) {
        TypedQuery<BanEntity> query = entityManager.createQuery("SELECT obj FROM BanEntity obj WHERE obj.userEntity =" +
                " :user AND (obj.willEnd = false OR obj.endsAt > :now)", BanEntity.class);
        query.setParameter("user", userEntity);
        query.setParameter("now", LocalDateTime.now());

        List<BanEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public void unbanUser(UserEntity userEntity) {
        Query createQuery = entityManager.createQuery("DELETE BanEntity obj WHERE obj.userEntity = :user");
        createQuery.setParameter("user", userEntity);
        createQuery.executeUpdate();
    }
}