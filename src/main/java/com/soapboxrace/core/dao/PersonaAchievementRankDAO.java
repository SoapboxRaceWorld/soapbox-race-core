/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.PersonaAchievementRankEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PersonaAchievementRankDAO extends LongKeyedDAO<PersonaAchievementRankEntity> {

    public PersonaAchievementRankDAO() {
        super(PersonaAchievementRankEntity.class);
    }

    public List<PersonaAchievementRankEntity> findAllByPersonaId(Long personaId) {
        TypedQuery<PersonaAchievementRankEntity> query = this.entityManager.createNamedQuery(
                "PersonaAchievementRankEntity.findAllByPersonaId", PersonaAchievementRankEntity.class);
        query.setParameter("personaId", personaId);
        return query.getResultList();
    }

    public PersonaAchievementRankEntity findByPersonaIdAndAchievementRankId(Long personaId, Long achievementRankId) {
        TypedQuery<PersonaAchievementRankEntity> query = this.entityManager.createNamedQuery(
                "PersonaAchievementRankEntity.findByPersonaIdAndAchievementRankId", PersonaAchievementRankEntity.class);
        query.setParameter("personaId", personaId);
        query.setParameter("achievementRankId", achievementRankId);

        List<PersonaAchievementRankEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public PersonaAchievementRankEntity findHighestCompletedRankOfAchievementByPersona(Long personaId,
                                                                                       Long achievementId) {
        TypedQuery<PersonaAchievementRankEntity> query = this.entityManager.createNamedQuery(
                "PersonaAchievementRankEntity.findHighestCompletedRankOfAchievementByPersona",
                PersonaAchievementRankEntity.class);
        query.setParameter("personaId", personaId);
        query.setParameter("achievementId", achievementId);

        List<PersonaAchievementRankEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public Long countPersonasWithRank(Long achievementRankId) {
        return this.entityManager.createNamedQuery("PersonaAchievementRankEntity.countPersonasWithRank", Long.class)
                .setParameter("achievementRankId", achievementRankId)
                .getSingleResult();
    }

    public void deleteByPersona(PersonaEntity personaEntity) {
        List<PersonaAchievementRankEntity> personaAchievementRankEntities =
                findAllByPersonaId(personaEntity.getPersonaId());

        for (PersonaAchievementRankEntity personaAchievementRankEntity : personaAchievementRankEntities) {
            delete(personaAchievementRankEntity);
        }
    }
}
