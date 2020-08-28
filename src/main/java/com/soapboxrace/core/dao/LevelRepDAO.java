/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.LevelRepEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class LevelRepDAO extends LongKeyedDAO<LevelRepEntity> {

    public LevelRepDAO() {
        super(LevelRepEntity.class);
    }

    @Deprecated
    public LevelRepEntity findByLevel(Long level) {
        return find(level);
    }

    public List<LevelRepEntity> findAll() {
        TypedQuery<LevelRepEntity> query = entityManager.createNamedQuery("LevelRepEntity.findAll",
                LevelRepEntity.class);
        return query.getResultList();
    }
}
