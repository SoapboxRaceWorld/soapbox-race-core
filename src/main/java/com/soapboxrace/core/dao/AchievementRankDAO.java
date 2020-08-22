/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.AchievementRankEntity;

import javax.ejb.Stateless;

@Stateless
public class AchievementRankDAO extends BaseDAO<AchievementRankEntity> {

    public AchievementRankEntity findById(Long id) {
        return this.entityManager.find(AchievementRankEntity.class, id);
    }
}
