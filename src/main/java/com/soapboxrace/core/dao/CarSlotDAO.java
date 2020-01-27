/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CarSlotDAO extends BaseDAO<CarSlotEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CarSlotEntity findById(Long id) {
        return entityManager.find(CarSlotEntity.class, id);
    }

    public List<CarSlotEntity> findByPersonaId(Long personaId) {
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setPersonaId(personaId);

        TypedQuery<CarSlotEntity> query = entityManager.createNamedQuery("CarSlotEntity.findByPersonaId",
                CarSlotEntity.class);
        query.setParameter("persona", personaEntity);
        return query.getResultList();
    }

    public List<CarSlotEntity> findNonRentalsByPersonaId(Long personaId) {
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setPersonaId(personaId);

        TypedQuery<CarSlotEntity> query = entityManager.createNamedQuery("CarSlotEntity.findNonRentalsByPersonaId",
                CarSlotEntity.class);
        query.setParameter("persona", personaEntity);
        return query.getResultList();
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
