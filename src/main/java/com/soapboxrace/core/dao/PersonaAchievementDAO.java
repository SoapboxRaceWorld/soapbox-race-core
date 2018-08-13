package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.AchievementDefinitionEntity;
import com.soapboxrace.core.jpa.PersonaAchievementEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PersonaAchievementDAO extends BaseDAO<PersonaAchievementEntity>
{
    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    public List<PersonaAchievementEntity> findAllForPersona(PersonaEntity personaEntity)
    {
        return findAllForPersona(personaEntity.getPersonaId());
    }

    public List<PersonaAchievementEntity> findAllForPersona(Long personaId)
    {
        TypedQuery<PersonaAchievementEntity> query = entityManager.createNamedQuery(
                "PersonaAchievementEntity.findAllForPersona",
                PersonaAchievementEntity.class
        );

        query.setParameter("id", personaId);

        return query.getResultList();
    }

    public List<PersonaAchievementEntity> findAllForAchievement(AchievementDefinitionEntity achievement)
    {
        return findAllForAchievement(achievement.getId());
    }

    public List<PersonaAchievementEntity> findAllForAchievement(Long achievementId)
    {
        TypedQuery<PersonaAchievementEntity> query = entityManager.createNamedQuery(
                "PersonaAchievementEntity.findAllForAchievement",
                PersonaAchievementEntity.class
        );

        query.setParameter("id", achievementId);

        return query.getResultList();
    }

    public PersonaAchievementEntity getForPersonaAchievement(PersonaEntity personaEntity, AchievementDefinitionEntity achievementDefinitionEntity)
    {
        return getForPersonaAchievement(personaEntity.getPersonaId(), achievementDefinitionEntity.getId());
    }
    
    public PersonaAchievementEntity getForPersonaAchievement(Long personaId, Long achievementId)
    {
        TypedQuery<PersonaAchievementEntity> query = entityManager.createNamedQuery(
                "PersonaAchievementEntity.getForPersonaAchievement",
                PersonaAchievementEntity.class
        );

        query.setParameter("personaId", personaId);
        query.setParameter("achId", achievementId);

        List<PersonaAchievementEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public void deleteByPersona(Long personaId)
    {
        Query query = entityManager.createNamedQuery("PersonaAchievementEntity.deleteByPersona");
        query.setParameter("personaId", personaId);
        query.executeUpdate();
    }
}
