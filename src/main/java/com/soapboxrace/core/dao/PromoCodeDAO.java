/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.PromoCodeEntity;
import com.soapboxrace.core.jpa.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PromoCodeDAO extends BaseDAO<PromoCodeEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PromoCodeEntity findByUser(UserEntity userEntity) {
        TypedQuery<PromoCodeEntity> query = entityManager.createNamedQuery("PromoCodeEntity.findByUser",
                PromoCodeEntity.class);
        query.setParameter("user", userEntity);

        List<PromoCodeEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

    public PromoCodeEntity findByCode(String promoCode) {
        TypedQuery<PromoCodeEntity> query = entityManager.createNamedQuery("PromoCodeEntity.findByCode",
                PromoCodeEntity.class);
        query.setParameter("promoCode", promoCode);

        List<PromoCodeEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

}
