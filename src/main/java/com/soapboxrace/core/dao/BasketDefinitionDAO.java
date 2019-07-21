package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.BasketDefinitionEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class BasketDefinitionDAO extends BaseDAO<BasketDefinitionEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BasketDefinitionEntity findById(String productId) {
        return entityManager.find(BasketDefinitionEntity.class, productId);
    }

}
