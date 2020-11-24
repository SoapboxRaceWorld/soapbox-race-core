/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.PersonaAchievementEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PersonaAchievementDAO extends LongKeyedDAO<PersonaAchievementEntity> {

    public PersonaAchievementDAO() {
        super(PersonaAchievementEntity.class);
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

    public void deleteByPersona(PersonaEntity personaEntity) {
        List<PersonaAchievementEntity> personaAchievementEntities = findAllByPersonaId(personaEntity.getPersonaId());

        for (PersonaAchievementEntity personaAchievementEntity : personaAchievementEntities) {
            delete(personaAchievementEntity);
        }
    }
}
