/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.TreasureHuntEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class TreasureHuntDAO extends LongKeyedDAO<TreasureHuntEntity> {

    public TreasureHuntDAO() {
        super(TreasureHuntEntity.class);
    }

    public void deleteByPersona(PersonaEntity personaEntity) {
        Query query = entityManager.createNamedQuery("TreasureHuntEntity.deleteByPersona");
        query.setParameter("personaId", personaEntity.getPersonaId());
        query.executeUpdate();
    }

}
