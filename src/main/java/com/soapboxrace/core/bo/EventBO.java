/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.dao.EventDataDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.*;
import org.hibernate.Hibernate;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Objects;

@Stateless
public class EventBO {

    @EJB
    private EventDAO eventDao;

    @EJB
    private EventSessionDAO eventSessionDao;

    @EJB
    private EventDataDAO eventDataDao;

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private PersonaBO personaBO;

    public List<EventEntity> availableAtLevel(Long personaId) {
        PersonaEntity personaEntity = personaDao.find(personaId);
        return eventDao.findByLevel(personaEntity.getLevel());
    }

    public void createEventDataSession(Long personaId, Long eventSessionId) {
        CarEntity carEntity = personaBO.getDefaultCarEntity(personaId);

        EventSessionEntity eventSessionEntity = eventSessionDao.find(eventSessionId);
        EventDataEntity eventDataEntity = new EventDataEntity();
        eventDataEntity.setPersonaId(personaId);
        eventDataEntity.setEventSessionId(eventSessionId);
        eventDataEntity.setEvent(eventSessionEntity.getEvent());
        eventDataEntity.setServerTimeStarted(System.currentTimeMillis());
        eventDataEntity.setCarClassHash(carEntity.getCarClassHash());
        eventDataEntity.setCarRating(carEntity.getRating());
        eventDataDao.insert(eventDataEntity);
    }

    public EventSessionEntity createEventSession(TokenSessionEntity tokenSessionEntity, int eventId) {
        Objects.requireNonNull(tokenSessionEntity);

        EventEntity eventEntity = eventDao.find(eventId);
        if (eventEntity == null) {
            return null;
        }

        Long activePersonaId = tokenSessionEntity.getActivePersonaId();

        if (activePersonaId.equals(0L)) {
            return null;
        }

        CarEntity carEntity = personaBO.getDefaultCarEntity(activePersonaId);

        // only check restriction on non-open events
        if (eventEntity.getCarClassHash() != 607077938) {
            if (carEntity.getCarClassHash() != eventEntity.getCarClassHash()) {
                // The client UI does not allow you to join events outside your current car's class
                throw new EngineException(EngineExceptionCode.CarDataInvalid, true);
            }
        }

        EventSessionEntity eventSessionEntity = new EventSessionEntity();
        eventSessionEntity.setEvent(eventEntity);
        eventSessionEntity.setStarted(System.currentTimeMillis());
        eventSessionDao.insert(eventSessionEntity);
        return eventSessionEntity;
    }

    public EventSessionEntity findEventSessionById(Long id) {
        EventSessionEntity eventSession = eventSessionDao.find(id);
        Hibernate.initialize(eventSession.getEvent().getSingleplayerRewardConfig());
        Hibernate.initialize(eventSession.getEvent().getMultiplayerRewardConfig());
        Hibernate.initialize(eventSession.getEvent().getPrivateRewardConfig());
        return eventSession;
    }
}
