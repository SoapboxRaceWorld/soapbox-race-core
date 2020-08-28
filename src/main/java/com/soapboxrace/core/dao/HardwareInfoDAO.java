/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.HardwareInfoEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class HardwareInfoDAO extends LongKeyedDAO<HardwareInfoEntity> {

    public HardwareInfoDAO() {
        super(HardwareInfoEntity.class);
    }

    public HardwareInfoEntity findByHardwareHash(String hardwareHash) {
        TypedQuery<HardwareInfoEntity> query = entityManager.createNamedQuery("HardwareInfoEntity.findByHardwareHash"
                , HardwareInfoEntity.class);
        query.setParameter("hardwareHash", hardwareHash);
        List<HardwareInfoEntity> resultList = query.getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    public HardwareInfoEntity findByUserId(Long userId) {
        TypedQuery<HardwareInfoEntity> query = entityManager.createNamedQuery("HardwareInfoEntity.findByUserId",
                HardwareInfoEntity.class);
        query.setParameter("userId", userId);
        List<HardwareInfoEntity> resultList = query.getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

}
