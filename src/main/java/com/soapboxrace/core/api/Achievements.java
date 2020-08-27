/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.AchievementBO;
import com.soapboxrace.core.bo.RequestSessionInfo;
import com.soapboxrace.jaxb.http.AchievementRewards;
import com.soapboxrace.jaxb.http.AchievementsPacket;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/achievements")
public class Achievements {

    @EJB
    private AchievementBO achievementBO;

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @GET
    @Secured
    @Path("/loadall")
    @Produces(MediaType.APPLICATION_XML)
    public AchievementsPacket loadall() {
        return achievementBO.loadAll(requestSessionInfo.getActivePersonaId());
    }

    @GET
    @Secured
    @Path("/redeemreward")
    @Produces(MediaType.APPLICATION_XML)
    public AchievementRewards redeemreward(@QueryParam(
            "achievementRankId") Long achievementRankId) {
        return achievementBO.redeemReward(requestSessionInfo.getActivePersonaId(), achievementRankId);
    }
}
