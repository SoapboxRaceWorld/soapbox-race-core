/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.RewardTableEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RewardTableDAO extends BaseDAO<RewardTableEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<RewardTableEntity> findAll() {
        return this.entityManager.createNamedQuery("RewardTableEntity.findAll", RewardTableEntity.class)
                .getResultList();
    }

    public RewardTableEntity findByName(String name) {
        return this.entityManager.createNamedQuery("RewardTableEntity.findByName", RewardTableEntity.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public RewardTableEntity findByID(Long id) {
        return this.entityManager.find(RewardTableEntity.class, id);
    }
}
