/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.StringKeyedDAO;
import com.soapboxrace.core.jpa.ParameterEntity;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ParameterDAO extends StringKeyedDAO<ParameterEntity> {

    public ParameterDAO() {
        super(ParameterEntity.class);
    }

    public List<ParameterEntity> findAll() {
        return entityManager.createNamedQuery("ParameterEntity.findAll", ParameterEntity.class).getResultList();
    }
}
