package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.PersonaAchievementEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PersonaAchievementDAO extends BaseDAO<PersonaAchievementEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<PersonaAchievementEntity> findAllByPersonaId(Long personaId) {
        TypedQuery<PersonaAchievementEntity> query = this.entityManager.createNamedQuery("PersonaAchievementEntity" +
                ".findAllByPersonaId", PersonaAchievementEntity.class);
        query.setParameter("personaId", personaId);
        return query.getResultList();
    }

    public PersonaAchievementEntity findByPersonaIdAndAchievementId(Long personaId, Long achievementId) {
        TypedQuery<PersonaAchievementEntity> query = this.entityManager.createNamedQuery("PersonaAchievementEntity" +
                ".findByPersonaIdAndAchievementId", PersonaAchievementEntity.class);
        query.setParameter("personaId", personaId);
        query.setParameter("achievementId", achievementId);

        List<PersonaAchievementEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public void deleteByPersona(PersonaEntity personaEntity) {
        List<PersonaAchievementEntity> personaAchievementEntities = findAllByPersonaId(personaEntity.getPersonaId());

        for (PersonaAchievementEntity personaAchievementEntity : personaAchievementEntities) {
            delete(personaAchievementEntity);
        }
    }
}
