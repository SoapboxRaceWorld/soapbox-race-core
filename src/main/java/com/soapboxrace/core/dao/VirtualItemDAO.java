/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.VirtualItemEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class VirtualItemDAO extends BaseDAO<VirtualItemEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public VirtualItemEntity findByHash(Integer hash) {
        TypedQuery<VirtualItemEntity> query = this.entityManager.createNamedQuery("VirtualItemEntity.findByHash",
                VirtualItemEntity.class);
        query.setParameter("hash", hash);
        List<VirtualItemEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public VirtualItemEntity findByItemName(String itemName) {
        TypedQuery<VirtualItemEntity> query = this.entityManager.createNamedQuery("VirtualItemEntity.findByItemName",
                VirtualItemEntity.class);
        query.setParameter("itemName", itemName);
        List<VirtualItemEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}
