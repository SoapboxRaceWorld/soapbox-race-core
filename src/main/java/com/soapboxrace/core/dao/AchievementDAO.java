package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.AchievementDefinitionEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class AchievementDAO extends BaseDAO<AchievementDefinitionEntity>
{
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public AchievementDefinitionEntity findById(Long id)
    {
        return entityManager.find(AchievementDefinitionEntity.class, id);
    }

    public AchievementDefinitionEntity findByBadgeId(Long badgeId)
    {
        TypedQuery<AchievementDefinitionEntity> query = entityManager.createQuery(
                "SELECT obj FROM AchievementDefinitionEntity obj WHERE obj.badgeDefinition.id = :badgeId",
                AchievementDefinitionEntity.class
        );

        query.setParameter("badgeId", badgeId);

        List<AchievementDefinitionEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }


    public AchievementDefinitionEntity findByName(String name)
    {
        TypedQuery<AchievementDefinitionEntity> query = entityManager.createQuery(
                "SELECT obj FROM AchievementDefinitionEntity obj WHERE obj.friendlyIdentifier = :name",
                AchievementDefinitionEntity.class
        );

        query.setParameter("name", name);

        List<AchievementDefinitionEntity> results = query.getResultList();
        
        return results.isEmpty() ? null : results.get(0);
    }

    public List<AchievementDefinitionEntity> getAll()
    {
        return entityManager.createQuery(
                "SELECT obj FROM AchievementDefinitionEntity obj",
                AchievementDefinitionEntity.class
        ).getResultList();
    }
}
