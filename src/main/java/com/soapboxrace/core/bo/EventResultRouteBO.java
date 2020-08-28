/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.EventDataDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.EventDataEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppEvent;
import com.soapboxrace.jaxb.http.ArrayOfRouteEntrantResult;
import com.soapboxrace.jaxb.http.RouteArbitrationPacket;
import com.soapboxrace.jaxb.http.RouteEntrantResult;
import com.soapboxrace.jaxb.http.RouteEventResult;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeRouteEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_RouteEntrantResultType;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class EventResultRouteBO extends EventResultBO<RouteArbitrationPacket, RouteEventResult> {

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

    @EJB
    private DNFTimerBO dnfTimerBO;

    protected RouteEventResult handleInternal(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                              RouteArbitrationPacket routeArbitrationPacket) {
        Long eventSessionId = eventSessionEntity.getId();

        EventDataEntity eventDataEntity = eventDataDao.findByPersonaAndEventSessionId(activePersonaId, eventSessionId);

        if (eventDataEntity.getFinishReason() != 0) {
            throw new EngineException("Session already completed.", EngineExceptionCode.SecurityKickedArbitration, true);
        }

        prepareBasicEventData(eventDataEntity, activePersonaId, routeArbitrationPacket);

        eventDataEntity.setBestLapDurationInMilliseconds(routeArbitrationPacket.getBestLapDurationInMilliseconds());
        eventDataEntity.setFractionCompleted(routeArbitrationPacket.getFractionCompleted());
        eventDataEntity.setLongestJumpDurationInMilliseconds(routeArbitrationPacket.getLongestJumpDurationInMilliseconds());
        eventDataEntity.setNumberOfCollisions(routeArbitrationPacket.getNumberOfCollisions());
        eventDataEntity.setPerfectStart(routeArbitrationPacket.getPerfectStart());
        eventDataEntity.setSumOfJumpsDurationInMilliseconds(routeArbitrationPacket.getSumOfJumpsDurationInMilliseconds());
        eventDataEntity.setTopSpeed(routeArbitrationPacket.getTopSpeed());
        eventSessionEntity.setEnded(System.currentTimeMillis());

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

        PersonaEntity personaEntity = personaDAO.find(activePersonaId);
        AchievementTransaction transaction = achievementBO.createTransaction(activePersonaId);
        RouteEventResult routeEventResult = new RouteEventResult();
        routeEventResult.setAccolades(rewardRouteBO.getAccolades(activePersonaId, routeArbitrationPacket,
                eventDataEntity, eventSessionEntity, transaction));
        routeEventResult.setDurability(carDamageBO.induceCarDamage(activePersonaId, routeArbitrationPacket,
                eventDataEntity.getEvent()));
        routeEventResult.setEntrants(arrayOfRouteEntrantResult);
        routeEventResult.setEventId(eventDataEntity.getEvent().getId());
        routeEventResult.setEventSessionId(eventSessionId);
        routeEventResult.setPersonaId(activePersonaId);
        prepareRaceAgain(eventSessionEntity, routeEventResult);
        sendXmppPacket(eventSessionEntity, activePersonaId, routeArbitrationPacket);

        updateEventAchievements(eventDataEntity, eventSessionEntity, activePersonaId, routeArbitrationPacket, transaction);
        achievementBO.commitTransaction(personaEntity, transaction);

        eventSessionDao.update(eventSessionEntity);
        eventDataDao.update(eventDataEntity);

        if (eventSessionEntity.getLobby() != null && !eventSessionEntity.getLobby().getIsPrivate()) {
            matchmakingBO.resetIgnoredEvents(activePersonaId);
        }

        return routeEventResult;
    }

    private void sendXmppPacket(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                RouteArbitrationPacket routeArbitrationPacket) {
        XMPP_RouteEntrantResultType xmppRouteResult = new XMPP_RouteEntrantResultType();
        xmppRouteResult.setBestLapDurationInMilliseconds(routeArbitrationPacket.getBestLapDurationInMilliseconds());
        xmppRouteResult.setEventDurationInMilliseconds(routeArbitrationPacket.getEventDurationInMilliseconds());
        xmppRouteResult.setEventSessionId(eventSessionEntity.getId());
        xmppRouteResult.setFinishReason(routeArbitrationPacket.getFinishReason());
        xmppRouteResult.setPersonaId(activePersonaId);
        xmppRouteResult.setRanking(routeArbitrationPacket.getRank());
        xmppRouteResult.setTopSpeed(routeArbitrationPacket.getTopSpeed());

        XMPP_ResponseTypeRouteEntrantResult routeEntrantResultResponse = new XMPP_ResponseTypeRouteEntrantResult();
        routeEntrantResultResponse.setRouteEntrantResult(xmppRouteResult);

        for (EventDataEntity racer : eventDataDao.getRacers(eventSessionEntity.getId())) {
            if (!racer.getPersonaId().equals(activePersonaId)) {
                XmppEvent xmppEvent = new XmppEvent(racer.getPersonaId(), openFireSoapBoxCli);
                xmppEvent.sendRaceEnd(routeEntrantResultResponse);
                if (routeArbitrationPacket.getFinishReason() == 22 && routeArbitrationPacket.getRank() == 1 && eventSessionEntity.getEvent().isDnfEnabled()) {
                    xmppEvent.sendEventTimingOut(eventSessionEntity);
                    dnfTimerBO.scheduleDNF(eventSessionEntity, racer.getPersonaId());
                }
            }
        }
    }

}
