package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.VirtualItemEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class VirtualItemDAO extends BaseDAO<VirtualItemEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public VirtualItemEntity findByHash(Integer hash) {
        TypedQuery<VirtualItemEntity> query = this.entityManager.createNamedQuery("VirtualItemEntity.findByHash", VirtualItemEntity.class);
        query.setParameter("hash", hash);
        List<VirtualItemEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}
