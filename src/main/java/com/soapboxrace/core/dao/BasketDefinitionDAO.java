/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.StringKeyedDAO;
import com.soapboxrace.core.jpa.BasketDefinitionEntity;

import javax.ejb.Stateless;

@Stateless
public class BasketDefinitionDAO extends StringKeyedDAO<BasketDefinitionEntity> {

    public BasketDefinitionDAO() {
        super(BasketDefinitionEntity.class);
    }
}
