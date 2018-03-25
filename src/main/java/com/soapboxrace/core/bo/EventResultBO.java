package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.jaxb.http.DragArbitrationPacket;
import com.soapboxrace.jaxb.http.DragEventResult;
import com.soapboxrace.jaxb.http.PursuitArbitrationPacket;
import com.soapboxrace.jaxb.http.PursuitEventResult;
import com.soapboxrace.jaxb.http.RouteArbitrationPacket;
import com.soapboxrace.jaxb.http.RouteEventResult;
import com.soapboxrace.jaxb.http.TeamEscapeArbitrationPacket;
import com.soapboxrace.jaxb.http.TeamEscapeEventResult;

@Stateless
public class EventResultBO {

	@EJB
	private EventResultRouteBO eventResultRouteBO;

	@EJB
	private EventResultDragBO eventResultDragBO;

	@EJB
	private EventResultTeamEscapeBO eventResultTeamEscapeBO;

	@EJB
	private EventResultPursuitBO eventResultPursuitBO;

	public PursuitEventResult handlePursitEnd(EventSessionEntity eventSessionEntity, Long activePersonaId, PursuitArbitrationPacket pursuitArbitrationPacket,
			Boolean isBusted) {
		return eventResultPursuitBO.handlePursitEnd(eventSessionEntity, activePersonaId, pursuitArbitrationPacket, isBusted);
	}

	public RouteEventResult handleRaceEnd(EventSessionEntity eventSessionEntity, Long activePersonaId, RouteArbitrationPacket routeArbitrationPacket) {
		return eventResultRouteBO.handleRaceEnd(eventSessionEntity, activePersonaId, routeArbitrationPacket);
	}

	public DragEventResult handleDragEnd(EventSessionEntity eventSessionEntity, Long activePersonaId, DragArbitrationPacket dragArbitrationPacket) {
		return eventResultDragBO.handleDragEnd(eventSessionEntity, activePersonaId, dragArbitrationPacket);
	}

	public TeamEscapeEventResult handleTeamEscapeEnd(EventSessionEntity eventSessionEntity, Long activePersonaId,
			TeamEscapeArbitrationPacket teamEscapeArbitrationPacket) {
		return eventResultTeamEscapeBO.handleTeamEscapeEnd(eventSessionEntity, activePersonaId, teamEscapeArbitrationPacket);
	}

}
