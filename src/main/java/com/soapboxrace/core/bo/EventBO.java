package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.AccoladesFunc;
import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.dao.EventDataDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.OwnedCarDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.CardDecks;
import com.soapboxrace.core.jpa.EventDataEntity;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.OwnedCarEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppEvent;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.ArbitrationPacket;
import com.soapboxrace.jaxb.http.ArrayOfDragEntrantResult;
import com.soapboxrace.jaxb.http.ArrayOfLuckyDrawItem;
import com.soapboxrace.jaxb.http.ArrayOfRouteEntrantResult;
import com.soapboxrace.jaxb.http.ArrayOfTeamEscapeEntrantResult;
import com.soapboxrace.jaxb.http.DragArbitrationPacket;
import com.soapboxrace.jaxb.http.DragEntrantResult;
import com.soapboxrace.jaxb.http.DragEventResult;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.ExitPath;
import com.soapboxrace.jaxb.http.LuckyDrawInfo;
import com.soapboxrace.jaxb.http.PursuitArbitrationPacket;
import com.soapboxrace.jaxb.http.PursuitEventResult;
import com.soapboxrace.jaxb.http.RouteArbitrationPacket;
import com.soapboxrace.jaxb.http.RouteEntrantResult;
import com.soapboxrace.jaxb.http.RouteEventResult;
import com.soapboxrace.jaxb.http.TeamEscapeArbitrationPacket;
import com.soapboxrace.jaxb.http.TeamEscapeEntrantResult;
import com.soapboxrace.jaxb.http.TeamEscapeEventResult;
import com.soapboxrace.jaxb.xmpp.XMPP_DragEntrantResultType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeDragEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeRouteEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeTeamEscapeEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_RouteEntrantResultType;
import com.soapboxrace.jaxb.xmpp.XMPP_TeamEscapeEntrantResultType;
//import com.soapboxrace.xmpp.openfire.XmppEvent;

@Stateless
public class EventBO extends AccoladesFunc {

	@EJB
	private EventDAO eventDao;

	@EJB
	private EventSessionDAO eventSessionDao;

	@EJB
	private EventDataDAO eventDataDao;

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private CarSlotDAO carSlotDao;

	@EJB
	private OwnedCarDAO ownedCarDAO;

	@EJB
	private SocialBO socialBo;

	@EJB
	private PersonaBO personaBo;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private OpenFireSoapBoxCli openFireSoapBoxCli;

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

	public EventSessionEntity createEventSession(int eventId) {
		EventEntity eventEntity = eventDao.findById(eventId);
		if (eventEntity == null) {
			return null;
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

	public PursuitEventResult getPursitEnd(Long eventSessionId, Long activePersonaId, PursuitArbitrationPacket pursuitArbitrationPacket, Boolean isBusted) {
		EventSessionEntity sessionEntity = eventSessionDao.findById(eventSessionId);
		sessionEntity.setEnded(System.currentTimeMillis());

		eventSessionDao.update(sessionEntity);

		boolean legit = isLegit(activePersonaId, pursuitArbitrationPacket, sessionEntity);

		if (pursuitArbitrationPacket.getHacksDetected() > 0) {
			sendReportFromServer(activePersonaId, (int) pursuitArbitrationPacket.getCarId(), pursuitArbitrationPacket.getHacksDetected());
		}

		EventDataEntity eventDataEntity = eventDataDao.findByPersonaAndEventSessionId(activePersonaId, eventSessionId);
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

		PursuitEventResult pursuitEventResult = new PursuitEventResult();
		pursuitEventResult.setAccolades(legit ? getPursuitAccolades(activePersonaId, pursuitArbitrationPacket, isBusted) : new Accolades());
		pursuitEventResult.setDurability(
				updateDamageCar(activePersonaId, pursuitArbitrationPacket.getCarId(), 0, pursuitArbitrationPacket.getEventDurationInMilliseconds()));
		pursuitEventResult.setEventId(eventDataEntity.getEvent().getId());
		pursuitEventResult.setEventSessionId(eventSessionId);
		pursuitEventResult.setExitPath(ExitPath.EXIT_TO_FREEROAM);
		pursuitEventResult.setHeat(1);
		pursuitEventResult.setInviteLifetimeInMilliseconds(0);
		pursuitEventResult.setLobbyInviteId(0);
		pursuitEventResult.setPersonaId(activePersonaId);
		return pursuitEventResult;
	}

	private boolean isLegit(Long activePersonaId, ArbitrationPacket arbitrationPacket, EventSessionEntity sessionEntity) {
		int minimumTime = 0;

		if (arbitrationPacket instanceof PursuitArbitrationPacket)
			minimumTime = parameterBO.getMinPursuitTime();
		else if (arbitrationPacket instanceof RouteArbitrationPacket)
			minimumTime = parameterBO.getMinRouteTime();
		else if (arbitrationPacket instanceof TeamEscapeArbitrationPacket)
			minimumTime = parameterBO.getMinTETime();
		else if (arbitrationPacket instanceof DragArbitrationPacket)
			minimumTime = parameterBO.getMinDragTime();

		final long timeDiff = sessionEntity.getEnded() - sessionEntity.getStarted();
		boolean legit = timeDiff >= minimumTime;

		if (!legit) {
			socialBo.sendReport(0L, activePersonaId, 3, String.format("Abnormal event time: %d", timeDiff), (int) arbitrationPacket.getCarId(), 0, 0L);
		}
		return legit;
	}

	public RouteEventResult getRaceEnd(Long eventSessionId, Long activePersonaId, RouteArbitrationPacket routeArbitrationPacket) {
		EventSessionEntity sessionEntity = eventSessionDao.findById(eventSessionId);
		sessionEntity.setEnded(System.currentTimeMillis());

		eventSessionDao.update(sessionEntity);

		boolean legit = isLegit(activePersonaId, routeArbitrationPacket, sessionEntity);

		if (routeArbitrationPacket.getHacksDetected() > 0) {
			sendReportFromServer(activePersonaId, (int) routeArbitrationPacket.getCarId(), routeArbitrationPacket.getHacksDetected());
		}

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

		EventDataEntity eventDataEntity = eventDataDao.findByPersonaAndEventSessionId(activePersonaId, eventSessionId);
		eventDataEntity.setAlternateEventDurationInMilliseconds(routeArbitrationPacket.getAlternateEventDurationInMilliseconds());
		eventDataEntity.setBestLapDurationInMilliseconds(routeArbitrationPacket.getBestLapDurationInMilliseconds());
		eventDataEntity.setCarId(routeArbitrationPacket.getCarId());
		eventDataEntity.setEventDurationInMilliseconds(routeArbitrationPacket.getEventDurationInMilliseconds());
		eventDataEntity.setEventModeId(eventDataEntity.getEvent().getEventModeId());
		eventDataEntity.setFinishReason(routeArbitrationPacket.getFinishReason());
		eventDataEntity.setFractionCompleted(routeArbitrationPacket.getFractionCompleted());
		eventDataEntity.setHacksDetected(routeArbitrationPacket.getHacksDetected());
		eventDataEntity.setLongestJumpDurationInMilliseconds(routeArbitrationPacket.getLongestJumpDurationInMilliseconds());
		eventDataEntity.setNumberOfCollisions(routeArbitrationPacket.getNumberOfCollisions());
		eventDataEntity.setPerfectStart(routeArbitrationPacket.getPerfectStart());
		eventDataEntity.setPersonaId(activePersonaId);
		eventDataEntity.setRank(routeArbitrationPacket.getRank());
		eventDataEntity.setSumOfJumpsDurationInMilliseconds(routeArbitrationPacket.getSumOfJumpsDurationInMilliseconds());
		eventDataEntity.setTopSpeed(routeArbitrationPacket.getTopSpeed());
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

			if (!racer.getPersonaId().equals(activePersonaId)) {
				XmppEvent xmppEvent = new XmppEvent(racer.getPersonaId(), openFireSoapBoxCli);
				xmppEvent.sendRaceEnd(routeEntrantResultResponse);
				if (routeArbitrationPacket.getRank() == 1) {
					xmppEvent.sendEventTimingOut(eventSessionId);
				}
			}
		}

		RouteEventResult routeEventResult = new RouteEventResult();
		routeEventResult.setAccolades(legit ? getRouteAccolades(activePersonaId, routeArbitrationPacket) : new Accolades());
		routeEventResult.setDurability(updateDamageCar(activePersonaId, routeArbitrationPacket.getCarId(), routeArbitrationPacket.getNumberOfCollisions(),
				routeArbitrationPacket.getEventDurationInMilliseconds()));
		routeEventResult.setEntrants(arrayOfRouteEntrantResult);
		routeEventResult.setEventId(eventDataEntity.getEvent().getId());
		routeEventResult.setEventSessionId(eventSessionId);
		routeEventResult.setExitPath(ExitPath.EXIT_TO_FREEROAM);
		routeEventResult.setInviteLifetimeInMilliseconds(0);
		routeEventResult.setLobbyInviteId(0);
		routeEventResult.setPersonaId(activePersonaId);
		return routeEventResult;
	}

	public DragEventResult getDragEnd(Long eventSessionId, Long activePersonaId, DragArbitrationPacket dragArbitrationPacket) {
		EventSessionEntity sessionEntity = eventSessionDao.findById(eventSessionId);
		sessionEntity.setEnded(System.currentTimeMillis());

		eventSessionDao.update(sessionEntity);

		boolean legit = isLegit(activePersonaId, dragArbitrationPacket, sessionEntity);

		if (dragArbitrationPacket.getHacksDetected() > 0) {
			sendReportFromServer(activePersonaId, (int) dragArbitrationPacket.getCarId(), dragArbitrationPacket.getHacksDetected());
		}

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
					xmppEvent.sendEventTimingOut(eventSessionId);
				}
			}
		}

		DragEventResult dragEventResult = new DragEventResult();
		dragEventResult.setAccolades(legit ? getDragAccolades(activePersonaId, dragArbitrationPacket) : new Accolades());
		dragEventResult.setDurability(updateDamageCar(activePersonaId, dragArbitrationPacket.getCarId(), dragArbitrationPacket.getNumberOfCollisions(),
				dragArbitrationPacket.getEventDurationInMilliseconds()));
		dragEventResult.setEntrants(arrayOfDragEntrantResult);
		dragEventResult.setEventId(eventDataEntity.getEvent().getId());
		dragEventResult.setEventSessionId(eventSessionId);
		dragEventResult.setExitPath(ExitPath.EXIT_TO_FREEROAM);
		dragEventResult.setInviteLifetimeInMilliseconds(0);
		dragEventResult.setLobbyInviteId(0);
		dragEventResult.setPersonaId(activePersonaId);
		return dragEventResult;
	}

	public TeamEscapeEventResult getTeamEscapeEnd(Long eventSessionId, Long activePersonaId, TeamEscapeArbitrationPacket teamEscapeArbitrationPacket) {
		EventSessionEntity sessionEntity = eventSessionDao.findById(eventSessionId);
		sessionEntity.setEnded(System.currentTimeMillis());

		eventSessionDao.update(sessionEntity);

		boolean legit = isLegit(activePersonaId, teamEscapeArbitrationPacket, sessionEntity);

		if (teamEscapeArbitrationPacket.getHacksDetected() > 0) {
			sendReportFromServer(activePersonaId, (int) teamEscapeArbitrationPacket.getCarId(), teamEscapeArbitrationPacket.getHacksDetected());
		}

		XMPP_TeamEscapeEntrantResultType xmppTeamEscapeResult = new XMPP_TeamEscapeEntrantResultType();
		xmppTeamEscapeResult.setEventDurationInMilliseconds(teamEscapeArbitrationPacket.getEventDurationInMilliseconds());
		xmppTeamEscapeResult.setEventSessionId(eventSessionId);
		xmppTeamEscapeResult.setFinishReason(teamEscapeArbitrationPacket.getFinishReason());
		xmppTeamEscapeResult.setPersonaId(activePersonaId);

		XMPP_ResponseTypeTeamEscapeEntrantResult teamEscapeEntrantResultResponse = new XMPP_ResponseTypeTeamEscapeEntrantResult();
		teamEscapeEntrantResultResponse.setTeamEscapeEntrantResult(xmppTeamEscapeResult);

		EventDataEntity eventDataEntity = eventDataDao.findByPersonaAndEventSessionId(activePersonaId, eventSessionId);
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
				if (teamEscapeArbitrationPacket.getRank() == 1) {
					xmppEvent.sendEventTimingOut(eventSessionId);
				}
			}
		}

		TeamEscapeEventResult teamEscapeEventResult = new TeamEscapeEventResult();
		teamEscapeEventResult.setAccolades(legit ? getTeamEscapeAccolades(activePersonaId, teamEscapeArbitrationPacket) : new Accolades());
		teamEscapeEventResult.setDurability(updateDamageCar(activePersonaId, teamEscapeArbitrationPacket.getCarId(),
				teamEscapeArbitrationPacket.getNumberOfCollisions(), teamEscapeArbitrationPacket.getEventDurationInMilliseconds()));
		teamEscapeEventResult.setEntrants(arrayOfTeamEscapeEntrantResult);
		teamEscapeEventResult.setEventId(eventDataEntity.getEvent().getId());
		teamEscapeEventResult.setEventSessionId(eventSessionId);
		teamEscapeEventResult.setExitPath(ExitPath.EXIT_TO_FREEROAM);
		teamEscapeEventResult.setInviteLifetimeInMilliseconds(0);
		teamEscapeEventResult.setLobbyInviteId(0);
		teamEscapeEventResult.setPersonaId(activePersonaId);
		return teamEscapeEventResult;
	}

	private Accolades getPursuitAccolades(Long activePersonaId, PursuitArbitrationPacket pursuitArbitrationPacket, Boolean isBusted) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = new RewardVO(parameterBO.getBoolParam("ENABLE_ECONOMY"), parameterBO.getBoolParam("ENABLE_REPUTATION"));

		float rep = 0;
		float cash = 0;

		if (!isBusted) {
			if (personaEntity.getLevel() < 60) {
				rep = 200.0f * (personaEntity.getLevel() / 10.0f);
			}
			if (personaEntity.getCash() < 9999999) {
				cash = 600.0f * (personaEntity.getLevel() / 10.0f);
			}
			rewardVO.add((int) rep, (int) cash, EnumRewardCategory.PURSUIT, EnumRewardType.EVADED);
			float skillCash = cash * getSkillMultiplicater(personaEntity.getPersonaId(), 1);
			rewardVO.add(0, (int) skillCash, EnumRewardCategory.SKILL, EnumRewardType.SKILL_MOD);

			float copsDeployedExp = rep * (pursuitArbitrationPacket.getCopsDeployed() / 200.0f);
			float copsDeployedCash = cash * (pursuitArbitrationPacket.getCopsDeployed() / 200.0f);
			rewardVO.add((int) copsDeployedExp, (int) copsDeployedCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DEPLOYED);

			float copsDisabledExp = rep * (pursuitArbitrationPacket.getCopsDisabled() / 100.0f);
			float copsDisabledCash = cash * (pursuitArbitrationPacket.getCopsDisabled() / 100.0f);
			rewardVO.add((int) copsDisabledExp, (int) copsDisabledCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DISABLED);

			float copsRammedExp = rep * (pursuitArbitrationPacket.getCopsRammed() / 150.0f);
			float copsRammedCash = cash * (pursuitArbitrationPacket.getCopsRammed() / 150.0f);
			rewardVO.add((int) copsRammedExp, (int) copsRammedCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_RAMMED);

			float costToStateExp = rep * ((pursuitArbitrationPacket.getCostToState() / 5000.0f) / 150.0f);
			float costToStateCash = cash * ((pursuitArbitrationPacket.getCostToState() / 5000.0f) / 150.0f);
			rewardVO.add((int) costToStateExp, (int) costToStateCash, EnumRewardCategory.PURSUIT, EnumRewardType.COST_TO_STATE);

			float eventDurationExp = rep * ((pursuitArbitrationPacket.getEventDurationInMilliseconds() / 2500.0f) / 150.0f);
			float eventDurationCash = cash * ((pursuitArbitrationPacket.getEventDurationInMilliseconds() / 2500.0f) / 150.0f);
			rewardVO.add((int) eventDurationExp, (int) eventDurationCash, EnumRewardCategory.PURSUIT, EnumRewardType.PURSUIT_LENGTH);

			float heatExp = rep * (pursuitArbitrationPacket.getHeat() / 5.0f);
			float heatCash = cash * (pursuitArbitrationPacket.getHeat() / 5.0f);
			rewardVO.add((int) heatExp, (int) heatCash, EnumRewardCategory.PURSUIT, EnumRewardType.HEAT_LEVEL);

			float infractionsExp = rep * (pursuitArbitrationPacket.getInfractions() / 100.0f);
			float infractionsCash = cash * (pursuitArbitrationPacket.getInfractions() / 100.0f);
			rewardVO.add((int) infractionsExp, (int) infractionsCash, EnumRewardCategory.PURSUIT, EnumRewardType.INFRACTIONS);

			float roadBlocksDodgedExp = rep * (pursuitArbitrationPacket.getRoadBlocksDodged() / 50.0f);
			float roadBlocksDodgedCash = cash * (pursuitArbitrationPacket.getRoadBlocksDodged() / 50.0f);
			rewardVO.add((int) roadBlocksDodgedExp, (int) roadBlocksDodgedCash, EnumRewardCategory.PURSUIT, EnumRewardType.ROADBLOCKS_DODGED);

			float spikeStripsDodgedExp = rep * (pursuitArbitrationPacket.getSpikeStripsDodged() / 50.0f);
			float spikeStripsDodgedCash = cash * (pursuitArbitrationPacket.getSpikeStripsDodged() / 50.0f);
			rewardVO.add((int) spikeStripsDodgedExp, (int) spikeStripsDodgedCash, EnumRewardCategory.PURSUIT, EnumRewardType.SPIKE_STRIPS_DODGED);

		}

		float repMult = rewardVO.getRep() * parameterBO.getRepRewardMultiplier();
		float cashMult = rewardVO.getCash() * parameterBO.getCashRewardMultiplier();
		rewardVO.add((int) repMult, 0, EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
		rewardVO.add(0, (int) cashMult, EnumRewardCategory.AMPLIFIER, EnumRewardType.TOKEN_AMPLIFIER);

		Accolades accolades = new Accolades();

		accolades.setFinalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setHasLeveledUp(isLeveledUp(personaEntity, rewardVO.getRep()));
		if (!isBusted) {
			accolades.setLuckyDrawInfo(getLuckyDrawInfo(1, personaEntity.getLevel(), personaEntity));
		}
		accolades.setOriginalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());

		applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return accolades;
	}

	private Accolades getRouteAccolades(Long activePersonaId, RouteArbitrationPacket routeArbitrationPacket) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = new RewardVO(parameterBO.getBoolParam("ENABLE_ECONOMY"), parameterBO.getBoolParam("ENABLE_REPUTATION"));

		float rep = 0;
		float cash = 0;

		if (personaEntity.getLevel() < 60) {
			rep = 200 * ((personaEntity.getLevel() + 1.0f) / 5.0f);
		}
		if (personaEntity.getCash() < 9999999) {
			cash = 600 * ((personaEntity.getLevel() + 1.0f) / 5.0f);
		}
		rewardVO.add((int) rep, (int) cash, EnumRewardCategory.BASE, EnumRewardType.NONE);

		float skillCash = cash * getSkillMultiplicater(personaEntity.getPersonaId(), 0);
		rewardVO.add(0, (int) skillCash, EnumRewardCategory.SKILL, EnumRewardType.SKILL_MOD);

		float rankExp = routeArbitrationPacket.getRank() == 1 ? rep * 0.10f : rep * 0.05f; // + 10% if fist, + 5% else
		float rankCash = routeArbitrationPacket.getRank() == 1 ? cash * 0.10f : cash * 0.05f; // + 10% if fist, + 5% else
		rankExp = routeArbitrationPacket.getFinishReason() == 22 ? rankExp : rankExp / 10;
		rankCash = routeArbitrationPacket.getFinishReason() == 22 ? rankCash : rankCash / 10;
		rewardVO.add((int) rankExp, (int) rankCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);

		float timeRaceExp = rep * (((routeArbitrationPacket.getEventDurationInMilliseconds() / 1000.0f) / routeArbitrationPacket.getRank()) / 100.0f);
		float timeRaceCash = cash * (((routeArbitrationPacket.getEventDurationInMilliseconds() / 1000.0f) / routeArbitrationPacket.getRank()) / 100.0f);
		timeRaceExp = routeArbitrationPacket.getFinishReason() == 22 ? timeRaceExp : timeRaceExp / 10;
		timeRaceCash = routeArbitrationPacket.getFinishReason() == 22 ? timeRaceCash : timeRaceCash / 10;
		rewardVO.add((int) timeRaceExp, (int) timeRaceCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);

		if (routeArbitrationPacket.getPerfectStart() == 1) {
			float perfectStartExp = rep * 0.10f; // + 10%
			float perfectStartCash = cash * 0.10f; // + 10%
			perfectStartExp = routeArbitrationPacket.getFinishReason() == 22 ? perfectStartExp : perfectStartExp / 10;
			perfectStartCash = routeArbitrationPacket.getFinishReason() == 22 ? perfectStartCash : perfectStartCash / 10;
			rewardVO.add((int) perfectStartExp, (int) perfectStartCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}

		if (routeArbitrationPacket.getTopSpeed() >= 70.0f) {
			float highSpeedExp = rep * 0.10f; // + 10%
			float highSpeedCash = cash * 0.10f; // + 10%
			highSpeedExp = routeArbitrationPacket.getFinishReason() == 22 ? highSpeedExp : highSpeedExp / 10;
			highSpeedCash = routeArbitrationPacket.getFinishReason() == 22 ? highSpeedCash : highSpeedCash / 10;
			rewardVO.add((int) highSpeedExp, (int) highSpeedCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}

		float repMult = rewardVO.getRep() * parameterBO.getRepRewardMultiplier();
		float cashMult = rewardVO.getCash() * parameterBO.getCashRewardMultiplier();
		rewardVO.add((int) repMult, 0, EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
		rewardVO.add(0, (int) cashMult, EnumRewardCategory.AMPLIFIER, EnumRewardType.TOKEN_AMPLIFIER);

		Accolades accolades = new Accolades();
		accolades.setFinalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setHasLeveledUp(isLeveledUp(personaEntity, rewardVO.getRep()));
		accolades.setLuckyDrawInfo(getLuckyDrawInfo(routeArbitrationPacket.getRank(), personaEntity.getLevel(), personaEntity));
		accolades.setOriginalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());

		applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return accolades;
	}

	private Accolades getDragAccolades(Long activePersonaId, DragArbitrationPacket dragArbitrationPacket) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = new RewardVO(parameterBO.getBoolParam("ENABLE_ECONOMY"), parameterBO.getBoolParam("ENABLE_REPUTATION"));

		float rep = 0;
		float cash = 0;
		if (personaEntity.getLevel() < 60) {
			rep = 100 * (personaEntity.getLevel() / 5.0f);
		}
		if (personaEntity.getCash() < 9999999) {
			cash = 200 * (personaEntity.getLevel() / 5.0f);
		}
		rewardVO.add((int) rep, (int) cash, EnumRewardCategory.BASE, EnumRewardType.NONE);

		float skillCash = cash * getSkillMultiplicater(personaEntity.getPersonaId(), 0);
		rewardVO.add(0, (int) skillCash, EnumRewardCategory.SKILL, EnumRewardType.SKILL_MOD);

		float rankExp = dragArbitrationPacket.getRank() == 1 ? rep * 0.10f : rep * 0.05f; // + 10% if fist, + 5% else
		float rankCash = dragArbitrationPacket.getRank() == 1 ? cash * 0.10f : cash * 0.05f; // + 10% if fist, + 5% else
		rankExp = dragArbitrationPacket.getFinishReason() == 22 ? rankExp : rankExp / 10;
		rankCash = dragArbitrationPacket.getFinishReason() == 22 ? rankCash : rankCash / 10;
		rewardVO.add((int) rankExp, (int) rankCash, EnumRewardCategory.OBJECTIVE, EnumRewardType.NONE);

		float timeRaceExp = rep * ((60000.0f / dragArbitrationPacket.getEventDurationInMilliseconds()) / 4.0f);
		float timeRaceCash = cash * ((60000.0f / dragArbitrationPacket.getEventDurationInMilliseconds()) / 4.0f);
		timeRaceExp = dragArbitrationPacket.getFinishReason() == 22 ? timeRaceExp : timeRaceExp / 10;
		timeRaceCash = dragArbitrationPacket.getFinishReason() == 22 ? timeRaceCash : timeRaceCash / 10;
		rewardVO.add((int) timeRaceExp, (int) timeRaceCash, EnumRewardCategory.OBJECTIVE, EnumRewardType.NONE);

		if (dragArbitrationPacket.getPerfectStart() == 1) {
			float perfectStartExp = rep * 0.10f; // + 10%
			float perfectStartCash = cash * 0.10f; // + 10%
			perfectStartExp = dragArbitrationPacket.getFinishReason() == 22 ? perfectStartExp : perfectStartExp / 10;
			perfectStartCash = dragArbitrationPacket.getFinishReason() == 22 ? perfectStartCash : perfectStartCash / 10;
			rewardVO.add((int) perfectStartExp, (int) perfectStartCash, EnumRewardCategory.OBJECTIVE, EnumRewardType.NONE);
		}

		if (dragArbitrationPacket.getTopSpeed() >= 70.0f) {
			float highSpeedExp = rep * 0.10f; // + 10%
			float highSpeedCash = cash * 0.10f; // + 10%
			highSpeedExp = dragArbitrationPacket.getFinishReason() == 22 ? highSpeedExp : highSpeedExp / 10;
			highSpeedCash = dragArbitrationPacket.getFinishReason() == 22 ? highSpeedCash : highSpeedCash / 10;
			rewardVO.add((int) highSpeedExp, (int) highSpeedCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}

		float repMult = rewardVO.getRep() * parameterBO.getRepRewardMultiplier();
		float cashMult = rewardVO.getCash() * parameterBO.getCashRewardMultiplier();
		rewardVO.add((int) repMult, 0, EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
		rewardVO.add(0, (int) cashMult, EnumRewardCategory.AMPLIFIER, EnumRewardType.TOKEN_AMPLIFIER);

		Accolades accolades = new Accolades();
		accolades.setFinalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());
		accolades.setHasLeveledUp(isLeveledUp(personaEntity, rewardVO.getRep()));
		accolades.setLuckyDrawInfo(getLuckyDrawInfo(dragArbitrationPacket.getRank(), personaEntity.getLevel(), personaEntity));
		accolades.setOriginalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));

		applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return accolades;
	}

	private Accolades getTeamEscapeAccolades(Long activePersonaId, TeamEscapeArbitrationPacket teamEscapeArbitrationPacket) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = new RewardVO(parameterBO.getBoolParam("ENABLE_ECONOMY"), parameterBO.getBoolParam("ENABLE_REPUTATION"));

		float rep = 0;
		float cash = 0;

		if (personaEntity.getLevel() < 60) {
			rep = 200.0f * ((personaEntity.getLevel() + 1.0f) / 10.0f);
		}
		if (personaEntity.getCash() < 9999999) {
			cash = 600.0f * ((personaEntity.getLevel() + 1.0f) / 10.0f);
		}
		rewardVO.add((int) rep, (int) cash, EnumRewardCategory.BASE, EnumRewardType.NONE);

		Integer bustedCount = teamEscapeArbitrationPacket.getBustedCount();
		Integer finishReason = teamEscapeArbitrationPacket.getFinishReason();

		float skillCash = cash * getSkillMultiplicater(personaEntity.getPersonaId(), 1);
		rewardVO.add(0, (int) skillCash, EnumRewardCategory.SKILL, EnumRewardType.SKILL_MOD);

		float copsDeployedExp = rep * (teamEscapeArbitrationPacket.getCopsDeployed() / 175.0f);
		float copsDeployedCash = cash * (teamEscapeArbitrationPacket.getCopsDeployed() / 175.0f);
		copsDeployedExp = bustedCount > 0 && finishReason == 22 ? copsDeployedExp / (bustedCount * 2.0f)
				: finishReason != 22 ? copsDeployedExp / 10 : copsDeployedExp;
		copsDeployedCash = bustedCount > 0 && finishReason == 22 ? copsDeployedCash / (bustedCount * 2.0f)
				: finishReason != 22 ? copsDeployedCash / 10 : copsDeployedCash;
		rewardVO.add((int) copsDeployedExp, (int) copsDeployedCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DEPLOYED);

		float copsDisabledExp = rep * (teamEscapeArbitrationPacket.getCopsDisabled() / 100.0f);
		float copsDisabledCash = cash * (teamEscapeArbitrationPacket.getCopsDisabled() / 100.0f);
		copsDisabledExp = bustedCount > 0 && finishReason == 22 ? copsDisabledExp / (bustedCount * 2.0f)
				: finishReason != 22 ? copsDisabledExp / 10 : copsDisabledExp;
		copsDisabledCash = bustedCount > 0 && finishReason == 22 ? copsDisabledCash / (bustedCount * 2.0f)
				: finishReason != 22 ? copsDisabledCash / 10 : copsDisabledCash;
		rewardVO.add((int) copsDisabledExp, (int) copsDisabledCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DISABLED);

		float copsRammedExp = rep * (teamEscapeArbitrationPacket.getCopsRammed() / 150.0f);
		float copsRammedCash = cash * (teamEscapeArbitrationPacket.getCopsRammed() / 150.0f);
		copsRammedExp = bustedCount > 0 && finishReason == 22 ? copsRammedExp / (bustedCount * 2.0f) : finishReason != 22 ? copsRammedExp / 10 : copsRammedExp;
		copsRammedCash = bustedCount > 0 && finishReason == 22 ? copsRammedCash / (bustedCount * 2.0f)
				: finishReason != 22 ? copsRammedCash / 10 : copsRammedCash;
		rewardVO.add((int) copsRammedExp, (int) copsRammedCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_RAMMED);

		float costToStateExp = rep * ((teamEscapeArbitrationPacket.getCostToState() / 2500.0f) / 100.0f);
		float costToStateCash = cash * ((teamEscapeArbitrationPacket.getCostToState() / 2500.0f) / 100.0f);
		costToStateExp = bustedCount > 0 && finishReason == 22 ? costToStateExp / (bustedCount * 2.0f)
				: finishReason != 22 ? costToStateExp / 10 : costToStateExp;
		costToStateCash = bustedCount > 0 && finishReason == 22 ? costToStateCash / (bustedCount * 2.0f)
				: finishReason != 22 ? costToStateCash / 10 : costToStateCash;
		rewardVO.add((int) costToStateExp, (int) costToStateCash, EnumRewardCategory.PURSUIT, EnumRewardType.COST_TO_STATE);

		float infractionsExp = rep * (teamEscapeArbitrationPacket.getInfractions() / 100.0f);
		float infractionsCash = cash * (teamEscapeArbitrationPacket.getInfractions() / 100.0f);
		infractionsExp = bustedCount > 0 && finishReason == 22 ? infractionsExp / (bustedCount * 2.0f)
				: finishReason != 22 ? infractionsExp / 10 : infractionsExp;
		infractionsCash = bustedCount > 0 && finishReason == 22 ? infractionsCash / (bustedCount * 2.0f)
				: finishReason != 22 ? infractionsCash / 10 : infractionsCash;
		rewardVO.add((int) infractionsExp, (int) infractionsCash, EnumRewardCategory.PURSUIT, EnumRewardType.INFRACTIONS);

		float roadBlocksDodgedExp = rep * (teamEscapeArbitrationPacket.getRoadBlocksDodged() / 50.0f);
		float roadBlocksDodgedCash = cash * (teamEscapeArbitrationPacket.getRoadBlocksDodged() / 50.0f);
		roadBlocksDodgedExp = bustedCount > 0 && finishReason == 22 ? roadBlocksDodgedExp / (bustedCount * 2.0f)
				: finishReason != 22 ? roadBlocksDodgedExp / 10 : roadBlocksDodgedExp;
		roadBlocksDodgedCash = bustedCount > 0 && finishReason == 22 ? roadBlocksDodgedCash / (bustedCount * 2.0f)
				: finishReason != 22 ? roadBlocksDodgedCash / 10 : roadBlocksDodgedCash;
		rewardVO.add((int) roadBlocksDodgedExp, (int) roadBlocksDodgedCash, EnumRewardCategory.PURSUIT, EnumRewardType.ROADBLOCKS_DODGED);

		float spikeStripsDodgedExp = rep * (teamEscapeArbitrationPacket.getSpikeStripsDodged() / 50.0f);
		float spikeStripsDodgedCash = cash * (teamEscapeArbitrationPacket.getSpikeStripsDodged() / 50.0f);
		spikeStripsDodgedExp = bustedCount > 0 && finishReason == 22 ? spikeStripsDodgedExp / (bustedCount * 2.0f)
				: finishReason != 22 ? spikeStripsDodgedExp / 10 : spikeStripsDodgedExp;
		spikeStripsDodgedCash = bustedCount > 0 && finishReason == 22 ? spikeStripsDodgedCash / (bustedCount * 2.0f)
				: finishReason != 22 ? spikeStripsDodgedCash / 10 : spikeStripsDodgedCash;
		rewardVO.add((int) spikeStripsDodgedExp, (int) spikeStripsDodgedCash, EnumRewardCategory.PURSUIT, EnumRewardType.SPIKE_STRIPS_DODGED);

		float rankExp = teamEscapeArbitrationPacket.getRank() == 1 ? rep * 0.10f : rep * 0.05f; // + 10% if fist, + 5% else
		float rankCash = teamEscapeArbitrationPacket.getRank() == 1 ? cash * 0.10f : cash * 0.05f; // + 10% if fist, + 5% else
		rankExp = bustedCount > 0 && finishReason == 22 ? rankExp / (bustedCount * 2.0f) : finishReason != 22 ? rankExp / 10 : rankExp;
		rankCash = bustedCount > 0 && finishReason == 22 ? rankCash / (bustedCount * 2.0f) : finishReason != 22 ? rankCash / 10 : rankCash;
		rewardVO.add((int) rankExp, (int) rankCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);

		if (teamEscapeArbitrationPacket.getPerfectStart() == 1) {
			float perfectStartExp = rep * 0.10f; // + 10%
			float perfectStartCash = cash * 0.10f; // + 10%
			perfectStartExp = bustedCount > 0 && finishReason == 22 ? perfectStartExp / (bustedCount * 2.0f)
					: finishReason != 22 ? perfectStartExp / 10 : perfectStartExp;
			perfectStartCash = bustedCount > 0 && finishReason == 22 ? perfectStartCash / (bustedCount * 2.0f)
					: finishReason != 22 ? perfectStartCash / 10 : perfectStartCash;
			rewardVO.add((int) perfectStartExp, (int) perfectStartCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}

		if (teamEscapeArbitrationPacket.getTopSpeed() >= 70.0f) {
			float highSpeedExp = rep * 0.10f; // + 10%
			float highSpeedCash = cash * 0.10f; // + 10%
			highSpeedExp = bustedCount > 0 && finishReason == 22 ? highSpeedExp / (bustedCount * 2.0f) : finishReason != 22 ? highSpeedExp / 10 : highSpeedExp;
			highSpeedCash = bustedCount > 0 && finishReason == 22 ? highSpeedCash / (bustedCount * 2.0f)
					: finishReason != 22 ? highSpeedCash / 10 : highSpeedCash;
			rewardVO.add((int) highSpeedExp, (int) highSpeedCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}

		float repMult = rewardVO.getRep() * parameterBO.getRepRewardMultiplier();
		float cashMult = rewardVO.getCash() * parameterBO.getCashRewardMultiplier();
		rewardVO.add((int) repMult, 0, EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
		rewardVO.add(0, (int) cashMult, EnumRewardCategory.AMPLIFIER, EnumRewardType.TOKEN_AMPLIFIER);

		Accolades accolades = new Accolades();
		accolades.setFinalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setHasLeveledUp(isLeveledUp(personaEntity, rewardVO.getRep()));
		if (teamEscapeArbitrationPacket.getFinishReason() == 22) {
			accolades.setLuckyDrawInfo(getLuckyDrawInfo(teamEscapeArbitrationPacket.getRank(), personaEntity.getLevel(), personaEntity));
		}
		accolades.setOriginalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());

		applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return accolades;
	}

	private LuckyDrawInfo getLuckyDrawInfo(Integer rank, Integer level, PersonaEntity personaEntity) {
		LuckyDrawInfo luckyDrawInfo = new LuckyDrawInfo();
		if (!parameterBO.getBoolParam("ENABLE_DROP_ITEM")) {
			return luckyDrawInfo;
		}
		ArrayOfLuckyDrawItem arrayOfLuckyDrawItem = new ArrayOfLuckyDrawItem();
		arrayOfLuckyDrawItem.getLuckyDrawItem().add(getItemFromProduct(personaEntity));
		luckyDrawInfo.setCardDeck(CardDecks.forRank(rank));
		luckyDrawInfo.setItems(arrayOfLuckyDrawItem);
		return luckyDrawInfo;
	}

	private void sendReportFromServer(Long activePersonaId, Integer carId, Long hacksDetected) {
		socialBo.sendReport(0L, activePersonaId, 3, "Server sent a report for cheat", carId, 0, hacksDetected);
	}

	private Integer updateDamageCar(Long personaId, Long carId, Integer numberOfCollision, Long eventDuration) {
		if (!parameterBO.getBoolParam("ENABLE_CAR_DAMAGE")) {
			return 100;
		}
		OwnedCarEntity ownedCarEntity = ownedCarDAO.findById(carId);
		CarSlotEntity carSlotEntity = ownedCarEntity.getCarSlot();
		int durability = ownedCarEntity.getDurability();
		if (durability > 10) {
			Integer calcDamage = numberOfCollision + ((int) (eventDuration / 60000)) * 2;
			Integer newCarDamage = durability - calcDamage;
			ownedCarEntity.setDurability(newCarDamage < 10 ? 10 : newCarDamage);
			if (carSlotEntity != null) {
				carSlotDao.update(carSlotEntity);
			}
		}
		return ownedCarEntity.getDurability();
	}
}
