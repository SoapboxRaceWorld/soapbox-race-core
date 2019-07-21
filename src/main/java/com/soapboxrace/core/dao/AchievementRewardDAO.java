package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.AchievementRewardEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AchievementRewardDAO extends BaseDAO<AchievementRewardEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public AchievementRewardEntity findByDescription(String description) {
        return this.entityManager.createNamedQuery("AchievementRewardEntity.findByDescription", AchievementRewardEntity.class)
                .setParameter("description", description)
                .getSingleResult();
    }
}
