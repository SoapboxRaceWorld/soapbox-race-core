/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.AchievementRankEntity;

import javax.ejb.Stateless;

@Stateless
public class AchievementRankDAO extends LongKeyedDAO<AchievementRankEntity> {

    public AchievementRankDAO() {
        super(AchievementRankEntity.class);
    }

    public void updateRankRarity(Long rankId, float rarity) {
        this.entityManager.createNamedQuery("AchievementRankEntity.updateRarity")
                .setParameter("id", rankId)
                .setParameter("rarity", rarity)
                .executeUpdate();
    }
}
