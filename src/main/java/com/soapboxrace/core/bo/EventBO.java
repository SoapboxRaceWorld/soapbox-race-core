/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

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
    private TokenSessionDAO tokenSessionDao;

    @EJB
    private PersonaBO personaBO;

    public List<EventEntity> availableAtLevel(Long personaId) {
        PersonaEntity personaEntity = personaDao.findById(personaId);
        return eventDao.findByLevel(personaEntity.getLevel());
    }

    public void createEventDataSession(Long personaId, Long eventSessionId) {
        EventSessionEntity eventSessionEntity = findEventSessionById(eventSessionId);
        EventDataEntity eventDataEntity = new EventDataEntity();
        eventDataEntity.setPersonaId(personaId);
        eventDataEntity.setEventSessionId(eventSessionId);
        eventDataEntity.setEvent(eventSessionEntity.getEvent());
        eventDataDao.insert(eventDataEntity);
    }

    public EventSessionEntity createEventSession(String securityToken, int eventId) {
        EventEntity eventEntity = eventDao.findById(eventId);
        if (eventEntity == null) {
            return null;
        }

        TokenSessionEntity tokenSessionEntity = tokenSessionDao.findById(securityToken);

        if (tokenSessionEntity == null) {
            return null;
        }

        Long activePersonaId = tokenSessionEntity.getActivePersonaId();

        if (activePersonaId.equals(0L)) {
            return null;
        }

        CarSlotEntity defaultCarEntity = personaBO.getDefaultCarEntity(activePersonaId);
        OwnedCarEntity ownedCarEntity = defaultCarEntity.getOwnedCar();
        CustomCarEntity customCarEntity = ownedCarEntity.getCustomCar();

        // only check restriction on non-open events
        if (eventEntity.getCarClassHash() != 607077938) {
            if (customCarEntity.getCarClassHash() != eventEntity.getCarClassHash()) {
                // The client UI does not allow you to join events outside your current car's class
                throw new EngineException(EngineExceptionCode.CarDataInvalid);
            }
        }

        EventSessionEntity eventSessionEntity = new EventSessionEntity();
        eventSessionEntity.setEvent(eventEntity);
        eventSessionEntity.setStarted(System.currentTimeMillis());
        eventSessionDao.insert(eventSessionEntity);
        return eventSessionEntity;
    }

    public EventSessionEntity findEventSessionById(Long id) {
        return eventSessionDao.findById(id);
    }
}
