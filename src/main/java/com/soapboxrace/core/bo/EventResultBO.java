/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

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

    public PursuitEventResult handlePursitEnd(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                              PursuitArbitrationPacket pursuitArbitrationPacket,
                                              Boolean isBusted) {
        pursuitArbitrationPacket.setHacksDetected(pursuitArbitrationPacket.getHacksDetected() & ~32);
        return eventResultPursuitBO.handlePursuitEnd(eventSessionEntity, activePersonaId, pursuitArbitrationPacket,
                isBusted);
    }

    public RouteEventResult handleRaceEnd(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                          RouteArbitrationPacket routeArbitrationPacket) {
        routeArbitrationPacket.setHacksDetected(routeArbitrationPacket.getHacksDetected() & ~32);
        return eventResultRouteBO.handleRaceEnd(eventSessionEntity, activePersonaId, routeArbitrationPacket);
    }

    public DragEventResult handleDragEnd(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                         DragArbitrationPacket dragArbitrationPacket) {
        dragArbitrationPacket.setHacksDetected(dragArbitrationPacket.getHacksDetected() & ~32);
        return eventResultDragBO.handleDragEnd(eventSessionEntity, activePersonaId, dragArbitrationPacket);
    }

    public TeamEscapeEventResult handleTeamEscapeEnd(EventSessionEntity eventSessionEntity, Long activePersonaId,
                                                     TeamEscapeArbitrationPacket teamEscapeArbitrationPacket) {
        teamEscapeArbitrationPacket.setHacksDetected(teamEscapeArbitrationPacket.getHacksDetected() & ~32);
        return eventResultTeamEscapeBO.handleTeamEscapeEnd(eventSessionEntity, activePersonaId,
                teamEscapeArbitrationPacket);
    }

}
