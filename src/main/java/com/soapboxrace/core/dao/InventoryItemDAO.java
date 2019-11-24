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

    public List<InventoryItemEntity> findAllWithExpirationDate() {
        return entityManager.createNamedQuery("InventoryItemEntity.findAllWithExpirationDate",
                InventoryItemEntity.class)
                .getResultList();
    }

    public List<InventoryItemEntity> findAllByInventoryId(Long inventoryId) {
        return entityManager.createNamedQuery("InventoryItemEntity.findAllByInventoryId", InventoryItemEntity.class)
                .setParameter("inventoryId", inventoryId)
                .getResultList();
    }

    public List<InventoryItemEntity> findAllByPersonaId(Long personaId) {
        return entityManager.createNamedQuery("InventoryItemEntity.findAllByPersonaId", InventoryItemEntity.class)
                .setParameter("personaId", personaId)
                .getResultList();
    }

    public List<InventoryItemEntity> findAllByInventoryAndType(Long inventoryId, String type) {
        return entityManager.createNamedQuery("InventoryItemEntity.findAllByInventoryIdAndType",
                InventoryItemEntity.class)
                .setParameter("inventoryId", inventoryId)
                .setParameter("virtualItemType", type)
                .getResultList();
    }

    public List<InventoryItemEntity> findAllByPersonaIdAndEntitlementTag(Long personaId, String entitlementTag) {
        return entityManager.createNamedQuery("InventoryItemEntity.findAllByPersonaIdAndTag", InventoryItemEntity.class)
                .setParameter("personaId", personaId)
                .setParameter("entitlementTag", entitlementTag)
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

    public void deleteByPersona(PersonaEntity personaEntity) {
        List<InventoryItemEntity> items = this.findAllByPersonaId(personaEntity.getPersonaId());

        for (InventoryItemEntity inventoryItemEntity : items) {
            delete(inventoryItemEntity);
        }
    }

    @Override
    public void insert(InventoryItemEntity entity) {
        super.insert(entity);

        System.out.println("InventoryItemDAO insert() called");
    }
}
