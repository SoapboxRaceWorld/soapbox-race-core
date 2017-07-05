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
		pursuitEventResult.setAccolades(getAccolades(1, isBusted));
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
		routeEventResult.setAccolades(getAccolades(routeArbitrationPacket.getRank(), false));
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
		teamEscapeEventResult.setAccolades(getAccolades(teamEscapeArbitrationPacket.getRank(), false));
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
	
	private Accolades getDragAccolades(Long activePersonaId, DragArbitrationPacket dragArbitrationPacket) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		float exp = 100.0f * (((personaEntity.getLevel() * 2.0f) / 100.0f) + 1.0f);
		
		ArrayOfRewardPart arrayOfRewardPart = new ArrayOfRewardPart();
		RewardPart rewardPart = new RewardPart();
		rewardPart.setRepPart((int)exp);
		rewardPart.setRewardCategory(EnumRewardCategory.BASE);
		rewardPart.setRewardType(EnumRewardType.NONE);
		rewardPart.setTokenPart(1337);
		arrayOfRewardPart.getRewardPart().add(rewardPart);
		
		float rank = dragArbitrationPacket.getRank() == 1 ? exp * 0.5f : dragArbitrationPacket.getRank() == 2 ? exp * 0.3f : dragArbitrationPacket.getRank() == 3 ? exp * 0.2f : exp * 0.1f;	 
		exp += (int)rank;
		rewardPart = new RewardPart();
		rewardPart.setRepPart((int)rank);
		rewardPart.setRewardCategory(EnumRewardCategory.RANK);
		rewardPart.setRewardType(EnumRewardType.PLAYER_1);
		arrayOfRewardPart.getRewardPart().add(rewardPart);
		
		float timeRace = exp * (((60000.0f / dragArbitrationPacket.getEventDurationInMilliseconds()) / 10.0f) + 1.0f);
		exp += (int)timeRace;
		rewardPart = new RewardPart();
		rewardPart.setRepPart((int)timeRace);
		rewardPart.setRewardCategory(EnumRewardCategory.BONUS);
		rewardPart.setRewardType(EnumRewardType.TIME_BONUS);
		arrayOfRewardPart.getRewardPart().add(rewardPart);
		
		if(dragArbitrationPacket.getPerfectStart() == 1) {
			float perfectStart = exp * 0.2f;
			exp += (int)perfectStart;
			rewardPart = new RewardPart();
			rewardPart.setRepPart((int)perfectStart);
			rewardPart.setRewardCategory(EnumRewardCategory.BONUS);
			rewardPart.setRewardType(EnumRewardType.NONE);
			arrayOfRewardPart.getRewardPart().add(rewardPart);
		}
		
		if(dragArbitrationPacket.getTopSpeed() >= 70.0f) {
			float highSpeed = exp * 0.2f;
			exp += (int)highSpeed;
			rewardPart = new RewardPart();
			rewardPart.setRepPart((int)highSpeed);
			rewardPart.setRewardCategory(EnumRewardCategory.BONUS);
			rewardPart.setRewardType(EnumRewardType.NONE);
			arrayOfRewardPart.getRewardPart().add(rewardPart);
		}
		
		Reward originalRewards = new Reward();
		originalRewards.setRep((int)exp);
		originalRewards.setTokens(1337);
		
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
		luckyDrawInfo.setCardDeck(CardDecks.forRank(dragArbitrationPacket.getRank()));
		luckyDrawInfo.setItems(arrayOfLuckyDrawItem);
			
		Reward finalReward = new Reward();
		finalReward.setRep((int)exp);
		finalReward.setTokens(1337);
		
		Accolades accolades = new Accolades();
		accolades.setFinalRewards(finalReward);
		accolades.setHasLeveledUp(false);
		accolades.setLuckyDrawInfo(luckyDrawInfo);
		accolades.setOriginalRewards(originalRewards);
		accolades.setRewardInfo(arrayOfRewardPart);
		return accolades;
	}
	
	private Accolades getAccolades(Integer rank, Boolean isBusted) {
		ArrayOfRewardPart arrayOfRewardPart = new ArrayOfRewardPart();
		RewardPart rewardPart = new RewardPart();
		rewardPart.setRepPart(256);
		rewardPart.setRewardCategory(EnumRewardCategory.BASE);
		rewardPart.setRewardType(EnumRewardType.NONE);
		rewardPart.setTokenPart(852);
		arrayOfRewardPart.getRewardPart().add(rewardPart);
		
		Reward originalRewards = new Reward();
		originalRewards.setRep(562);
		originalRewards.setTokens(845);
		
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
			
		Reward finalReward = new Reward();
		finalReward.setRep(7331);
		finalReward.setTokens(1337);
		
		Accolades accolades = new Accolades();
		accolades.setFinalRewards(finalReward);
		accolades.setHasLeveledUp(false);
		if(!isBusted) {
			accolades.setLuckyDrawInfo(luckyDrawInfo);
		}
		accolades.setOriginalRewards(originalRewards);
		accolades.setRewardInfo(arrayOfRewardPart);
		return accolades;
	}
	
	private void sendReportFromServer(Long activePersonaId, Integer carId, Long hacksDetected) {
		socialBo.sendReport(0L, activePersonaId, 3, "Server was send a report for cheat", carId, 0, hacksDetected);
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
