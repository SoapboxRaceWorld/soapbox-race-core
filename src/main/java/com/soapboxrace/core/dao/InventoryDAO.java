package com.soapboxrace.core.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.InventoryEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

@Stateless
public class InventoryDAO extends BaseDAO<InventoryEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public InventoryEntity findById(Long id) {
        InventoryEntity inventoryEntity = entityManager.find(InventoryEntity.class, id);
        inventoryEntity.getItems().size();
        return inventoryEntity;
    }

    public InventoryEntity findByPersonaId(Long personaId) {
        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setPersonaId(personaId);

        TypedQuery<InventoryEntity> query = entityManager.createNamedQuery("InventoryEntity.findByPersona", InventoryEntity.class);
        query.setParameter("persona", personaEntity);

        List<InventoryEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

    public void deleteByPersona(Long personaId) {
        Query query = entityManager.createNamedQuery("InventoryEntity.deleteByPersona");
        query.setParameter("personaId", personaId);
        query.executeUpdate();
    }
}
