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
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeTeamEscapeEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_TeamEscapeEntrantResultType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;

@Stateless
public class EventResultTeamEscapeBO {

    @EJB
    private EventSessionDAO eventSessionDao;

    @EJB
    private EventDataDAO eventDataDao;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @EJB
    private RewardTeamEscapeBO rewardTeamEscapeBO;

    @EJB
    private CarDamageBO carDamageBO;

    @EJB
    private AchievementBO achievementBO;

    @EJB
    private LobbyBO lobbyBO;

    @EJB
    private DNFTimerBO dnfTimerBO;

    public TeamEscapeEventResult handleTeamEscapeEnd(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                                     TeamEscapeArbitrationPacket teamEscapeArbitrationPacket) {
        Long eventSessionId = eventSessionEntity.getId();

        XMPP_TeamEscapeEntrantResultType xmppTeamEscapeResult = new XMPP_TeamEscapeEntrantResultType();
        xmppTeamEscapeResult.setEventDurationInMilliseconds(teamEscapeArbitrationPacket.getEventDurationInMilliseconds());
        xmppTeamEscapeResult.setEventSessionId(eventSessionId);
        xmppTeamEscapeResult.setFinishReason(teamEscapeArbitrationPacket.getFinishReason());
        xmppTeamEscapeResult.setPersonaId(activePersonaId);

        XMPP_ResponseTypeTeamEscapeEntrantResult teamEscapeEntrantResultResponse =
                new XMPP_ResponseTypeTeamEscapeEntrantResult();
        teamEscapeEntrantResultResponse.setTeamEscapeEntrantResult(xmppTeamEscapeResult);

        EventDataEntity eventDataEntity = eventDataDao.findByPersonaAndEventSessionId(activePersonaId, eventSessionId);

        if (eventDataEntity.getFinishReason() != 0) {
            throw new EngineException("Session already completed.", EngineExceptionCode.SecurityKickedArbitration, true);
        }

        eventDataEntity.setAlternateEventDurationInMilliseconds(teamEscapeArbitrationPacket.getAlternateEventDurationInMilliseconds());
        eventDataEntity.setBustedCount(teamEscapeArbitrationPacket.getBustedCount());
        eventDataEntity.setCarId(teamEscapeArbitrationPacket.getCarId());
        eventDataEntity.setCopsDeployed(teamEscapeArbitrationPacket.getCopsDeployed());
        eventDataEntity.setCopsDisabled(teamEscapeArbitrationPacket.getCopsDisabled());
        eventDataEntity.setCopsRammed(teamEscapeArbitrationPacket.getCopsRammed());
        eventDataEntity.setCostToState(teamEscapeArbitrationPacket.getCostToState());
        eventDataEntity.setDistanceToFinish(teamEscapeArbitrationPacket.getDistanceToFinish());
        eventDataEntity.setEventDurationInMilliseconds(teamEscapeArbitrationPacket.getEventDurationInMilliseconds());
        eventDataEntity.setEventModeId(eventDataEntity.getEvent().getEventModeId());
        eventDataEntity.setFinishReason(teamEscapeArbitrationPacket.getFinishReason());
        eventDataEntity.setFractionCompleted(teamEscapeArbitrationPacket.getFractionCompleted());
        eventDataEntity.setHacksDetected(teamEscapeArbitrationPacket.getHacksDetected());
        eventDataEntity.setInfractions(teamEscapeArbitrationPacket.getInfractions());
        eventDataEntity.setLongestJumpDurationInMilliseconds(teamEscapeArbitrationPacket.getLongestJumpDurationInMilliseconds());
        eventDataEntity.setNumberOfCollisions(teamEscapeArbitrationPacket.getNumberOfCollisions());
        eventDataEntity.setPerfectStart(teamEscapeArbitrationPacket.getPerfectStart());
        eventDataEntity.setRank(teamEscapeArbitrationPacket.getRank());
        eventDataEntity.setPersonaId(activePersonaId);
        eventDataEntity.setRoadBlocksDodged(teamEscapeArbitrationPacket.getRoadBlocksDodged());
        eventDataEntity.setSpikeStripsDodged(teamEscapeArbitrationPacket.getSpikeStripsDodged());
        eventDataEntity.setSumOfJumpsDurationInMilliseconds(teamEscapeArbitrationPacket.getSumOfJumpsDurationInMilliseconds());
        eventDataEntity.setTopSpeed(teamEscapeArbitrationPacket.getTopSpeed());
        eventSessionEntity.setEnded(System.currentTimeMillis());

        eventSessionDao.update(eventSessionEntity);
        eventDataDao.update(eventDataEntity);

        ArrayOfTeamEscapeEntrantResult arrayOfTeamEscapeEntrantResult = new ArrayOfTeamEscapeEntrantResult();
        for (EventDataEntity racer : eventDataDao.getRacers(eventSessionId)) {
            TeamEscapeEntrantResult teamEscapeEntrantResult = new TeamEscapeEntrantResult();
            teamEscapeEntrantResult.setDistanceToFinish(racer.getDistanceToFinish());
            teamEscapeEntrantResult.setEventDurationInMilliseconds(racer.getEventDurationInMilliseconds());
            teamEscapeEntrantResult.setEventSessionId(eventSessionId);
            teamEscapeEntrantResult.setFinishReason(racer.getFinishReason());
            teamEscapeEntrantResult.setFractionCompleted(racer.getFractionCompleted());
            teamEscapeEntrantResult.setPersonaId(racer.getPersonaId());
            teamEscapeEntrantResult.setRanking(racer.getRank());
            arrayOfTeamEscapeEntrantResult.getTeamEscapeEntrantResult().add(teamEscapeEntrantResult);

            if (!racer.getPersonaId().equals(activePersonaId)) {
                XmppEvent xmppEvent = new XmppEvent(racer.getPersonaId(), openFireSoapBoxCli);
                xmppEvent.sendTeamEscapeEnd(teamEscapeEntrantResultResponse);
                if ((teamEscapeArbitrationPacket.getFinishReason() == 518 ||
                        teamEscapeArbitrationPacket.getFinishReason() == 22) && teamEscapeArbitrationPacket.getRank() == 1 && eventSessionEntity.getEvent().isDnfEnabled()) {
                    xmppEvent.sendEventTimingOut(eventSessionEntity);
                    dnfTimerBO.scheduleDNF(eventSessionEntity, racer.getPersonaId());
                }
            }
        }

        TeamEscapeEventResult teamEscapeEventResult = new TeamEscapeEventResult();
        teamEscapeEventResult.setAccolades(rewardTeamEscapeBO.getTeamEscapeAccolades(activePersonaId,
                teamEscapeArbitrationPacket, eventSessionEntity));
        teamEscapeEventResult
                .setDurability(carDamageBO.induceCarDamage(activePersonaId, teamEscapeArbitrationPacket,
                        teamEscapeArbitrationPacket.getNumberOfCollisions()));
        teamEscapeEventResult.setEntrants(arrayOfTeamEscapeEntrantResult);
        teamEscapeEventResult.setEventId(eventDataEntity.getEvent().getId());
        teamEscapeEventResult.setEventSessionId(eventSessionId);
        teamEscapeEventResult.setExitPath(ExitPath.EXIT_TO_LOBBY);
        teamEscapeEventResult.setInviteLifetimeInMilliseconds(0);
        teamEscapeEventResult.setPersonaId(activePersonaId);

        if (eventSessionEntity.getEvent().isRaceAgainEnabled()) {
            if (eventSessionEntity.getLobby() == null) {
                teamEscapeEventResult.setLobbyInviteId(0);
            } else if (eventSessionEntity.getNextLobby() != null) {
                teamEscapeEventResult.setLobbyInviteId(eventSessionEntity.getNextLobby().getId());
                teamEscapeEventResult.setInviteLifetimeInMilliseconds(eventSessionEntity.getNextLobby()
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
                teamEscapeEventResult.setLobbyInviteId(newLobby.getId());
                teamEscapeEventResult.setInviteLifetimeInMilliseconds(eventSessionEntity.getEvent().getLobbyCountdownTime());
            }
        }

        if (teamEscapeArbitrationPacket.getBustedCount() == 0) {
            PersonaEntity personaEntity = personaDAO.findById(activePersonaId);

            achievementBO.updateAchievements(personaEntity, "EVENT", new HashMap<>() {{
                put("persona", personaEntity);
                put("event", eventDataEntity.getEvent());
                put("eventData", eventDataEntity);
                put("eventSession", eventSessionEntity);
                put("eventContext", new AchievementEventContext(
                        EventMode.fromId(eventDataEntity.getEvent().getEventModeId()),
                        teamEscapeArbitrationPacket,
                        eventSessionEntity));
            }});
        }

        return teamEscapeEventResult;
    }

}
