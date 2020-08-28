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
import com.soapboxrace.jaxb.http.ArrayOfTeamEscapeEntrantResult;
import com.soapboxrace.jaxb.http.TeamEscapeArbitrationPacket;
import com.soapboxrace.jaxb.http.TeamEscapeEntrantResult;
import com.soapboxrace.jaxb.http.TeamEscapeEventResult;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeTeamEscapeEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_TeamEscapeEntrantResultType;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class EventResultTeamEscapeBO extends EventResultBO<TeamEscapeArbitrationPacket, TeamEscapeEventResult> {

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
    private DNFTimerBO dnfTimerBO;

    protected TeamEscapeEventResult handleInternal(EventSessionEntity eventSessionEntity, Long activePersonaId,
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

        prepareBasicEventData(eventDataEntity, activePersonaId, teamEscapeArbitrationPacket);
        eventDataEntity.setBustedCount(teamEscapeArbitrationPacket.getBustedCount());
        eventDataEntity.setCopsDeployed(teamEscapeArbitrationPacket.getCopsDeployed());
        eventDataEntity.setCopsDisabled(teamEscapeArbitrationPacket.getCopsDisabled());
        eventDataEntity.setCopsRammed(teamEscapeArbitrationPacket.getCopsRammed());
        eventDataEntity.setCostToState(teamEscapeArbitrationPacket.getCostToState());
        eventDataEntity.setDistanceToFinish(teamEscapeArbitrationPacket.getDistanceToFinish());
        eventDataEntity.setFractionCompleted(teamEscapeArbitrationPacket.getFractionCompleted());
        eventDataEntity.setInfractions(teamEscapeArbitrationPacket.getInfractions());
        eventDataEntity.setLongestJumpDurationInMilliseconds(teamEscapeArbitrationPacket.getLongestJumpDurationInMilliseconds());
        eventDataEntity.setNumberOfCollisions(teamEscapeArbitrationPacket.getNumberOfCollisions());
        eventDataEntity.setPerfectStart(teamEscapeArbitrationPacket.getPerfectStart());
        eventDataEntity.setRoadBlocksDodged(teamEscapeArbitrationPacket.getRoadBlocksDodged());
        eventDataEntity.setSpikeStripsDodged(teamEscapeArbitrationPacket.getSpikeStripsDodged());
        eventDataEntity.setSumOfJumpsDurationInMilliseconds(teamEscapeArbitrationPacket.getSumOfJumpsDurationInMilliseconds());
        eventDataEntity.setTopSpeed(teamEscapeArbitrationPacket.getTopSpeed());
        eventSessionEntity.setEnded(System.currentTimeMillis());

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

        PersonaEntity personaEntity = personaDAO.find(activePersonaId);
        AchievementTransaction transaction = achievementBO.createTransaction(activePersonaId);
        TeamEscapeEventResult teamEscapeEventResult = new TeamEscapeEventResult();
        teamEscapeEventResult.setAccolades(rewardTeamEscapeBO.getAccolades(activePersonaId,
                teamEscapeArbitrationPacket, eventDataEntity, eventSessionEntity, transaction));
        teamEscapeEventResult
                .setDurability(carDamageBO.induceCarDamage(activePersonaId, teamEscapeArbitrationPacket,
                        eventDataEntity.getEvent()));
        teamEscapeEventResult.setEntrants(arrayOfTeamEscapeEntrantResult);
        teamEscapeEventResult.setEventId(eventDataEntity.getEvent().getId());
        teamEscapeEventResult.setEventSessionId(eventSessionId);
        teamEscapeEventResult.setPersonaId(activePersonaId);
        prepareRaceAgain(eventSessionEntity, teamEscapeEventResult);

        if (teamEscapeArbitrationPacket.getBustedCount() == 0) {
            updateEventAchievements(eventDataEntity, eventSessionEntity, activePersonaId, teamEscapeArbitrationPacket, transaction);
        }

        achievementBO.commitTransaction(personaEntity, transaction);

        eventSessionDao.update(eventSessionEntity);
        eventDataDao.update(eventDataEntity);

        if (eventSessionEntity.getLobby() != null && !eventSessionEntity.getLobby().getIsPrivate()) {
            matchmakingBO.resetIgnoredEvents(activePersonaId);
        }

        return teamEscapeEventResult;
    }

}
