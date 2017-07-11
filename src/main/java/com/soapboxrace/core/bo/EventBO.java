package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.dao.EventDataDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.CardDecks;
import com.soapboxrace.core.jpa.EventDataEntity;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.ArrayOfDragEntrantResult;
import com.soapboxrace.jaxb.http.ArrayOfLuckyDrawItem;
import com.soapboxrace.jaxb.http.ArrayOfRewardPart;
import com.soapboxrace.jaxb.http.ArrayOfRouteEntrantResult;
import com.soapboxrace.jaxb.http.ArrayOfTeamEscapeEntrantResult;
import com.soapboxrace.jaxb.http.DragArbitrationPacket;
import com.soapboxrace.jaxb.http.DragEntrantResult;
import com.soapboxrace.jaxb.http.DragEventResult;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.ExitPath;
import com.soapboxrace.jaxb.http.LuckyDrawInfo;
import com.soapboxrace.jaxb.http.LuckyDrawItem;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.http.PursuitArbitrationPacket;
import com.soapboxrace.jaxb.http.PursuitEventResult;
import com.soapboxrace.jaxb.http.Reward;
import com.soapboxrace.jaxb.http.RewardPart;
import com.soapboxrace.jaxb.http.RouteArbitrationPacket;
import com.soapboxrace.jaxb.http.RouteEntrantResult;
import com.soapboxrace.jaxb.http.RouteEventResult;
import com.soapboxrace.jaxb.http.TeamEscapeArbitrationPacket;
import com.soapboxrace.jaxb.http.TeamEscapeEntrantResult;
import com.soapboxrace.jaxb.http.TeamEscapeEventResult;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_DragEntrantResultType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeDragEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeRouteEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeTeamEscapeEntrantResult;
import com.soapboxrace.jaxb.xmpp.XMPP_RouteEntrantResultType;
import com.soapboxrace.jaxb.xmpp.XMPP_TeamEscapeEntrantResultType;
import com.soapboxrace.xmpp.openfire.XmppEvent;

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
	private CarSlotDAO carSlotDao;
	
	@EJB
	private SocialBO socialBo;
	
	@EJB
	private PersonaBO personaBo;

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
		EventSessionEntity eventSessionEntity = new EventSessionEntity();
		eventSessionEntity.setEvent(eventEntity);
		eventSessionDao.insert(eventSessionEntity);
		return eventSessionEntity;
	}

	public EventSessionEntity findEventSessionById(Long id) {
		return eventSessionDao.findById(id);
	}
	
	public PursuitEventResult getPursitEnd(Long eventSessionId, Long activePersonaId, PursuitArbitrationPacket pursuitArbitrationPacket, Boolean isBusted) {
		if(pursuitArbitrationPacket.getHacksDetected() > 0) {
			sendReportFromServer(activePersonaId, (int)pursuitArbitrationPacket.getCarId(), pursuitArbitrationPacket.getHacksDetected());
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
		pursuitEventResult.setAccolades(getPursuitAccolades(activePersonaId, pursuitArbitrationPacket, isBusted));
		pursuitEventResult.setDurability(updateDamageCar(activePersonaId, pursuitArbitrationPacket.getCarId(), 0, pursuitArbitrationPacket.getEventDurationInMilliseconds()));
		pursuitEventResult.setEventId(eventDataEntity.getEvent().getId());
		pursuitEventResult.setEventSessionId(eventSessionId);
		pursuitEventResult.setExitPath(ExitPath.EXIT_TO_FREEROAM);
		pursuitEventResult.setHeat(1);
		pursuitEventResult.setInviteLifetimeInMilliseconds(0);
		pursuitEventResult.setLobbyInviteId(0);
		pursuitEventResult.setPersonaId(activePersonaId);
		return pursuitEventResult;
	}
	
	public RouteEventResult getRaceEnd(Long eventSessionId, Long activePersonaId, RouteArbitrationPacket routeArbitrationPacket) {
		if(routeArbitrationPacket.getHacksDetected() > 0) {
			sendReportFromServer(activePersonaId, (int)routeArbitrationPacket.getCarId(), routeArbitrationPacket.getHacksDetected());
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
		for(EventDataEntity racer : eventDataDao.getRacers(eventSessionId)) {
			RouteEntrantResult routeEntrantResult = new RouteEntrantResult();
			routeEntrantResult.setBestLapDurationInMilliseconds(racer.getBestLapDurationInMilliseconds());
			routeEntrantResult.setEventDurationInMilliseconds(racer.getEventDurationInMilliseconds());
			routeEntrantResult.setEventSessionId(eventSessionId);
			routeEntrantResult.setFinishReason(racer.getFinishReason());
			routeEntrantResult.setPersonaId(racer.getPersonaId());
			routeEntrantResult.setRanking(racer.getRank());
			routeEntrantResult.setTopSpeed(racer.getTopSpeed());
			arrayOfRouteEntrantResult.getRouteEntrantResult().add(routeEntrantResult);

			if(!racer.getPersonaId().equals(activePersonaId)) {
				XmppEvent xmppEvent = new XmppEvent(racer.getPersonaId());
				xmppEvent.sendRaceEnd(routeEntrantResultResponse);
				if(routeArbitrationPacket.getRank() == 1) {
					xmppEvent.sendEventTimingOut(eventSessionId);
				}
			}
		}
		
		RouteEventResult routeEventResult = new RouteEventResult();
		routeEventResult.setAccolades(getRouteAccolades(activePersonaId, routeArbitrationPacket));
		routeEventResult.setDurability(updateDamageCar(activePersonaId, routeArbitrationPacket.getCarId(), routeArbitrationPacket.getNumberOfCollisions(), routeArbitrationPacket.getEventDurationInMilliseconds()));
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
		if(dragArbitrationPacket.getHacksDetected() > 0) {
			sendReportFromServer(activePersonaId, (int)dragArbitrationPacket.getCarId(), dragArbitrationPacket.getHacksDetected());
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
		for(EventDataEntity racer : eventDataDao.getRacers(eventSessionId)) {
			DragEntrantResult dragEntrantResult = new DragEntrantResult();
			dragEntrantResult.setEventDurationInMilliseconds(racer.getEventDurationInMilliseconds());
			dragEntrantResult.setEventSessionId(eventSessionId);
			dragEntrantResult.setFinishReason(racer.getFinishReason());
			dragEntrantResult.setPersonaId(racer.getPersonaId());
			dragEntrantResult.setRanking(racer.getRank());
			dragEntrantResult.setTopSpeed(racer.getTopSpeed());
			arrayOfDragEntrantResult.getDragEntrantResult().add(dragEntrantResult);

			if(!racer.getPersonaId().equals(activePersonaId)) {
				XmppEvent xmppEvent = new XmppEvent(racer.getPersonaId());
				xmppEvent.sendDragEnd(dragEntrantResultResponse);
				if(dragArbitrationPacket.getRank() == 1) {
					xmppEvent.sendEventTimingOut(eventSessionId);
				}
			}
		}
		
		DragEventResult dragEventResult = new DragEventResult();
		dragEventResult.setAccolades(getDragAccolades(activePersonaId, dragArbitrationPacket));
		dragEventResult.setDurability(updateDamageCar(activePersonaId, dragArbitrationPacket.getCarId(), dragArbitrationPacket.getNumberOfCollisions(), dragArbitrationPacket.getEventDurationInMilliseconds()));
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
		if(teamEscapeArbitrationPacket.getHacksDetected() > 0) {
			sendReportFromServer(activePersonaId, (int)teamEscapeArbitrationPacket.getCarId(), teamEscapeArbitrationPacket.getHacksDetected());
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
		for(EventDataEntity racer : eventDataDao.getRacers(eventSessionId)) {
			TeamEscapeEntrantResult teamEscapeEntrantResult = new TeamEscapeEntrantResult();
			teamEscapeEntrantResult.setDistanceToFinish(racer.getDistanceToFinish());
			teamEscapeEntrantResult.setEventDurationInMilliseconds(racer.getEventDurationInMilliseconds());
			teamEscapeEntrantResult.setEventSessionId(eventSessionId);
			teamEscapeEntrantResult.setFinishReason(racer.getFinishReason());
			teamEscapeEntrantResult.setFractionCompleted(racer.getFractionCompleted());
			teamEscapeEntrantResult.setPersonaId(racer.getPersonaId());
			teamEscapeEntrantResult.setRanking(racer.getRank());
			arrayOfTeamEscapeEntrantResult.getTeamEscapeEntrantResult().add(teamEscapeEntrantResult);

			if(!racer.getPersonaId().equals(activePersonaId)) {
				XmppEvent xmppEvent = new XmppEvent(racer.getPersonaId());
				xmppEvent.sendTeamEscapeEnd(teamEscapeEntrantResultResponse);
				if(teamEscapeArbitrationPacket.getRank() == 1) {
					xmppEvent.sendEventTimingOut(eventSessionId);
				}
			}
		}

		TeamEscapeEventResult teamEscapeEventResult = new TeamEscapeEventResult();
		teamEscapeEventResult.setAccolades(getTeamEscapeAccolades(activePersonaId, teamEscapeArbitrationPacket));
		teamEscapeEventResult.setDurability(updateDamageCar(activePersonaId, teamEscapeArbitrationPacket.getCarId(), teamEscapeArbitrationPacket.getNumberOfCollisions(), teamEscapeArbitrationPacket.getEventDurationInMilliseconds()));
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
		
		// Maths begin
		ArrayOfRewardPart arrayOfRewardPart = new ArrayOfRewardPart();
		float exp = 100.0f * (personaEntity.getLevel() / 10.0f);
		exp = isBusted ? 0 : exp;
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)exp, 685, EnumRewardCategory.BASE, EnumRewardType.NONE));
		
		if(!isBusted) {	
			float copsDeployed = exp * (pursuitArbitrationPacket.getCopsDeployed() / 200.0f);
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)copsDeployed, 0, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DEPLOYED));
			
			float copsDisabled = exp * (pursuitArbitrationPacket.getCopsDisabled() / 100.0f);
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)copsDisabled, 0, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DISABLED));
			
			float copsRammed = exp * (pursuitArbitrationPacket.getCopsRammed() / 150.0f);
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)copsRammed, 0, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_RAMMED));
			
			float costToState = exp * ((pursuitArbitrationPacket.getCostToState() / 5000.0f) / 150.0f);
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)costToState, 0, EnumRewardCategory.PURSUIT, EnumRewardType.COST_TO_STATE));
			
			float eventDuration = exp * ((pursuitArbitrationPacket.getEventDurationInMilliseconds() / 2500.0f) / 150.0f);
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)eventDuration, 0, EnumRewardCategory.PURSUIT, EnumRewardType.PURSUIT_LENGTH));
			
			float heat = exp * (pursuitArbitrationPacket.getHeat() / 2.0f);
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)heat, 0, EnumRewardCategory.PURSUIT, EnumRewardType.HEAT_LEVEL));
			
			float infractions = exp * (pursuitArbitrationPacket.getInfractions() / 100.0f);
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)infractions, 0, EnumRewardCategory.PURSUIT, EnumRewardType.INFRACTIONS));
			
			float roadBlocksDodged = exp * (pursuitArbitrationPacket.getRoadBlocksDodged() / 50.0f);
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)roadBlocksDodged, 0, EnumRewardCategory.PURSUIT, EnumRewardType.ROADBLOCKS_DODGED));
			
			float spikeStripsDodged = exp * (pursuitArbitrationPacket.getSpikeStripsDodged() / 50.0f);
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)spikeStripsDodged, 0, EnumRewardCategory.PURSUIT, EnumRewardType.SPIKE_STRIPS_DODGED));
			
			exp += (int)copsRammed + (int)copsDisabled + (int)copsDeployed + (int)costToState + (int)eventDuration + (int)infractions + (int)heat + (int)roadBlocksDodged + (int)spikeStripsDodged;
		}
		// Maths ending
		
		Accolades accolades = new Accolades();
		accolades.setFinalRewards(getFinalReward((int)exp, 685));
		accolades.setHasLeveledUp(false);
		if(!isBusted) {
			accolades.setLuckyDrawInfo(getLuckyDrawInfo(1));
		}
		accolades.setOriginalRewards(getFinalReward((int)exp, 685));
		accolades.setRewardInfo(arrayOfRewardPart);
		return accolades;
	}
	
	private Accolades getRouteAccolades(Long activePersonaId, RouteArbitrationPacket routeArbitrationPacket) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		float exp = 100.0f * (((personaEntity.getLevel() * 2.0f) / 100.0f) + 1.0f);
		
		ArrayOfRewardPart arrayOfRewardPart = new ArrayOfRewardPart();
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)exp, 685, EnumRewardCategory.BASE, EnumRewardType.NONE));
		
		Accolades accolades = new Accolades();
		accolades.setFinalRewards(getFinalReward((int)exp, 685));
		accolades.setHasLeveledUp(false);
		accolades.setLuckyDrawInfo(getLuckyDrawInfo(routeArbitrationPacket.getRank()));
		accolades.setOriginalRewards(getFinalReward((int)exp, 685));
		accolades.setRewardInfo(arrayOfRewardPart);
		return accolades;
	}
	
	private Accolades getDragAccolades(Long activePersonaId, DragArbitrationPacket dragArbitrationPacket) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		
		// Maths begin
		ArrayOfRewardPart arrayOfRewardPart = new ArrayOfRewardPart();
		float exp = 100.0f * (personaEntity.getLevel() / 25.0f);
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)exp, 1337, EnumRewardCategory.BASE, EnumRewardType.NONE));
		
		float rank = dragArbitrationPacket.getRank() == 1 ? exp * 0.5f : dragArbitrationPacket.getRank() == 2 ? exp * 0.3f : dragArbitrationPacket.getRank() == 3 ? exp * 0.2f : exp * 0.1f;
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)rank, 0, EnumRewardCategory.RANK, EnumRewardType.PLAYER_1));
		
		float timeRace = exp * (((60000.0f / dragArbitrationPacket.getEventDurationInMilliseconds()) / 10.0f) + 1.0f);
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)timeRace, 0, EnumRewardCategory.BONUS, EnumRewardType.TIME_BONUS));

		exp += (int)timeRace + (int)rank;
		
		if(dragArbitrationPacket.getPerfectStart() == 1) {
			float perfectStart = exp * 0.2f;
			exp += (int)perfectStart;
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)perfectStart, 0, EnumRewardCategory.BONUS, EnumRewardType.NONE));
		}
		
		if(dragArbitrationPacket.getTopSpeed() >= 70.0f) {
			float highSpeed = exp * 0.2f;
			exp += (int)highSpeed;
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)highSpeed, 0, EnumRewardCategory.BONUS, EnumRewardType.NONE));
		}
		// Maths ending
		
		Accolades accolades = new Accolades();
		accolades.setFinalRewards(getFinalReward((int)exp, 7331));
		accolades.setHasLeveledUp(false);
		accolades.setLuckyDrawInfo(getLuckyDrawInfo(dragArbitrationPacket.getRank()));
		accolades.setOriginalRewards(getFinalReward((int)exp, 7331));
		accolades.setRewardInfo(arrayOfRewardPart);
		return accolades;
	}
	
	private Accolades getTeamEscapeAccolades(Long activePersonaId, TeamEscapeArbitrationPacket teamEscapeArbitrationPacket) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		
		// Maths begin
		ArrayOfRewardPart arrayOfRewardPart = new ArrayOfRewardPart();
		float exp = 100.0f * (personaEntity.getLevel() / 10.0f);
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)exp, 685, EnumRewardCategory.BASE, EnumRewardType.NONE));
		
		float copsDeployed = exp * (teamEscapeArbitrationPacket.getCopsDeployed() / 200.0f);
		copsDeployed = teamEscapeArbitrationPacket.getBustedCount() > 0 && teamEscapeArbitrationPacket.getFinishReason() == 22 ? copsDeployed / (teamEscapeArbitrationPacket.getBustedCount() * 2.0f) : teamEscapeArbitrationPacket.getFinishReason() != 22 ? copsDeployed / 10 : copsDeployed;
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)copsDeployed, 0, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DEPLOYED));
		
		float copsDisabled = exp * (teamEscapeArbitrationPacket.getCopsDisabled() / 100.0f);
		copsDisabled = teamEscapeArbitrationPacket.getBustedCount() > 0 && teamEscapeArbitrationPacket.getFinishReason() == 22 ? copsDisabled / (teamEscapeArbitrationPacket.getBustedCount() * 2.0f) : teamEscapeArbitrationPacket.getFinishReason() != 22 ? copsDisabled / 10 : copsDisabled;
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)copsDisabled, 0, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DISABLED));
		
		float copsRammed = exp * (teamEscapeArbitrationPacket.getCopsRammed() / 150.0f);
		copsRammed = teamEscapeArbitrationPacket.getBustedCount() > 0 && teamEscapeArbitrationPacket.getFinishReason() == 22 ? copsRammed / (teamEscapeArbitrationPacket.getBustedCount() * 2.0f) : teamEscapeArbitrationPacket.getFinishReason() != 22 ? copsRammed / 10 : copsRammed;
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)copsRammed, 0, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_RAMMED));
		
		float costToState = exp * ((teamEscapeArbitrationPacket.getCostToState() / 5000.0f) / 150.0f);
		costToState = teamEscapeArbitrationPacket.getBustedCount() > 0 && teamEscapeArbitrationPacket.getFinishReason() == 22 ? costToState / (teamEscapeArbitrationPacket.getBustedCount() * 2.0f) : teamEscapeArbitrationPacket.getFinishReason() != 22 ? costToState / 10 : costToState;
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)costToState, 0, EnumRewardCategory.PURSUIT, EnumRewardType.COST_TO_STATE));
		
		float infractions = exp * (teamEscapeArbitrationPacket.getInfractions() / 100.0f);
		infractions = teamEscapeArbitrationPacket.getBustedCount() > 0 && teamEscapeArbitrationPacket.getFinishReason() == 22 ? infractions / (teamEscapeArbitrationPacket.getBustedCount() * 2.0f) : teamEscapeArbitrationPacket.getFinishReason() != 22 ? infractions / 10 : infractions;
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)infractions, 0, EnumRewardCategory.PURSUIT, EnumRewardType.INFRACTIONS));
		
		float roadBlocksDodged = exp * (teamEscapeArbitrationPacket.getRoadBlocksDodged() / 50.0f);
		roadBlocksDodged = teamEscapeArbitrationPacket.getBustedCount() > 0 && teamEscapeArbitrationPacket.getFinishReason() == 22 ? roadBlocksDodged / (teamEscapeArbitrationPacket.getBustedCount() * 2.0f) : teamEscapeArbitrationPacket.getFinishReason() != 22 ? roadBlocksDodged / 10 : roadBlocksDodged;
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)roadBlocksDodged, 0, EnumRewardCategory.PURSUIT, EnumRewardType.ROADBLOCKS_DODGED));
		
		float spikeStripsDodged = exp * (teamEscapeArbitrationPacket.getSpikeStripsDodged() / 50.0f);
		spikeStripsDodged = teamEscapeArbitrationPacket.getBustedCount() > 0 && teamEscapeArbitrationPacket.getFinishReason() == 22 ? spikeStripsDodged / (teamEscapeArbitrationPacket.getBustedCount() * 2.0f) : teamEscapeArbitrationPacket.getFinishReason() != 22 ? spikeStripsDodged / 10 : spikeStripsDodged;
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)spikeStripsDodged, 0, EnumRewardCategory.PURSUIT, EnumRewardType.SPIKE_STRIPS_DODGED));
		
		float rank = teamEscapeArbitrationPacket.getRank() == 1 ? exp * 0.5f : teamEscapeArbitrationPacket.getRank() == 2 ? exp * 0.3f : teamEscapeArbitrationPacket.getRank() == 3 ? exp * 0.2f : exp * 0.1f;
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)rank, 0, EnumRewardCategory.RANK, EnumRewardType.PLAYER_1));
		
		exp += (int)copsRammed + (int)copsDisabled + (int)copsDeployed + (int)costToState + (int)infractions + (int)roadBlocksDodged + (int)spikeStripsDodged + (int)rank;
		
		if(teamEscapeArbitrationPacket.getPerfectStart() == 1) {
			float perfectStart = exp * 0.2f;
			exp += (int)perfectStart;
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)perfectStart, 0, EnumRewardCategory.BONUS, EnumRewardType.NONE));
		}
		
		if(teamEscapeArbitrationPacket.getTopSpeed() >= 70.0f) {
			float highSpeed = exp * 0.2f;
			exp += (int)highSpeed;
			arrayOfRewardPart.getRewardPart().add(getRewardPart((int)highSpeed, 0, EnumRewardCategory.BONUS, EnumRewardType.NONE));
		}
		// Maths ending
		
		Accolades accolades = new Accolades();
		accolades.setFinalRewards(getFinalReward((int)exp, 685));
		accolades.setHasLeveledUp(false);
		if(teamEscapeArbitrationPacket.getFinishReason() == 22) {
			accolades.setLuckyDrawInfo(getLuckyDrawInfo(teamEscapeArbitrationPacket.getRank()));
		}
		accolades.setOriginalRewards(getFinalReward((int)exp, 685));
		accolades.setRewardInfo(arrayOfRewardPart);
		return accolades;
	}
	
	private Reward getFinalReward(Integer rep, Integer cash) {
		Reward finalReward = new Reward();
		finalReward.setRep(rep);
		finalReward.setTokens(cash);
		return finalReward;
	}
	
	private LuckyDrawInfo getLuckyDrawInfo(Integer rank) {
		ArrayOfLuckyDrawItem arrayOfLuckyDrawItem = new ArrayOfLuckyDrawItem();
		LuckyDrawItem luckyDrawItem = new LuckyDrawItem();
		luckyDrawItem.setDescription("TEST DROP");
		luckyDrawItem.setHash(-1681514783);
		luckyDrawItem.setIcon("product_nos_x1");
		luckyDrawItem.setRemainingUseCount(0);
		luckyDrawItem.setResellPrice(7331);
		luckyDrawItem.setVirtualItem("nosshot");
		luckyDrawItem.setVirtualItemType("POWERUP");
		luckyDrawItem.setWasSold(true);
		arrayOfLuckyDrawItem.getLuckyDrawItem().add(luckyDrawItem);
		
		LuckyDrawInfo luckyDrawInfo = new LuckyDrawInfo();
		luckyDrawInfo.setCardDeck(CardDecks.forRank(rank));
		luckyDrawInfo.setItems(arrayOfLuckyDrawItem);
		return luckyDrawInfo;
	}
	
	private RewardPart getRewardPart(Integer rep, Integer cash, EnumRewardCategory category, EnumRewardType type) {
		RewardPart rewardPart = new RewardPart();
		rewardPart.setRepPart(rep);
		rewardPart.setRewardCategory(category);
		rewardPart.setRewardType(type);
		rewardPart.setTokenPart(cash);
		return rewardPart;
	}
	
	private void sendReportFromServer(Long activePersonaId, Integer carId, Long hacksDetected) {
		socialBo.sendReport(0L, activePersonaId, 3, "Server sent a report for cheat", carId, 0, hacksDetected);
	}
	
	private Integer updateDamageCar(Long personaId, Long carId, Integer numberOfCollision, Long eventDuration) {
		OwnedCarTrans ownedCarTrans = personaBo.getDefaultCar(personaId);
		if(ownedCarTrans.getDurability() > 10) {
			Integer calcDamage = numberOfCollision + ((int)(eventDuration / 60000)) * 2;
			Integer newCarDamage = ownedCarTrans.getDurability() - calcDamage;
			ownedCarTrans.setDurability(newCarDamage < 10 ? 10 : newCarDamage);

			CarSlotEntity carSlotEntity = carSlotDao.findById(carId);
			if(carSlotEntity != null) {
				carSlotEntity.setOwnedCarTrans(MarshalXML.marshal(ownedCarTrans));
				carSlotDao.update(carSlotEntity);
			}
		}
		return ownedCarTrans.getDurability();
	}
	
}
