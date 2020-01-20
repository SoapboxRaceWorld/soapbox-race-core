/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.SocialRelationshipBO;
import com.soapboxrace.core.bo.TokenSessionBO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/addfriendrequest")
public class AddFriendRequest {
    @EJB
    private TokenSessionBO tokenSessionBO;

    @EJB
    private SocialRelationshipBO socialRelationshipBO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public Response addFriendRequest(@HeaderParam("securityToken") String securityToken,
                                     @QueryParam("displayName") String displayName) {
        Long activePersonaId = tokenSessionBO.getActivePersonaId(securityToken);
        return Response.ok().entity(socialRelationshipBO.addFriend(activePersonaId, displayName)).build();
    }
}