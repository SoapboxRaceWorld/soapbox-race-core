/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ParameterEntity;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ParameterDAO extends BaseDAO<ParameterEntity> {

    public ParameterEntity findById(String name) {
        return entityManager.find(ParameterEntity.class, name);
    }

    public List<ParameterEntity> findAll() {
        return entityManager.createNamedQuery("ParameterEntity.findAll", ParameterEntity.class).getResultList();
    }
}
