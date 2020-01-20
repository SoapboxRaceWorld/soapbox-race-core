/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.AmplifierEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class AmplifierDAO extends BaseDAO<AmplifierEntity> {
    /**
     * Finds the {@link AmplifierEntity} in the database with the given product hash.
     *
     * @param hash The product hash to search for.
     * @return The {@link AmplifierEntity} with the given hash.
     */
    public AmplifierEntity findAmplifierByHash(Integer hash) {
        TypedQuery<AmplifierEntity> query = this.entityManager.createNamedQuery("AmplifierEntity.findAmplifierByHash"
                , AmplifierEntity.class);
        query.setParameter("hash", hash);
        return query.getSingleResult();
    }

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
