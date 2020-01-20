/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.bo.PersonaBO;
import com.soapboxrace.core.bo.RewardPursuitBO;
import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.PursuitArbitrationPacket;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/testReward")
public class TestReward {

    @EJB
    private RewardPursuitBO pursuitBO;

    @EJB
    private PersonaBO personaBO;

    @EJB
    private EventDAO eventDAO;

    @POST
    @Path("/pursuit/{personaId}/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RewardVO pursuit(PursuitArbitrationPacket pursuitArbitrationPacket,
                            @PathParam(value = "personaId") Long personaId,
                            @PathParam(value = "eventId") Integer eventId) {
        PersonaEntity personaEntity = personaBO.getPersonaById(personaId);
        RewardVO rewardVO = pursuitBO.getRewardVO(personaEntity);
        EventEntity eventEntity = eventDAO.findById(eventId);
        pursuitBO.setPursuitRewards(personaEntity, eventEntity, pursuitArbitrationPacket, rewardVO);
        return rewardVO;
    }

    @GET
    @Path("/pursuit")
    @Produces(MediaType.APPLICATION_JSON)
    public PursuitArbitrationPacket vai() {
        return new PursuitArbitrationPacket();
    }
}
