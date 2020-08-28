/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CarSlotDAO extends LongKeyedDAO<CarSlotEntity> {

    public CarSlotDAO() {
        super(CarSlotEntity.class);
    }

    public List<CarSlotEntity> findByPersonaId(Long personaId) {
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setPersonaId(personaId);

        TypedQuery<CarSlotEntity> query = entityManager.createNamedQuery("CarSlotEntity.findByPersonaId",
                CarSlotEntity.class);
        query.setParameter("persona", personaEntity);
        return query.getResultList();
    }

    public Long findNumNonRentalsByPersonaId(Long personaId) {
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setPersonaId(personaId);

        TypedQuery<Long> query = entityManager.createNamedQuery("CarSlotEntity.findNumNonRentalsByPersonaId",
                Long.class);
        query.setParameter("persona", personaEntity);
        return query.getSingleResult();
    }

    public List<CarSlotEntity> findAllExpired() {
        TypedQuery<CarSlotEntity> query = entityManager.createNamedQuery("CarSlotEntity.findAllExpired",
                CarSlotEntity.class);
        return query.getResultList();
    }

    public void deleteByPersona(PersonaEntity personaEntity) {
        Query query = entityManager.createNamedQuery("CarSlotEntity.deleteByPersona");
        query.setParameter("persona", personaEntity);
        query.executeUpdate();
    }

}
