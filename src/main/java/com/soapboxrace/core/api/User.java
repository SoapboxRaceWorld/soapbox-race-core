/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.LauncherChecks;
import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.*;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.jaxb.http.UserInfo;
import com.soapboxrace.jaxb.login.LoginStatusVO;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("User")
public class User {

    @Context
    private HttpServletRequest sr;

    @EJB
    private AuthenticationBO authenticationBO;

    @EJB
    private UserBO userBO;

    @EJB
    private TokenSessionBO tokenBO;

    @EJB
    private OnlineUsersBO onlineUsersBO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private PresenceBO presenceBO;

    @EJB
    private MatchmakingBO matchmakingBO;

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @POST
    @Secured
    @Path("GetPermanentSession")
    @Produces(MediaType.APPLICATION_XML)
    public Response getPermanentSession() {
        long numberOfUsersOnlineNow = onlineUsersBO.getOnlineUsersStats().getNumberOfOnline();
        int maxOnlinePlayers = parameterBO.getIntParam("MAX_ONLINE_PLAYERS", -1);

        if (maxOnlinePlayers != -1) {
            if (numberOfUsersOnlineNow >= maxOnlinePlayers) {
                throw new EngineException(EngineExceptionCode.MaximumUsersLoggedInHardCapReached, true);
            }
        }

        UserEntity userEntity = requestSessionInfo.getUser();
        tokenBO.deleteByUserId(userEntity.getId());
        String randomUUID = tokenBO.createToken(userEntity, sr.getRemoteHost());
        UserInfo userInfo = userBO.getUserInfo(userEntity);
        userInfo.getUser().setSecurityToken(randomUUID);
        userBO.createXmppUser(userInfo);
        return Response.ok(userInfo).build();
    }

    @POST
    @Secured
    @Path("SecureLoginPersona")
    @Produces(MediaType.APPLICATION_XML)
    public String secureLoginPersona(@QueryParam("personaId") Long personaId) {
        tokenBO.setActivePersonaId(requestSessionInfo.getTokenSessionEntity(), personaId);
        userBO.secureLoginPersona(requestSessionInfo.getUser().getId(), personaId);
        // Question: Why is this here?
        // Answer: Weird things happen sometimes.
        matchmakingBO.removePlayerFromQueue(personaId);
        return "";
    }

    @POST
    @Secured
    @Path("SecureLogoutPersona")
    @Produces(MediaType.APPLICATION_XML)
    public String secureLogoutPersona(@QueryParam("personaId") Long personaId) {
        if (personaId.equals(requestSessionInfo.getActivePersonaId())) {
            presenceBO.removePresence(requestSessionInfo.getActivePersonaId());
            matchmakingBO.removePlayerFromQueue(requestSessionInfo.getActivePersonaId());
        }

        tokenBO.setActivePersonaId(requestSessionInfo.getTokenSessionEntity(), 0L);

        return "";
    }

    @POST
    @Secured
    @Path("SecureLogout")
    @Produces(MediaType.APPLICATION_XML)
    public String secureLogout() {
        Long activePersonaId = requestSessionInfo.getActivePersonaId();
        if (!Objects.isNull(activePersonaId) && !activePersonaId.equals(0L)) {
            tokenBO.setActivePersonaId(requestSessionInfo.getTokenSessionEntity(), 0L);
            presenceBO.removePresence(activePersonaId);
        }

        tokenBO.deleteByUserId(requestSessionInfo.getUser().getId());

        return "";
    }

    @GET
    @Path("authenticateUser")
    @Produces(MediaType.APPLICATION_XML)
    @LauncherChecks
    public Response authenticateUser(@QueryParam("email") String email, @QueryParam("password") String password) {
        LoginStatusVO loginStatusVO = tokenBO.login(email, password, sr);
        if (loginStatusVO.isLoginOk()) {
            return Response.ok(loginStatusVO).build();
        }
        return Response.serverError().entity(loginStatusVO).build();
    }

    @GET
    @Path("createUser")
    @Produces(MediaType.APPLICATION_XML)
    @LauncherChecks
    public Response createUser(@QueryParam("email") String email, @QueryParam("password") String password, @QueryParam(
            "inviteTicket") String inviteTicket) {
        LoginStatusVO loginStatusVO = userBO.createUserWithTicket(email, password, sr.getRemoteAddr(), inviteTicket);
        if (loginStatusVO != null && loginStatusVO.isLoginOk()) {
            loginStatusVO = tokenBO.login(email, password, sr);
            return Response.ok(loginStatusVO).build();
        }
        return Response.serverError().entity(loginStatusVO).build();
    }
}