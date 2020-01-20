/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.CustomCarEntity;
import com.soapboxrace.core.jpa.PerformancePartEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PerformancePartDAO extends BaseDAO<PerformancePartEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void deleteByCustomCar(CustomCarEntity customCarEntity) {
        Query query = entityManager.createNamedQuery("PerformancePartEntity.deleteByCustomCar");
        query.setParameter("customCar", customCarEntity);
        query.executeUpdate();
    }

}
