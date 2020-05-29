/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.bo.SocialRelationshipBO;
import com.soapboxrace.core.bo.AdminBO;
import com.soapboxrace.core.bo.TokenSessionBO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/addfriendrequest")
public class AddFriendRequest {
    @EJB
    private AdminBO adminBO;

    @EJB
    private TokenSessionBO tokenSessionBO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private SocialRelationshipBO socialRelationshipBO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public Response addFriendRequest(@HeaderParam("securityToken") String securityToken,
                                     @QueryParam("displayName") String displayName) {
        Long activePersonaId = tokenSessionBO.getActivePersonaId(securityToken);

        if (tokenSessionBO.isAdmin(securityToken) && displayName.startsWith("/")) {
            String[] abuseCase = displayName.split(" ");
            Long abuserId = personaDAO.findByName(abuseCase[1].trim()).getPersonaId();
            if (abuserId != null) {
                adminBO.sendCommand(tokenSessionBO.getActivePersonaId(securityToken), abuserId, displayName);
            } else {
                // System.out.println("Command: " + abuseCase[0] + " Name: " + abuseCase[1] + " (NOT FOUND)");
            }
        }
        return Response.ok().entity(socialRelationshipBO.addFriend(activePersonaId, displayName)).build();
    }
}