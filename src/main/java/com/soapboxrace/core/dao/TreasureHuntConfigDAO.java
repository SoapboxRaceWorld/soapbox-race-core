/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.TreasureHuntConfigEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TreasureHuntConfigDAO extends LongKeyedDAO<TreasureHuntConfigEntity> {

    public TreasureHuntConfigDAO() {
        super(TreasureHuntConfigEntity.class);
    }

    public TreasureHuntConfigEntity findForStreak(Integer streak) {
        TypedQuery<TreasureHuntConfigEntity> query = this.entityManager.createNamedQuery(
                "TreasureHuntConfigEntity.findConfigForStreak", TreasureHuntConfigEntity.class);
        query.setParameter("streak", streak);
        List<TreasureHuntConfigEntity> resultList = query.getResultList();

        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }

        throw new EngineException("No treasure hunt configuration was found in the database", EngineExceptionCode.UnspecifiedError, true);
    }
}
