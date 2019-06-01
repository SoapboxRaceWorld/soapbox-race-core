package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.PersonaAchievementEntity;
import com.soapboxrace.core.jpa.PersonaAchievementRankEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PersonaAchievementRankDAO extends BaseDAO<PersonaAchievementRankEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PersonaAchievementRankEntity findByPersonaIdAndAchievementRankId(Long personaId, Long achievementRankId) {
        TypedQuery<PersonaAchievementRankEntity> query = this.entityManager.createNamedQuery("PersonaAchievementRankEntity.findByPersonaIdAndAchievementRankId", PersonaAchievementRankEntity.class);
        query.setParameter("personaId", personaId);
        query.setParameter("achievementRankId", achievementRankId);

        List<PersonaAchievementRankEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}
