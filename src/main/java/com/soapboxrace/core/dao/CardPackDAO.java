package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.CardPackEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CardPackDAO extends BaseDAO<CardPackEntity> {
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CardPackEntity findByEntitlementTag(String entitlementTag) {
        return this.entityManager.createNamedQuery("CardPackEntity.findByEntitlementTag", CardPackEntity.class)
                .setParameter("entitlementTag", entitlementTag)
                .getSingleResult();
    }
}
