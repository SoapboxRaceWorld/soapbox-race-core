package com.soapboxrace.core.bo;

import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.jaxb.http.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;

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
        return eventResultPursuitBO.handlePursuitEnd(eventSessionEntity, activePersonaId, pursuitArbitrationPacket, isBusted);
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
