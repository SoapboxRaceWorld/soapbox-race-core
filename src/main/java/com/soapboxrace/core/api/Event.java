package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.EventBO;
import com.soapboxrace.core.bo.EventResultBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.EventMode;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.util.UnmarshalXML;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Path("/event")
public class Event {

    @EJB
    private TokenSessionBO tokenBO;

    @EJB
    private EventBO eventBO;

    @EJB
    private EventResultBO eventResultBO;

    @POST
    @Secured
    @Path("/abort")
    @Produces(MediaType.APPLICATION_XML)
    public String abort(@QueryParam("eventSessionId") Long eventSessionId) {
        return "";
    }

    @PUT
    @Secured
    @Path("/launched")
    @Produces(MediaType.APPLICATION_XML)
    public String launched(@HeaderParam("securityToken") String securityToken, @QueryParam("eventSessionId") Long eventSessionId) {
        Long activePersonaId = tokenBO.getActivePersonaId(securityToken);
        eventBO.createEventDataSession(activePersonaId, eventSessionId);
        return "";
    }

    @POST
    @Secured
    @Path("/arbitration")
    @Produces(MediaType.APPLICATION_XML)
    public Object arbitration(InputStream arbitrationXml, @HeaderParam("securityToken") String securityToken,
                              @QueryParam("eventSessionId") Long eventSessionId) {
        EventSessionEntity eventSessionEntity = eventBO.findEventSessionById(eventSessionId);
        EventEntity event = eventSessionEntity.getEvent();
        EventMode eventMode = EventMode.fromId(event.getEventModeId());
        Long activePersonaId = tokenBO.getActivePersonaId(securityToken);

        switch (eventMode) {
            case CIRCUIT:
            case SPRINT:
                RouteArbitrationPacket routeArbitrationPacket = UnmarshalXML.unMarshal(arbitrationXml, RouteArbitrationPacket.class);
                return eventResultBO.handleRaceEnd(eventSessionEntity, activePersonaId, routeArbitrationPacket);
            case DRAG:
                DragArbitrationPacket dragArbitrationPacket = UnmarshalXML.unMarshal(arbitrationXml, DragArbitrationPacket.class);
                return eventResultBO.handleDragEnd(eventSessionEntity, activePersonaId, dragArbitrationPacket);
            case MEETINGPLACE:
                break;
            case PURSUIT_MP:
                TeamEscapeArbitrationPacket teamEscapeArbitrationPacket = UnmarshalXML.unMarshal(arbitrationXml, TeamEscapeArbitrationPacket.class);
                return eventResultBO.handleTeamEscapeEnd(eventSessionEntity, activePersonaId, teamEscapeArbitrationPacket);
            case PURSUIT_SP:
                PursuitArbitrationPacket pursuitArbitrationPacket = UnmarshalXML.unMarshal(arbitrationXml, PursuitArbitrationPacket.class);
                return eventResultBO.handlePursitEnd(eventSessionEntity, activePersonaId, pursuitArbitrationPacket, false);
            default:
                break;
        }

        return "";
    }

    @POST
    @Secured
    @Path("/bust")
    @Produces(MediaType.APPLICATION_XML)
    public PursuitEventResult bust(InputStream bustXml, @HeaderParam("securityToken") String securityToken, @QueryParam("eventSessionId") Long eventSessionId) {
        EventSessionEntity eventSessionEntity = eventBO.findEventSessionById(eventSessionId);
        PursuitArbitrationPacket pursuitArbitrationPacket = UnmarshalXML.unMarshal(bustXml, PursuitArbitrationPacket.class);
        Long activePersonaId = tokenBO.getActivePersonaId(securityToken);
        return eventResultBO.handlePursitEnd(eventSessionEntity, activePersonaId, pursuitArbitrationPacket, true);
    }
}
