/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.CategoryEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CategoryDAO extends LongKeyedDAO<CategoryEntity> {

    public CategoryDAO() {
        super(CategoryEntity.class);
    }

    public List<CategoryEntity> getAll() {
        TypedQuery<CategoryEntity> query = entityManager.createNamedQuery("CategoryEntity.getAll",
                CategoryEntity.class);
        return query.getResultList();
    }

}
