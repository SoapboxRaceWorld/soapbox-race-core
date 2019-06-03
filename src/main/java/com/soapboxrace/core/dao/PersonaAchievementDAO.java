package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.PersonaAchievementEntity;
import com.soapboxrace.core.jpa.PersonaAchievementRankEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class PersonaAchievementDAO extends BaseDAO<PersonaAchievementEntity> {

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<PersonaAchievementEntity> findAllByPersonaId(Long personaId) {
        TypedQuery<PersonaAchievementEntity> query = this.entityManager.createNamedQuery("PersonaAchievementEntity.findAllByPersonaId", PersonaAchievementEntity.class);
        query.setParameter("personaId", personaId);
        return query.getResultList();
    }

    public PersonaAchievementEntity findByPersonaIdAndAchievementId(Long personaId, Long achievementId) {
        TypedQuery<PersonaAchievementEntity> query = this.entityManager.createNamedQuery("PersonaAchievementEntity.findByPersonaIdAndAchievementId", PersonaAchievementEntity.class);
        query.setParameter("personaId", personaId);
        query.setParameter("achievementId", achievementId);

        List<PersonaAchievementEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public Long countPersonasWithRank(Long achievementRankId) {
        return this.entityManager.createNamedQuery("PersonaAchievementRankEntity.countPersonasWithRank", Long.class)
                .setParameter("achievementRankId", achievementRankId)
                .getSingleResult();
//        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
//        cq.select(qb.count(cq.from(PersonaAchievementRankEntity.class)));
//        Root<PersonaAchievementRankEntity> personaAchievementEntityRoot = cq.from(PersonaAchievementRankEntity.class);
//        cq.where(qb.equal(personaAchievementEntityRoot.get("achievementRankEntity").get("id"), achievementRankId));
//        cq.where(qb.isNotNull(personaAchievementEntityRoot.get("achievedOn")));
//        return entityManager.createQuery(cq).getSingleResult();
//        TypedQuery<Long> query = this.entityManager.createNamedQuery("PersonaAchievementEntity.countPersonasWithRank", Long.class);
//        query.setParameter("achievementId", achievementId);
//        query.setParameter("threshold", threshold);
//        return query.getSingleResult();
    }
}
