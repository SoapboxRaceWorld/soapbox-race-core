/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.AdminBO;
import com.soapboxrace.core.bo.RequestSessionInfo;
import com.soapboxrace.core.bo.SocialBO;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/Social")
public class Social {

    @EJB
    private SocialBO bo;

    @EJB
    private AdminBO adminBo;

    @Inject
    private RequestSessionInfo requestSessionInfo;

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
        if (requestSessionInfo.isAdmin() && description.startsWith("/")) {
            adminBo.sendCommand(requestSessionInfo.getActivePersonaId(), abuserPersonaId, description);
        } else {
            bo.sendReport(personaId, abuserPersonaId, petitionType, description, customCarID, chatMinutes, 0L);
        }
        return "";
    }
}
