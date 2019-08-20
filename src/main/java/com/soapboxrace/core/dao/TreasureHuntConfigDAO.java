package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.TreasureHuntConfigEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class TreasureHuntConfigDAO extends BaseDAO<TreasureHuntConfigEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public TreasureHuntConfigEntity findForStreak(Integer streak) {
        TypedQuery<TreasureHuntConfigEntity> query = this.entityManager.createNamedQuery(
                "TreasureHuntConfigEntity.findConfigForStreak", TreasureHuntConfigEntity.class);
        query.setParameter("streak", streak);
        return query.getSingleResult();
    }
}
