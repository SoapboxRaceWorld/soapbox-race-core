/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.PersonaBO;
import com.soapboxrace.core.bo.RequestSessionInfo;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.jaxb.http.BadgeBundle;
import com.soapboxrace.jaxb.util.JAXBUtility;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.io.InputStream;

@Path("/badges")
public class Badges {

    @EJB
    private PersonaBO personaBO;

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @Path("/set")
    @PUT
    @Secured
    public String set(InputStream inputStream, @HeaderParam("securityToken") String securityToken) {
        Long activePersonaId = requestSessionInfo.getActivePersonaId();

        if (activePersonaId == 0L) {
            throw new EngineException(EngineExceptionCode.FailedSessionSecurityPolicy, false);
        }

        personaBO.updateBadges(activePersonaId, JAXBUtility.unMarshal(inputStream, BadgeBundle.class));

        return "";
    }
}
