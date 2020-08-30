/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.LongKeyedDAO;
import com.soapboxrace.core.jpa.EventDataEntity;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class EventDataDAO extends LongKeyedDAO<EventDataEntity> {

    public EventDataDAO() {
        super(EventDataEntity.class);
    }

    public List<EventDataEntity> getRacers(Long eventSessionId) {
        TypedQuery<EventDataEntity> query = entityManager.createNamedQuery("EventDataEntity.getRacers",
                EventDataEntity.class);
        query.setParameter("eventSessionId", eventSessionId);
        return query.getResultList();
    }

    public EventDataEntity findByPersonaAndEventSessionId(Long personaId, Long eventSessionId) {
        TypedQuery<EventDataEntity> query = entityManager.createNamedQuery("EventDataEntity" +
                ".findByPersonaAndEventSessionId", EventDataEntity.class);
        query.setParameter("personaId", personaId);
        query.setParameter("eventSessionId", eventSessionId);

        List<EventDataEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

}
