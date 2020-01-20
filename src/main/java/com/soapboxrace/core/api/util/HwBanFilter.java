/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

import com.soapboxrace.core.bo.HardwareInfoBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.jpa.UserEntity;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@HwBan
@Provider
@Priority(Priorities.AUTHORIZATION)
public class HwBanFilter implements ContainerRequestFilter {

    @EJB
    private HardwareInfoBO hardwareInfoBO;

    @EJB
    private TokenSessionBO tokenBO;

    @Context
    private HttpServletRequest sr;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String securityToken = requestContext.getHeaderString("securityToken");
        UserEntity user = tokenBO.getUser(securityToken);
        String gameHardwareHash = user.getGameHardwareHash();
        if (hardwareInfoBO.isHardwareHashBanned(gameHardwareHash)) {
            requestContext.abortWith(Response.status(Response.Status.GONE).build());
        }
    }

}
