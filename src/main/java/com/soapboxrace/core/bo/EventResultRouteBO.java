/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.AchievementEventContext;
import com.soapboxrace.core.dao.EventDataDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.EventDataEntity;
import com.soapboxrace.core.jpa.EventMode;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppEvent;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeRouteEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_RouteEntrantResultType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;

@Stateless
public class EventResultRouteBO {

    @EJB
    private EventSessionDAO eventSessionDao;

    @EJB
    private EventDataDAO eventDataDao;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @EJB
    private RewardRouteBO rewardRouteBO;

    @EJB
    private CarDamageBO carDamageBO;

    @EJB
    private AchievementBO achievementBO;

    public RouteEventResult handleRaceEnd(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                          RouteArbitrationPacket routeArbitrationPacket) {
        Long eventSessionId = eventSessionEntity.getId();
        eventSessionEntity.setEnded(System.currentTimeMillis());

        eventSessionDao.update(eventSessionEntity);

        EventDataEntity eventDataEntity = eventDataDao.findByPersonaAndEventSessionId(activePersonaId, eventSessionId);

        if (eventDataEntity.getFinishReason() != 0) {
            throw new EngineException("Session already completed.", EngineExceptionCode.SecurityKickedArbitration);
        }

        updateEventDataEntity(eventDataEntity, routeArbitrationPacket);

        // RouteArbitrationPacket
        eventDataEntity.setBestLapDurationInMilliseconds(routeArbitrationPacket.getBestLapDurationInMilliseconds());
        eventDataEntity.setFractionCompleted(routeArbitrationPacket.getFractionCompleted());
        eventDataEntity.setLongestJumpDurationInMilliseconds(routeArbitrationPacket.getLongestJumpDurationInMilliseconds());
        eventDataEntity.setNumberOfCollisions(routeArbitrationPacket.getNumberOfCollisions());
        eventDataEntity.setPerfectStart(routeArbitrationPacket.getPerfectStart());
        eventDataEntity.setSumOfJumpsDurationInMilliseconds(routeArbitrationPacket.getSumOfJumpsDurationInMilliseconds());
        eventDataEntity.setTopSpeed(routeArbitrationPacket.getTopSpeed());

        eventDataEntity.setEventModeId(eventDataEntity.getEvent().getEventModeId());
        eventDataEntity.setPersonaId(activePersonaId);
        eventDataDao.update(eventDataEntity);

        ArrayOfRouteEntrantResult arrayOfRouteEntrantResult = new ArrayOfRouteEntrantResult();
        for (EventDataEntity racer : eventDataDao.getRacers(eventSessionId)) {
            RouteEntrantResult routeEntrantResult = new RouteEntrantResult();
            routeEntrantResult.setBestLapDurationInMilliseconds(racer.getBestLapDurationInMilliseconds());
            routeEntrantResult.setEventDurationInMilliseconds(racer.getEventDurationInMilliseconds());
            routeEntrantResult.setEventSessionId(eventSessionId);
            routeEntrantResult.setFinishReason(racer.getFinishReason());
            routeEntrantResult.setPersonaId(racer.getPersonaId());
            routeEntrantResult.setRanking(racer.getRank());
            routeEntrantResult.setTopSpeed(racer.getTopSpeed());
            arrayOfRouteEntrantResult.getRouteEntrantResult().add(routeEntrantResult);
        }

        RouteEventResult routeEventResult = new RouteEventResult();
        routeEventResult.setAccolades(rewardRouteBO.getRouteAccolades(activePersonaId, routeArbitrationPacket,
                eventSessionEntity));
        routeEventResult.setDurability(carDamageBO.updateDamageCar(activePersonaId, routeArbitrationPacket,
                routeArbitrationPacket.getNumberOfCollisions()));
        routeEventResult.setEntrants(arrayOfRouteEntrantResult);
        routeEventResult.setEventId(eventDataEntity.getEvent().getId());
        routeEventResult.setEventSessionId(eventSessionId);
        routeEventResult.setExitPath(eventSessionEntity.getLobby() == null ? ExitPath.EXIT_TO_FREEROAM :
                ExitPath.EXIT_TO_LOBBY);
        routeEventResult.setInviteLifetimeInMilliseconds(0);
        routeEventResult.setLobbyInviteId(0);
        routeEventResult.setPersonaId(activePersonaId);
        sendXmppPacket(eventSessionId, activePersonaId, routeArbitrationPacket);

        PersonaEntity personaEntity = personaDAO.findById(activePersonaId);

        achievementBO.updateAchievements(personaEntity, "EVENT", new HashMap<String, Object>() {{
            put("persona", personaEntity);
            put("event", eventDataEntity.getEvent());
            put("eventData", eventDataEntity);
            put("eventSession", eventSessionEntity);
            put("eventContext", new AchievementEventContext(
                    EventMode.fromId(eventDataEntity.getEvent().getEventModeId()),
                    routeArbitrationPacket,
                    eventSessionEntity));
        }});

        return routeEventResult;
    }

    private void updateEventDataEntity(EventDataEntity eventDataEntity, ArbitrationPacket arbitrationPacket) {
        eventDataEntity.setAlternateEventDurationInMilliseconds(arbitrationPacket.getAlternateEventDurationInMilliseconds());
        eventDataEntity.setCarId(arbitrationPacket.getCarId());
        eventDataEntity.setEventDurationInMilliseconds(arbitrationPacket.getEventDurationInMilliseconds());
        eventDataEntity.setFinishReason(arbitrationPacket.getFinishReason());
        eventDataEntity.setHacksDetected(arbitrationPacket.getHacksDetected());
        eventDataEntity.setRank(arbitrationPacket.getRank());
    }

    private void sendXmppPacket(Long eventSessionId, Long activePersonaId,
                                RouteArbitrationPacket routeArbitrationPacket) {
        XMPP_RouteEntrantResultType xmppRouteResult = new XMPP_RouteEntrantResultType();
        xmppRouteResult.setBestLapDurationInMilliseconds(routeArbitrationPacket.getBestLapDurationInMilliseconds());
        xmppRouteResult.setEventDurationInMilliseconds(routeArbitrationPacket.getEventDurationInMilliseconds());
        xmppRouteResult.setEventSessionId(eventSessionId);
        xmppRouteResult.setFinishReason(routeArbitrationPacket.getFinishReason());
        xmppRouteResult.setPersonaId(activePersonaId);
        xmppRouteResult.setRanking(routeArbitrationPacket.getRank());
        xmppRouteResult.setTopSpeed(routeArbitrationPacket.getTopSpeed());

        XMPP_ResponseTypeRouteEntrantResult routeEntrantResultResponse = new XMPP_ResponseTypeRouteEntrantResult();
        routeEntrantResultResponse.setRouteEntrantResult(xmppRouteResult);

        for (EventDataEntity racer : eventDataDao.getRacers(eventSessionId)) {
            if (!racer.getPersonaId().equals(activePersonaId)) {
                XmppEvent xmppEvent = new XmppEvent(racer.getPersonaId(), openFireSoapBoxCli);
                xmppEvent.sendRaceEnd(routeEntrantResultResponse);
                if (routeArbitrationPacket.getRank() == 1) {
                    xmppEvent.sendEventTimingOut(eventSessionId);
                }
            }
        }
    }

}
