/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.RequestSessionInfo;
import com.soapboxrace.core.bo.SocialRelationshipBO;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/removefriend")
public class RemoveFriend {

    @EJB
    private SocialRelationshipBO socialRelationshipBO;

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @GET
    @Secured
    public Response removefriend(@QueryParam("friendPersonaId") Long friendPersonaId) {
        return Response.ok().entity(socialRelationshipBO.removeFriend(requestSessionInfo.getActivePersonaId(),
                friendPersonaId)).build();
    }
}