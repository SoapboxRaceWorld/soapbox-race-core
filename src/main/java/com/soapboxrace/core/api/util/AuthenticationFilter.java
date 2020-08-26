/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api.util;

import com.soapboxrace.core.dao.TokenSessionDAO;
import com.soapboxrace.core.jpa.TokenSessionEntity;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.Date;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @EJB
    private TokenSessionDAO tokenDAO;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String userIdStr = requestContext.getHeaderString("userId");
        String securityToken = requestContext.getHeaderString("securityToken");
        if (userIdStr == null || securityToken == null || userIdStr.isEmpty() || securityToken.isEmpty()) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        Long userId = Long.valueOf(userIdStr);
        try {
            TokenSessionEntity tokenSessionEntity = validateToken(userId, securityToken);
            requestContext.setProperty("tokenSession", tokenSessionEntity);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private TokenSessionEntity validateToken(Long userId, String securityToken) {
        TokenSessionEntity tokenSessionEntity = tokenDAO.findById(securityToken);
        if (tokenSessionEntity == null || !tokenSessionEntity.getUserEntity().getId().equals(userId)) {
            throw new NotAuthorizedException("Invalid Token");
        }
        long time = new Date().getTime();
        long tokenTime = tokenSessionEntity.getExpirationDate().getTime();
        if (time > tokenTime) {
            throw new NotAuthorizedException("Invalid Token");
        }
        return tokenSessionEntity;
    }
}
