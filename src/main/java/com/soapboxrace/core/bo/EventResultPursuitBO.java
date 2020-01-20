/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.AchievementEventContext;
import com.soapboxrace.core.dao.EventDataDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.OwnedCarDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.ExitPath;
import com.soapboxrace.jaxb.http.PursuitArbitrationPacket;
import com.soapboxrace.jaxb.http.PursuitEventResult;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;

@Stateless
public class EventResultPursuitBO {

    @EJB
    private EventSessionDAO eventSessionDao;

    @EJB
    private EventDataDAO eventDataDao;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private RewardPursuitBO rewardPursuitBO;

    @EJB
    private CarDamageBO carDamageBO;

    @EJB
    private AchievementBO achievementBO;

    @EJB
    private OwnedCarDAO ownedCarDAO;

    @EJB
    private PersonaBO personaBO;

    public PursuitEventResult handlePursuitEnd(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                               PursuitArbitrationPacket pursuitArbitrationPacket,
                                               Boolean isBusted) {
        Long eventSessionId = eventSessionEntity.getId();
        eventSessionEntity.setEnded(System.currentTimeMillis());

        eventSessionDao.update(eventSessionEntity);

        EventDataEntity eventDataEntity = eventDataDao.findByPersonaAndEventSessionId(activePersonaId, eventSessionId);

        if (eventDataEntity.getFinishReason() != 0) {
            throw new EngineException("Session already completed.", EngineExceptionCode.SecurityKickedArbitration);
        }

        eventDataEntity.setAlternateEventDurationInMilliseconds(pursuitArbitrationPacket.getAlternateEventDurationInMilliseconds());
        eventDataEntity.setCarId(pursuitArbitrationPacket.getCarId());
        eventDataEntity.setCopsDeployed(pursuitArbitrationPacket.getCopsDeployed());
        eventDataEntity.setCopsDisabled(pursuitArbitrationPacket.getCopsDisabled());
        eventDataEntity.setCopsRammed(pursuitArbitrationPacket.getCopsRammed());
        eventDataEntity.setCostToState(pursuitArbitrationPacket.getCostToState());
        eventDataEntity.setEventDurationInMilliseconds(pursuitArbitrationPacket.getEventDurationInMilliseconds());
        eventDataEntity.setEventModeId(eventDataEntity.getEvent().getEventModeId());
        eventDataEntity.setFinishReason(pursuitArbitrationPacket.getFinishReason());
        eventDataEntity.setHacksDetected(pursuitArbitrationPacket.getHacksDetected());
        eventDataEntity.setHeat(pursuitArbitrationPacket.getHeat());
        eventDataEntity.setInfractions(pursuitArbitrationPacket.getInfractions());
        eventDataEntity.setLongestJumpDurationInMilliseconds(pursuitArbitrationPacket.getLongestJumpDurationInMilliseconds());
        eventDataEntity.setPersonaId(activePersonaId);
        eventDataEntity.setRoadBlocksDodged(pursuitArbitrationPacket.getRoadBlocksDodged());
        eventDataEntity.setSpikeStripsDodged(pursuitArbitrationPacket.getSpikeStripsDodged());
        eventDataEntity.setSumOfJumpsDurationInMilliseconds(pursuitArbitrationPacket.getSumOfJumpsDurationInMilliseconds());
        eventDataEntity.setTopSpeed(pursuitArbitrationPacket.getTopSpeed());
        eventDataDao.update(eventDataEntity);

        pursuitArbitrationPacket.setRank(1); // there's only ever 1 player, and the game sets rank to 0... idk why

        PursuitEventResult pursuitEventResult = new PursuitEventResult();
        pursuitEventResult.setAccolades(rewardPursuitBO.getPursuitAccolades(activePersonaId, pursuitArbitrationPacket
                , eventSessionEntity, isBusted));
        pursuitEventResult.setDurability(carDamageBO.updateDamageCar(activePersonaId, pursuitArbitrationPacket, 0));
        pursuitEventResult.setEventId(eventDataEntity.getEvent().getId());
        pursuitEventResult.setEventSessionId(eventSessionId);
        pursuitEventResult.setExitPath(ExitPath.EXIT_TO_FREEROAM);
        pursuitEventResult.setHeat(isBusted ? 1 : pursuitArbitrationPacket.getHeat());
        pursuitEventResult.setInviteLifetimeInMilliseconds(0);
        pursuitEventResult.setLobbyInviteId(0);
        pursuitEventResult.setPersonaId(activePersonaId);

        if (!isBusted) {
            PersonaEntity personaEntity = personaDAO.findById(activePersonaId);

            achievementBO.updateAchievements(personaEntity, "EVENT", new HashMap<String, Object>() {{
                put("persona", personaEntity);
                put("event", eventDataEntity.getEvent());
                put("eventData", eventDataEntity);
                put("eventSession", eventSessionEntity);
                put("eventContext", new AchievementEventContext(
                        EventMode.fromId(eventDataEntity.getEvent().getEventModeId()),
                        pursuitArbitrationPacket,
                        eventSessionEntity));
            }});
        }

        OwnedCarEntity ownedCarEntity = personaBO.getDefaultCarEntity(activePersonaId).getOwnedCar();
        ownedCarEntity.setHeat(isBusted ? 1 : pursuitArbitrationPacket.getHeat());
        ownedCarDAO.update(ownedCarEntity);

        return pursuitEventResult;
    }

}
