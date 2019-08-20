package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.AchievementEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class AchievementDAO extends BaseDAO<AchievementEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<AchievementEntity> findAll() {
        TypedQuery<AchievementEntity> query = this.entityManager.createNamedQuery("AchievementEntity.findAll",
                AchievementEntity.class);
        return query.getResultList();
    }

    public AchievementEntity findByName(String name) {
        TypedQuery<AchievementEntity> query = this.entityManager.createNamedQuery("AchievementEntity.findByName",
                AchievementEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public List<AchievementEntity> findAllByCategory(String category) {
        TypedQuery<AchievementEntity> query = this.entityManager.createNamedQuery("AchievementEntity" +
                ".findAllByCategory", AchievementEntity.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public List<AchievementEntity> findAllVisible(String category) {
        TypedQuery<AchievementEntity> query = this.entityManager.createNamedQuery("AchievementEntity.findAllVisible",
                AchievementEntity.class);
        return query.getResultList();
    }
}
