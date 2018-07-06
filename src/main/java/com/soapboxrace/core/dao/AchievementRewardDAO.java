package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.AchievementRewardEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class AchievementRewardDAO extends BaseDAO<AchievementRewardEntity>
{
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public AchievementRewardEntity findByDescription(String description)
    {
        TypedQuery<AchievementRewardEntity> query = entityManager.createNamedQuery(
                "AchievementRewardEntity.findByDescription",
                AchievementRewardEntity.class
        );
        
        query.setParameter("description", description);

        List<AchievementRewardEntity> results = query.getResultList();
        
        return results.isEmpty() ? null : results.get(0);
    }
}
