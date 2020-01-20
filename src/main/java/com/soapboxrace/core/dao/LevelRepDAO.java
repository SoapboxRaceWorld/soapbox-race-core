/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.LevelRepEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class LevelRepDAO extends BaseDAO<LevelRepEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public LevelRepEntity findByLevel(Long level) {
        TypedQuery<LevelRepEntity> query = entityManager.createNamedQuery("LevelRepEntity.findByLevel",
                LevelRepEntity.class);
        query.setParameter("level", level);

        List<LevelRepEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

    public List<LevelRepEntity> findAll() {
        TypedQuery<LevelRepEntity> query = entityManager.createNamedQuery("LevelRepEntity.findAll",
                LevelRepEntity.class);
        return query.getResultList();
    }
}
