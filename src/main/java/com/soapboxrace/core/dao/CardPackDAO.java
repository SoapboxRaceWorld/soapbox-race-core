/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.CardPackEntity;

import javax.ejb.Stateless;

@Stateless
public class CardPackDAO extends LongKeyedDAO<CardPackEntity> {

    public CardPackDAO() {
        super(CardPackEntity.class);
    }

    public CardPackEntity findByEntitlementTag(String entitlementTag) {
        return this.entityManager.createNamedQuery("CardPackEntity.findByEntitlementTag", CardPackEntity.class)
                .setParameter("entitlementTag", entitlementTag)
                .getSingleResult();
    }
}
