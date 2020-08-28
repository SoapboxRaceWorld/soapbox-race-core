/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.AmplifierEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class AmplifierDAO extends LongKeyedDAO<AmplifierEntity> {

    public AmplifierDAO() {
        super(AmplifierEntity.class);
    }

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
}
