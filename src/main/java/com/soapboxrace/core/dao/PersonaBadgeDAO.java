/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.PersonaBadgeEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class PersonaBadgeDAO extends LongKeyedDAO<PersonaBadgeEntity> {

    public PersonaBadgeDAO() {
        super(PersonaBadgeEntity.class);
    }

    public void deleteAllBadgesForPersona(Long personaId) {
        this.entityManager.createNamedQuery("PersonaBadgeEntity.deleteAllBadgesForPersona")
                .setParameter("personaId", personaId)
                .executeUpdate();
    }

    public List<PersonaBadgeEntity> findAllBadgesForPersona(Long personaId) {
        return this.entityManager.createNamedQuery("PersonaBadgeEntity.findAllBadgesForPersona",
                PersonaBadgeEntity.class)
                .setParameter("personaId", personaId)
                .getResultList();
    }

    public PersonaBadgeEntity findBadgeInSlotForPersona(Long personaId, Integer slot) {
        TypedQuery<PersonaBadgeEntity> query = this.entityManager.createNamedQuery("PersonaBadgeEntity" +
                ".findBadgeInSlotForPersona", PersonaBadgeEntity.class)
                .setParameter("personaId", personaId)
                .setParameter("slot", slot);
        List<PersonaBadgeEntity> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}
