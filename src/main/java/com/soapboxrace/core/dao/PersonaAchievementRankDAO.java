package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.AchievementDefinitionEntity;
import com.soapboxrace.core.jpa.AchievementRankEntity;
import com.soapboxrace.core.jpa.PersonaAchievementRankEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PersonaAchievementRankDAO extends BaseDAO<PersonaAchievementRankEntity>
{
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public List<PersonaAchievementRankEntity> findAllForPersonaAchievement(PersonaEntity personaEntity, AchievementDefinitionEntity achievementDefinitionEntity)
    {
        return findAllForPersonaAchievement(personaEntity.getPersonaId(), achievementDefinitionEntity.getId());
    }

    public List<PersonaAchievementRankEntity> findAllForPersonaAchievement(long personaId, long achievementId)
    {
        TypedQuery<PersonaAchievementRankEntity> query = entityManager.createNamedQuery(
                "PersonaAchievementRankEntity.findAllForPersonaAchievement",
                PersonaAchievementRankEntity.class
        );

        query.setParameter("personaId", personaId);
        query.setParameter("achievementId", achievementId);

        return query.getResultList();
    }

    public List<PersonaAchievementRankEntity> findAllForRank(AchievementRankEntity achievementRankEntity)
    {
        return findAllForRank(achievementRankEntity.getId());
    }

    public List<PersonaAchievementRankEntity> findAllForRank(long rankId)
    {
        TypedQuery<PersonaAchievementRankEntity> query = entityManager.createNamedQuery(
                "PersonaAchievementRankEntity.findAllForRank",
                PersonaAchievementRankEntity.class
        );

        query.setParameter("rankId", rankId);

        return query.getResultList();
    }

    public PersonaAchievementRankEntity findByPersonaAchievement(PersonaEntity persona, AchievementDefinitionEntity achievement, AchievementRankEntity rank)
    {
        return findByPersonaAchievement(persona.getPersonaId(), achievement.getId(), rank.getId());
    }

    public PersonaAchievementRankEntity findByPersonaAchievement(long personaId, long achievementId, long rankId)
    {
        TypedQuery<PersonaAchievementRankEntity> query = entityManager.createNamedQuery(
                "PersonaAchievementRankEntity.findByPersonaAchievement",
                PersonaAchievementRankEntity.class
        );

        query.setParameter("personaId", personaId);
        query.setParameter("achievementId", achievementId);
        query.setParameter("rankId", rankId);

        List<PersonaAchievementRankEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public void deleteByPersona(Long personaId)
    {
        Query query = entityManager.createNamedQuery("PersonaAchievementRankEntity.deleteByPersona");
        query.setParameter("personaId", personaId);
        query.executeUpdate();
    }
}
