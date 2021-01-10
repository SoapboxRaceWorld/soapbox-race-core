/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.CarDAO;
import com.soapboxrace.core.dao.EventDataDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.CarEntity;
import com.soapboxrace.core.jpa.EventDataEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.ExitPath;
import com.soapboxrace.jaxb.http.PursuitArbitrationPacket;
import com.soapboxrace.jaxb.http.PursuitEventResult;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class EventResultPursuitBO extends EventResultBO<PursuitArbitrationPacket, PursuitEventResult> {

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
    private CarDAO carDAO;

    @EJB
    private PersonaBO personaBO;

    protected PursuitEventResult handleInternal(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                                PursuitArbitrationPacket pursuitArbitrationPacket) {
        Long eventSessionId = eventSessionEntity.getId();

        EventDataEntity eventDataEntity = eventDataDao.findByPersonaAndEventSessionId(activePersonaId, eventSessionId);

        if (eventDataEntity.getFinishReason() != 0) {
            throw new EngineException("Session already completed.", EngineExceptionCode.SecurityKickedArbitration, true);
        }

        prepareBasicEventData(eventDataEntity, activePersonaId, pursuitArbitrationPacket);
        eventDataEntity.setCopsDeployed(pursuitArbitrationPacket.getCopsDeployed());
        eventDataEntity.setCopsDisabled(pursuitArbitrationPacket.getCopsDisabled());
        eventDataEntity.setCopsRammed(pursuitArbitrationPacket.getCopsRammed());
        eventDataEntity.setCostToState(pursuitArbitrationPacket.getCostToState());
        eventDataEntity.setHeat(pursuitArbitrationPacket.getHeat());
        eventDataEntity.setInfractions(pursuitArbitrationPacket.getInfractions());
        eventDataEntity.setLongestJumpDurationInMilliseconds(pursuitArbitrationPacket.getLongestJumpDurationInMilliseconds());
        eventDataEntity.setRoadBlocksDodged(pursuitArbitrationPacket.getRoadBlocksDodged());
        eventDataEntity.setSpikeStripsDodged(pursuitArbitrationPacket.getSpikeStripsDodged());
        eventDataEntity.setSumOfJumpsDurationInMilliseconds(pursuitArbitrationPacket.getSumOfJumpsDurationInMilliseconds());
        eventDataEntity.setTopSpeed(pursuitArbitrationPacket.getTopSpeed());
        eventSessionEntity.setEnded(System.currentTimeMillis());

        pursuitArbitrationPacket.setRank(1); // there's only ever 1 player, and the game sets rank to 0... idk why

        boolean isBusted = pursuitArbitrationPacket.getFinishReason() == 266;

        PersonaEntity personaEntity = personaDAO.find(activePersonaId);
        AchievementTransaction transaction = achievementBO.createTransaction(activePersonaId);
        PursuitEventResult pursuitEventResult = new PursuitEventResult();
        pursuitEventResult.setAccolades(rewardPursuitBO.getAccolades(activePersonaId, pursuitArbitrationPacket
                , eventDataEntity, eventSessionEntity, transaction));
        pursuitEventResult.setDurability(carDamageBO.induceCarDamage(activePersonaId, pursuitArbitrationPacket,
                eventDataEntity.getEvent()));
        pursuitEventResult.setEventId(eventDataEntity.getEvent().getId());
        pursuitEventResult.setEventSessionId(eventSessionId);
        pursuitEventResult.setExitPath(ExitPath.EXIT_TO_FREEROAM);
        pursuitEventResult.setHeat(isBusted ? 1 : pursuitArbitrationPacket.getHeat());
        pursuitEventResult.setInviteLifetimeInMilliseconds(0);
        pursuitEventResult.setLobbyInviteId(0);
        pursuitEventResult.setPersonaId(activePersonaId);

        if (!isBusted) {
            updateEventAchievements(eventDataEntity, eventSessionEntity, activePersonaId, pursuitArbitrationPacket, transaction);
        }

        achievementBO.commitTransaction(personaEntity, transaction);

        CarEntity carEntity = personaBO.getDefaultCarEntity(activePersonaId);
        carEntity.setHeat(isBusted ? 1 : pursuitArbitrationPacket.getHeat());
        carDAO.update(carEntity);
        eventDataDao.update(eventDataEntity);
        eventSessionDao.update(eventSessionEntity);

        return pursuitEventResult;
    }

}
