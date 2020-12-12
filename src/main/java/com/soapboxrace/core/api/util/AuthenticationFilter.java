/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

import com.soapboxrace.core.bo.RequestSessionInfo;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.jpa.TokenSessionEntity;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @EJB
    private TokenSessionBO tokenSessionBO;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String userIdStr = requestContext.getHeaderString("userId");
        String securityToken = requestContext.getHeaderString("securityToken");
        if (userIdStr == null || securityToken == null || userIdStr.isEmpty() || securityToken.isEmpty()) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        Long userId = Long.valueOf(userIdStr);
        try {
            TokenSessionEntity tokenSessionEntity = tokenSessionBO.validateToken(userId, securityToken);
            requestSessionInfo.setTokenSessionEntity(tokenSessionEntity);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
