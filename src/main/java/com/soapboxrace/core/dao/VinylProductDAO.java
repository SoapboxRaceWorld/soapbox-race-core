/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.CategoryEntity;
import com.soapboxrace.core.jpa.VinylProductEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class VinylProductDAO extends LongKeyedDAO<VinylProductEntity> {

    public VinylProductDAO() {
        super(VinylProductEntity.class);
    }

    public VinylProductEntity findByHash(Integer hash) {
        TypedQuery<VinylProductEntity> query = entityManager.createNamedQuery("VinylProductEntity.findByHash",
                VinylProductEntity.class);
        query.setParameter("hash", hash);

        List<VinylProductEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

    public List<VinylProductEntity> findByCategoryLevelEnabled(CategoryEntity category, int minLevel, Boolean enabled
            , Boolean premium) {
        TypedQuery<VinylProductEntity> query = entityManager.createNamedQuery("VinylProductEntity" +
                ".findByCategoryLevelEnabled", VinylProductEntity.class);
        query.setParameter("category", category);
        query.setParameter("enabled", enabled);
        query.setParameter("minLevel", minLevel);
        query.setParameter("premium", premium);
        return query.getResultList();
    }

}
