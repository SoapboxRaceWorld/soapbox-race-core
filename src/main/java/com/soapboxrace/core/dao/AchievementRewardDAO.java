/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.AchievementRewardEntity;

import javax.ejb.Stateless;

@Stateless
public class AchievementRewardDAO extends LongKeyedDAO<AchievementRewardEntity> {

    public AchievementRewardDAO() {
        super(AchievementRewardEntity.class);
    }

    public AchievementRewardEntity findByDescription(String description) {
        return this.entityManager.createNamedQuery("AchievementRewardEntity.findByDescription",
                AchievementRewardEntity.class)
                .setParameter("description", description)
                .getSingleResult();
    }
}
