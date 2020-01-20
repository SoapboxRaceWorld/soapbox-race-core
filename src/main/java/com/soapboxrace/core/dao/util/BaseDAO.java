/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao.util;

import javax.persistence.EntityManager;

public abstract class BaseDAO<T> {

    protected EntityManager entityManager;

    protected abstract void setEntityManager(EntityManager entityManager);

    public void insert(T entity) {
        entityManager.persist(entity);
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entityManager.merge(entity));
    }

}
