/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.OwnedCarEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class OwnedCarDAO extends LongKeyedDAO<OwnedCarEntity> {

    public OwnedCarDAO() {
        super(OwnedCarEntity.class);
    }

    public List<OwnedCarEntity> findByPersonaId(Long personaId) {
        TypedQuery<OwnedCarEntity> query = entityManager.createNamedQuery("OwnedCarEntity.findByPersonaId",
                OwnedCarEntity.class);
        query.setParameter("persona", personaId);
        return query.getResultList();
    }

    public OwnedCarEntity findByPersonaIdEager(Long personaId, int index) {
        TypedQuery<OwnedCarEntity> query = entityManager.createNamedQuery("OwnedCarEntity.findByPersonaIdEager",
                OwnedCarEntity.class);
        query.setParameter("persona", personaId);
        query.setFirstResult(index);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    public List<OwnedCarEntity> findByPersonaIdEager(Long personaId) {
        TypedQuery<OwnedCarEntity> query = entityManager.createNamedQuery("OwnedCarEntity.findByPersonaIdEager",
                OwnedCarEntity.class);
        query.setParameter("persona", personaId);
        return query.getResultList();
    }

    public Long findNumNonRentalsByPersonaId(Long personaId) {
        TypedQuery<Long> query = entityManager.createNamedQuery("OwnedCarEntity.findNumNonRentalsByPersonaId",
                Long.class);
        query.setParameter("persona", personaId);
        return query.getSingleResult();
    }

    public int findNumByPersonaId(Long personaId) {
        TypedQuery<Long> query = entityManager.createNamedQuery("OwnedCarEntity.findNumByPersonaId",
                Long.class);
        query.setParameter("persona", personaId);
        return query.getSingleResult().intValue();
    }

    public int deleteAllExpired() {
        Query query = entityManager.createNamedQuery("OwnedCarEntity.deleteAllExpired");
        return query.executeUpdate();
    }

    public void deleteByPersona(PersonaEntity personaEntity) {
        Query query = entityManager.createNamedQuery("OwnedCarEntity.deleteByPersona");
        query.setParameter("persona", personaEntity);
        query.executeUpdate();
    }

}
