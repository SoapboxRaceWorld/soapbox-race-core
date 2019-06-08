package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.BadgeDefinitionEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class BadgeDefinitionDAO extends BaseDAO<BadgeDefinitionEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BadgeDefinitionEntity find(Long id) {
        return this.entityManager.find(BadgeDefinitionEntity.class, id);
    }

    public List<BadgeDefinitionEntity> findAll() {
        TypedQuery<BadgeDefinitionEntity> query = this.entityManager.createNamedQuery("BadgeDefinitionEntity.findAll", BadgeDefinitionEntity.class);
        return query.getResultList();
    }
}
