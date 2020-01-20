/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class InventoryItemDAO extends BaseDAO<InventoryItemEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<InventoryItemEntity> findAllByPersonaId(Long personaId) {
        return entityManager.createNamedQuery("InventoryItemEntity.findAllByPersonaId", InventoryItemEntity.class)
                .setParameter("personaId", personaId)
                .getResultList();
    }

    public List<InventoryItemEntity> findAllByPersonaIdAndEntitlementTag(Long personaId, String entitlementTag) {
        return entityManager.createNamedQuery("InventoryItemEntity.findAllByPersonaIdAndTag", InventoryItemEntity.class)
                .setParameter("personaId", personaId)
                .setParameter("entitlementTag", entitlementTag)
                .getResultList();
    }

    public List<InventoryItemEntity> findAllByPersonaIdAndType(Long personaId, String productType) {
        return entityManager.createNamedQuery("InventoryItemEntity.findAllByPersonaIdAndType",
                InventoryItemEntity.class)
                .setParameter("personaId", personaId)
                .setParameter("productType", productType)
                .getResultList();
    }

    public InventoryItemEntity findByPersonaIdAndHash(Long personaId, Integer hash) {
        TypedQuery<InventoryItemEntity> query = entityManager.createNamedQuery("InventoryItemEntity" +
                ".findAllByPersonaIdAndHash", InventoryItemEntity.class);
        query.setParameter("personaId", personaId);
        query.setParameter("hash", hash);

        List<InventoryItemEntity> results = query.getResultList();

        if (!results.isEmpty()) {
            return results.get(0);
        }

        return null;
    }

    public InventoryItemEntity findByPersonaIdAndEntitlementTag(Long personaId, String entitlementTag) {
        TypedQuery<InventoryItemEntity> query = entityManager.createNamedQuery("InventoryItemEntity" +
                ".findAllByPersonaIdAndTag", InventoryItemEntity.class);
        query.setParameter("personaId", personaId);
        query.setParameter("entitlementTag", entitlementTag);

        List<InventoryItemEntity> results = query.getResultList();

        if (!results.isEmpty()) {
            return results.get(0);
        }

        return null;
    }

    public InventoryItemEntity findByInventoryIdAndEntitlementTag(Long inventoryId, String entitlementTag) {
        TypedQuery<InventoryItemEntity> query = entityManager.createNamedQuery("InventoryItemEntity" +
                ".findAllByInventoryAndTag", InventoryItemEntity.class);
        query.setParameter("inventoryId", inventoryId);
        query.setParameter("entitlementTag", entitlementTag);

        List<InventoryItemEntity> results = query.getResultList();

        if (!results.isEmpty()) {
            return results.get(0);
        }

        return null;
    }

    public InventoryItemEntity findByInventoryIdAndHash(Long inventoryId, Integer hash) {
        TypedQuery<InventoryItemEntity> query = entityManager.createNamedQuery("InventoryItemEntity" +
                        ".findAllByInventoryAndHash",
                InventoryItemEntity.class);
        query.setParameter("inventoryId", inventoryId);
        query.setParameter("hash", hash);

        List<InventoryItemEntity> results = query.getResultList();

        if (!results.isEmpty()) {
            return results.get(0);
        }

        return null;
    }

    public void deleteByPersona(PersonaEntity personaEntity) {
        List<InventoryItemEntity> items = this.findAllByPersonaId(personaEntity.getPersonaId());

        for (InventoryItemEntity inventoryItemEntity : items) {
            delete(inventoryItemEntity);
        }
    }

    public void deleteAllExpiredItems() {
        entityManager.createNamedQuery("InventoryItemEntity.deleteAllExpiredItems").executeUpdate();
    }
}
