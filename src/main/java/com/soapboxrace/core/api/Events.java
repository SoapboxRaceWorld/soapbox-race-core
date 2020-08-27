/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.*;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.util.JAXBUtility;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/events")
public class Events {

    @EJB
    private EventBO eventBO;

    @EJB
    private EventsBO eventsBO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private PersonaBO personaBO;

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @GET
    @Secured
    @Path("/availableatlevel")
    @Produces(MediaType.APPLICATION_XML)
    public EventsPacket availableAtLevel() {
        Long activePersonaId = requestSessionInfo.getActivePersonaId();
        OwnedCarTrans defaultCar = personaBO.getDefaultCar(activePersonaId);
        int carClassHash = defaultCar.getCustomCar().getCarClassHash();

        EventsPacket eventsPacket = new EventsPacket();
        ArrayOfEventDefinition arrayOfEventDefinition = new ArrayOfEventDefinition();
        List<EventEntity> availableAtLevel = eventBO.availableAtLevel(activePersonaId);
        for (EventEntity eventEntity : availableAtLevel) {
            if (eventEntity.getCarClassHash() != 607077938 && carClassHash != eventEntity.getCarClassHash()) {
                eventEntity.setLocked(true);
            }
            arrayOfEventDefinition.getEventDefinition().add(getEventDefinitionWithId(eventEntity));
        }
        eventsPacket.setEvents(arrayOfEventDefinition);
        return eventsPacket;
    }

    private EventDefinition getEventDefinitionWithId(EventEntity eventEntity) {
        EventDefinition eventDefinition = new EventDefinition();
        eventDefinition.setCarClassHash(eventEntity.getCarClassHash());
        eventDefinition.setCoins(0);
        Vector3 vector3 = new Vector3();
        vector3.setX(0);
        vector3.setY(0);
        vector3.setZ(0);
        eventDefinition.setEngagePoint(vector3);
        eventDefinition.setEventId(eventEntity.getId());
        eventDefinition.setEventLocalization(953953223);
        eventDefinition.setEventModeDescriptionLocalization(1204604434);
        eventDefinition.setEventModeIcon("GameModeIcon_Sprint");
        eventDefinition.setEventModeId(eventEntity.getEventModeId());
        eventDefinition.setEventModeLocalization(-1152300457);
        eventDefinition.setIsEnabled(eventEntity.getIsEnabled());
        eventDefinition.setIsLocked(eventEntity.getIsLocked());
        eventDefinition.setLaps(0);
        eventDefinition.setLength(0);
        eventDefinition.setMaxClassRating(eventEntity.getMaxCarClassRating());
        eventDefinition.setMaxEntrants(2);
        eventDefinition.setMaxLevel(eventEntity.getMaxLevel());
        eventDefinition.setMinClassRating(eventEntity.getMinCarClassRating());
        eventDefinition.setMinEntrants(2);
        eventDefinition.setMinLevel(eventEntity.getMinLevel());
        eventDefinition.setRegionLocalization(0);
        eventDefinition.setRewardModes(new ArrayOfInt());
        eventDefinition.setTimeLimit(0);
        eventDefinition.setTrackLayoutMap("nothing");
        eventDefinition.setTrackLocalization(-491522157);
        return eventDefinition;
    }

    @GET
    @Secured
    @Path("/gettreasurehunteventsession")
    @Produces(MediaType.APPLICATION_XML)
    public TreasureHuntEventSession getTreasureHuntEventSession() {
        if (parameterBO.getBoolParam("ENABLE_TREASURE_HUNT")) {
            return eventsBO.getTreasureHuntEventSession(requestSessionInfo.getActivePersonaId());
        }
        return new TreasureHuntEventSession();
    }

    @GET
    @Secured
    @Path("/notifycoincollected")
    @Produces(MediaType.APPLICATION_XML)
    public String notifyCoinCollected(@QueryParam("coins") Integer coins) {
        return JAXBUtility.marshal(eventsBO.notifyCoinCollected(requestSessionInfo.getActivePersonaId(), coins));
    }

    @GET
    @Secured
    @Path("/accolades")
    @Produces(MediaType.APPLICATION_XML)
    public String accolades() {
        return JAXBUtility.marshal(eventsBO.accolades(requestSessionInfo.getActivePersonaId(), false));
    }

    @GET
    @Secured
    @Path("/instancedaccolades")
    @Produces(MediaType.APPLICATION_XML)
    public String instancedAccolades(@QueryParam("eventSessionId") Long eventSessionId) {
        return "";
    }
}
