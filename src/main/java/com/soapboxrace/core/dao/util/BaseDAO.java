/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao.util;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseDAO<TE, TK> {

    protected EntityManager entityManager;

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract TE find(TK key);

    public void insert(TE entity) {
        entityManager.persist(entity);
    }

    public void update(TE entity) {
        entityManager.merge(entity);
    }

    public void delete(TE entity) {
        entityManager.remove(entityManager.merge(entity));
    }

}
