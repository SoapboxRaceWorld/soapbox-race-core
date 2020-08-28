/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.InventoryEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class InventoryDAO extends LongKeyedDAO<InventoryEntity> {

    public InventoryDAO() {
        super(InventoryEntity.class);
    }

    public InventoryEntity findByPersonaId(Long personaId) {
        TypedQuery<InventoryEntity> query = entityManager.createNamedQuery("InventoryEntity.findByPersonaId",
                InventoryEntity.class);
        query.setParameter("personaId", personaId);

        List<InventoryEntity> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        }

        return null;
    }

    /**
     * Delete the inventory associated with the given persona.
     *
     * @param personaEntity The persona whose inventory should be deleted.
     */
    public void deleteByPersona(PersonaEntity personaEntity) {
        InventoryEntity inventoryEntity = findByPersonaId(personaEntity.getPersonaId());

        if (inventoryEntity != null) {
            this.delete(inventoryEntity);
        }
    }
}
