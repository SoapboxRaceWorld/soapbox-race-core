package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.AchievementDefinitionEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AchievementDAO extends BaseDAO<AchievementDefinitionEntity>
{
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
    
    public List<AchievementDefinitionEntity> getAll()
    {
        return entityManager.createQuery(
                "SELECT obj FROM AchievementDefinitionEntity obj",
                AchievementDefinitionEntity.class
        ).getResultList();
    }
}
