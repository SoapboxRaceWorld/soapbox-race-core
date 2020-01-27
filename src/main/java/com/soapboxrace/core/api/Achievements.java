/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.AchievementBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.jaxb.http.AchievementRewards;
import com.soapboxrace.jaxb.http.AchievementsPacket;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/achievements")
public class Achievements {
    @EJB
    private TokenSessionBO tokenSessionBO;

    @EJB
    private AchievementBO achievementBO;

    @GET
    @Secured
    @Path("/loadall")
    @Produces(MediaType.APPLICATION_XML)
    public AchievementsPacket loadall(@HeaderParam("securityToken") String securityToken) {
        Long personaId = tokenSessionBO.getActivePersonaId(securityToken);

        return achievementBO.loadAll(personaId);
    }

    @GET
    @Secured
    @Path("/redeemreward")
    @Produces(MediaType.APPLICATION_XML)
    public AchievementRewards redeemreward(@HeaderParam("securityToken") String securityToken, @QueryParam(
            "achievementRankId") Long achievementRankId) {
        return achievementBO.redeemReward(tokenSessionBO.getActivePersonaId(securityToken), achievementRankId);
    }
}
