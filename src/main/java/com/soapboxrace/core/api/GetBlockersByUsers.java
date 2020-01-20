/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.SocialRelationshipBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/getblockersbyusers")
public class GetBlockersByUsers {
    @EJB
    private SocialRelationshipBO socialRelationshipBO;

    @EJB
    private TokenSessionBO tokenSessionBO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public Response getBlockedUserList(@HeaderParam("userId") Long userId,
                                       @HeaderParam("securityToken") String securityToken) {
        Long activePersonaId = tokenSessionBO.getActivePersonaId(securityToken);

        if (activePersonaId == 0) {
            throw new EngineException(EngineExceptionCode.FailedSessionSecurityPolicy);
        }

        return Response.ok().entity(socialRelationshipBO.getBlockersByUsers(activePersonaId)).build();
    }
}
