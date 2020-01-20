/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.CardPackEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CardPackDAO extends BaseDAO<CardPackEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CardPackEntity findByEntitlementTag(String entitlementTag) {
        return this.entityManager.createNamedQuery("CardPackEntity.findByEntitlementTag", CardPackEntity.class)
                .setParameter("entitlementTag", entitlementTag)
                .getSingleResult();
    }
}
