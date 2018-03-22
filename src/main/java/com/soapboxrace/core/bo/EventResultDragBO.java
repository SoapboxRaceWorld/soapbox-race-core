package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.EventDataDAO;
import com.soapboxrace.core.dao.EventSessionDAO;
import com.soapboxrace.core.jpa.EventDataEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppEvent;
import com.soapboxrace.jaxb.http.ArrayOfDragEntrantResult;
import com.soapboxrace.jaxb.http.DragArbitrationPacket;
import com.soapboxrace.jaxb.http.DragEntrantResult;
import com.soapboxrace.jaxb.http.DragEventResult;
import com.soapboxrace.jaxb.http.ExitPath;
import com.soapboxrace.jaxb.xmpp.XMPP_DragEntrantResultType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypeDragEntrantResult;

@Stateless
public class EventResultDragBO {

	@EJB
	private EventSessionDAO eventSessionDao;

	@EJB
	private EventDataDAO eventDataDao;

	@EJB
	private OpenFireSoapBoxCli openFireSoapBoxCli;

	@EJB
	private RewardDragBO rewardDragBO;

	@EJB
	private CarDamageBO carDamageBO;

	public DragEventResult handleDragEnd(EventSessionEntity eventSessionEntity, Long activePersonaId, DragArbitrationPacket dragArbitrationPacket) {
		Long eventSessionId = eventSessionEntity.getId();
		eventSessionEntity.setEnded(System.currentTimeMillis());

		eventSessionDao.update(eventSessionEntity);

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
		dragEventResult.setAccolades(rewardDragBO.getDragAccolades(activePersonaId, dragArbitrationPacket, eventSessionEntity));
		dragEventResult.setDurability(carDamageBO.updateDamageCar(activePersonaId, dragArbitrationPacket, dragArbitrationPacket.getNumberOfCollisions()));
		dragEventResult.setEntrants(arrayOfDragEntrantResult);
		dragEventResult.setEventId(eventDataEntity.getEvent().getId());
		dragEventResult.setEventSessionId(eventSessionId);
		dragEventResult.setExitPath(ExitPath.EXIT_TO_FREEROAM);
		dragEventResult.setInviteLifetimeInMilliseconds(0);
		dragEventResult.setLobbyInviteId(0);
		dragEventResult.setPersonaId(activePersonaId);
		return dragEventResult;
	}

}
