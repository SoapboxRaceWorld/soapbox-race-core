/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.AdminBO;
import com.soapboxrace.core.bo.SocialBO;
import com.soapboxrace.core.bo.TokenSessionBO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/Social")
public class Social {

    @EJB
    private SocialBO bo;

    @EJB
    private TokenSessionBO tokenSessionBo;

    @EJB
    private AdminBO adminBo;

    @POST
    @Secured
    @Path("/petition")
    @Produces(MediaType.APPLICATION_XML)
    public String petition(@HeaderParam("securityToken") String securityToken, @QueryParam("personaId") Long personaId,
                           @QueryParam("abuserPersonaId") Long abuserPersonaId,
                           @QueryParam("petitionType") Integer petitionType,
                           @QueryParam("description") String description,
                           @QueryParam("customCarID") Integer customCarID,
                           @QueryParam("chatMinutes") Integer chatMinutes) {
        if (tokenSessionBo.isAdmin(securityToken) && description.startsWith("/")) {
            adminBo.sendCommand(tokenSessionBo.getActivePersonaId(securityToken), abuserPersonaId, description);
        } else {
            bo.sendReport(personaId, abuserPersonaId, petitionType, description, customCarID, chatMinutes, 0L);
        }
        return "";
    }
}
