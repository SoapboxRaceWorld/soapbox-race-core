package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.InventoryEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class InventoryDAO extends BaseDAO<InventoryEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public InventoryEntity findByPersonaId(Long personaId) {
        TypedQuery<InventoryEntity> query = entityManager.createNamedQuery("InventoryEntity.findByPersonaId", InventoryEntity.class);
        query.setParameter("personaId", personaId);

        List<InventoryEntity> results = query.getResultList();

        if (!results.isEmpty()) {
            return results.get(0);
        }

        return null;
    }

    /**
     * Delete the inventory associated with the given persona ID.
     *
     * @param personaId The ID of the persona whose inventory should be deleted.
     */
    public void deleteByPersona(Long personaId) {
        InventoryEntity inventoryEntity = findByPersonaId(personaId);

        if (inventoryEntity != null) {
            this.delete(inventoryEntity);
        }
    }
}
