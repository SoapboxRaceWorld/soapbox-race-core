package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.InventoryItemEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class InventoryItemDAO extends BaseDAO<InventoryItemEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public InventoryItemEntity findByEntitlementTagAndPersonaId(String entitlementTag, long personaId)
    {
        TypedQuery<InventoryItemEntity> query = entityManager.createQuery(
                "SELECT obj FROM InventoryItemEntity obj WHERE obj.entitlementTag = :tag AND obj.persona.id = :personaId", InventoryItemEntity.class);
        query.setParameter("tag", entitlementTag);
        query.setParameter("personaId", personaId);

        List<InventoryItemEntity> list = query.getResultList();
        
        return list.isEmpty() ? null : list.get(0);
    }
    
    public void deleteByPersona(Long personaId) {
        Query query = entityManager.createNamedQuery("InventoryItemEntity.deleteByPersona");
        query.setParameter("personaId", personaId);
        query.executeUpdate();
    }
}
