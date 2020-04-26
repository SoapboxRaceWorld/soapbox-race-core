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
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppEvent;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.xmpp.XMPP_DragEntrantResultType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeDragEntrantResult;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;

@Stateless
public class EventResultDragBO {

    @EJB
    private EventSessionDAO eventSessionDao;

    @EJB
    private EventDataDAO eventDataDao;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @EJB
    private RewardDragBO rewardDragBO;

    @EJB
    private CarDamageBO carDamageBO;

    @EJB
    private AchievementBO achievementBO;

    @EJB
    private LobbyBO lobbyBO;

    @EJB
    private DNFTimerBO dnfTimerBO;

    public DragEventResult handleDragEnd(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                         DragArbitrationPacket dragArbitrationPacket) {
        Long eventSessionId = eventSessionEntity.getId();

        XMPP_DragEntrantResultType xmppDragResult = new XMPP_DragEntrantResultType();
        xmppDragResult.setEventDurationInMilliseconds(dragArbitrationPacket.getEventDurationInMilliseconds());
        xmppDragResult.setEventSessionId(eventSessionId);
        xmppDragResult.setFinishReason(dragArbitrationPacket.getFinishReason());
        xmppDragResult.setPersonaId(activePersonaId);
        xmppDragResult.setRanking(dragArbitrationPacket.getRank());
        xmppDragResult.setTopSpeed(dragArbitrationPacket.getTopSpeed());

        XMPP_ResponseTypeDragEntrantResult dragEntrantResultResponse = new XMPP_ResponseTypeDragEntrantResult();
        dragEntrantResultResponse.setDragEntrantResult(xmppDragResult);

        EventDataEntity eventDataEntity = eventDataDao.findByPersonaAndEventSessionId(activePersonaId, eventSessionId);

        if (eventDataEntity.getFinishReason() != 0) {
            throw new EngineException("Session already completed.", EngineExceptionCode.SecurityKickedArbitration, true);
        }

        eventDataEntity.setAlternateEventDurationInMilliseconds(dragArbitrationPacket.getAlternateEventDurationInMilliseconds());
        eventDataEntity.setCarId(dragArbitrationPacket.getCarId());
        eventDataEntity.setEventDurationInMilliseconds(dragArbitrationPacket.getEventDurationInMilliseconds());
        eventDataEntity.setEventModeId(eventDataEntity.getEvent().getEventModeId());
        eventDataEntity.setFinishReason(dragArbitrationPacket.getFinishReason());
        eventDataEntity.setFractionCompleted(dragArbitrationPacket.getFractionCompleted());
        eventDataEntity.setHacksDetected(dragArbitrationPacket.getHacksDetected());
        eventDataEntity.setLongestJumpDurationInMilliseconds(dragArbitrationPacket.getLongestJumpDurationInMilliseconds());
        eventDataEntity.setNumberOfCollisions(dragArbitrationPacket.getNumberOfCollisions());
        eventDataEntity.setPerfectStart(dragArbitrationPacket.getPerfectStart());
        eventDataEntity.setPersonaId(activePersonaId);
        eventDataEntity.setRank(dragArbitrationPacket.getRank());
        eventDataEntity.setSumOfJumpsDurationInMilliseconds(dragArbitrationPacket.getSumOfJumpsDurationInMilliseconds());
        eventDataEntity.setTopSpeed(dragArbitrationPacket.getTopSpeed());
        eventSessionEntity.setEnded(System.currentTimeMillis());

        eventSessionDao.update(eventSessionEntity);
        eventDataDao.update(eventDataEntity);

        ArrayOfDragEntrantResult arrayOfDragEntrantResult = new ArrayOfDragEntrantResult();
        for (EventDataEntity racer : eventDataDao.getRacers(eventSessionId)) {
            DragEntrantResult dragEntrantResult = new DragEntrantResult();
            dragEntrantResult.setEventDurationInMilliseconds(racer.getEventDurationInMilliseconds());
            dragEntrantResult.setEventSessionId(eventSessionId);
            dragEntrantResult.setFinishReason(racer.getFinishReason());
            dragEntrantResult.setPersonaId(racer.getPersonaId());
            dragEntrantResult.setRanking(racer.getRank());
            dragEntrantResult.setTopSpeed(racer.getTopSpeed());
            arrayOfDragEntrantResult.getDragEntrantResult().add(dragEntrantResult);

            if (!racer.getPersonaId().equals(activePersonaId)) {
                XmppEvent xmppEvent = new XmppEvent(racer.getPersonaId(), openFireSoapBoxCli);
                xmppEvent.sendDragEnd(dragEntrantResultResponse);
                if (dragArbitrationPacket.getRank() == 1) {
                    xmppEvent.sendEventTimingOut(eventSessionEntity);
                    dnfTimerBO.scheduleDNF(eventSessionEntity, racer.getPersonaId());
                }
            }
        }

        DragEventResult dragEventResult = new DragEventResult();
        dragEventResult.setAccolades(rewardDragBO.getDragAccolades(activePersonaId, dragArbitrationPacket,
                eventSessionEntity));
        dragEventResult.setDurability(carDamageBO.induceCarDamage(activePersonaId, dragArbitrationPacket,
                dragArbitrationPacket.getNumberOfCollisions()));
        dragEventResult.setEntrants(arrayOfDragEntrantResult);
        dragEventResult.setEventId(eventDataEntity.getEvent().getId());
        dragEventResult.setEventSessionId(eventSessionId);
        dragEventResult.setExitPath(eventSessionEntity.getLobby() == null ? ExitPath.EXIT_TO_FREEROAM :
                ExitPath.EXIT_TO_LOBBY);
        dragEventResult.setInviteLifetimeInMilliseconds(0);
        dragEventResult.setPersonaId(activePersonaId);

        if (eventSessionEntity.getLobby() == null) {
            dragEventResult.setLobbyInviteId(0);
        } else if (eventSessionEntity.getNextLobby() != null) {
            dragEventResult.setLobbyInviteId(eventSessionEntity.getNextLobby().getId());
            dragEventResult.setInviteLifetimeInMilliseconds(eventSessionEntity.getNextLobby()
                    .getLobbyCountdownInMilliseconds(eventSessionEntity.getEvent().getLobbyCountdownTime()));
        } else {
            LobbyEntity oldLobby = eventSessionEntity.getLobby();
            LobbyEntity newLobby = lobbyBO.createLobby(
                    personaDAO.findById(oldLobby.getPersonaId()),
                    oldLobby.getEvent().getId(),
                    oldLobby.getEvent().getCarClassHash(),
                    oldLobby.getIsPrivate());
            eventSessionEntity.setNextLobby(newLobby);
            eventSessionDao.update(eventSessionEntity);
            dragEventResult.setLobbyInviteId(newLobby.getId());
            dragEventResult.setInviteLifetimeInMilliseconds(eventSessionEntity.getEvent().getLobbyCountdownTime());
        }
        PersonaEntity personaEntity = personaDAO.findById(activePersonaId);

        achievementBO.updateAchievements(personaEntity, "EVENT", new HashMap<String, Object>() {{
            put("persona", personaEntity);
            put("event", eventDataEntity.getEvent());
            put("eventData", eventDataEntity);
            put("eventSession", eventSessionEntity);
            put("eventContext", new AchievementEventContext(
                    EventMode.fromId(eventDataEntity.getEvent().getEventModeId()),
                    dragArbitrationPacket,
                    eventSessionEntity));
        }});

        return dragEventResult;
    }

}
