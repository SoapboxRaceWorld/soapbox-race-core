/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.AchievementEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class AchievementDAO extends LongKeyedDAO<AchievementEntity> {

    protected AchievementDAO() {
        super(AchievementEntity.class);
    }

    public List<AchievementEntity> findAll() {
        TypedQuery<AchievementEntity> query = this.entityManager.createNamedQuery("AchievementEntity.findAll",
                AchievementEntity.class);
        return query.getResultList();
    }

    public List<AchievementEntity> findAllByCategory(String category) {
        TypedQuery<AchievementEntity> query = this.entityManager.createNamedQuery("AchievementEntity.findAllByCategory",
                AchievementEntity.class);
        query.setParameter("category", category);
        return query.getResultList();
    }
}
