/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.LauncherChecks;
import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.*;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.SocialRelationshipDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.BanEntity;
import com.soapboxrace.core.jpa.UserEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.UserInfo;
import com.soapboxrace.jaxb.login.LoginStatusVO;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Objects;

@Path("User")
public class User {

    @Context
    UriInfo uri;

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
    private SocialRelationshipDAO socialRelationshipDAO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @POST
    @Secured
    @Path("GetPermanentSession")
    @Produces(MediaType.APPLICATION_XML)
    public Response getPermanentSession(@HeaderParam("securityToken") String securityToken,
                                        @HeaderParam("userId") Long userId) {
        UserEntity userEntity = tokenBO.getUser(securityToken);
        BanEntity ban = authenticationBO.checkUserBan(userEntity);

        if (ban != null) {
            // Ideally this will never happen. Then again, plenty of weird stuff has happened.
            tokenBO.deleteByUserId(userId);

            throw new EngineException(EngineExceptionCode.BannedEntitlements);
        }

        int numberOfUsersOnlineNow = onlineUsersBO.getNumberOfUsersOnlineNow();
        int maxOnlinePlayers = parameterBO.getIntParam("MAX_ONLINE_PLAYERS");

        if (maxOnlinePlayers != -1) {
            if (numberOfUsersOnlineNow >= maxOnlinePlayers) {
                throw new EngineException(EngineExceptionCode.MaximumUsersLoggedInHardCapReached);
            }
        }

        tokenBO.deleteByUserId(userId);
        URI myUri = uri.getBaseUri();
        String randomUUID = tokenBO.createToken(userId, myUri.getHost());
        UserInfo userInfo = userBO.getUserById(userId);
        userInfo.getUser().setSecurityToken(randomUUID);
        userBO.createXmppUser(userInfo);
        return Response.ok(userInfo).build();
    }

    @POST
    @Secured
    @Path("SecureLoginPersona")
    @Produces(MediaType.APPLICATION_XML)
    public String secureLoginPersona(@HeaderParam("securityToken") String securityToken,
                                     @HeaderParam("userId") Long userId,
                                     @QueryParam("personaId") Long personaId) {
        tokenBO.setActivePersonaId(securityToken, personaId, false);
        userBO.secureLoginPersona(userId, personaId);
        return "";
    }

    @POST
    @Secured
    @Path("SecureLogoutPersona")
    @Produces(MediaType.APPLICATION_XML)
    public String secureLogoutPersona(@HeaderParam("securityToken") String securityToken,
                                      @HeaderParam("userId") Long userId,
                                      @QueryParam("personaId") Long personaId) {
        long activePersonaId = tokenBO.getActivePersonaId(securityToken);
        tokenBO.setActivePersonaId(securityToken, 0L, true);
        presenceBO.removePresence(activePersonaId);

        return "";
    }

    @POST
    @Secured
    @Path("SecureLogout")
    @Produces(MediaType.APPLICATION_XML)
    public String secureLogout(@HeaderParam("securityToken") String securityToken) {
        Long activePersonaId = tokenBO.getActivePersonaId(securityToken);

        if (Objects.isNull(activePersonaId) || activePersonaId == 0L) {
            throw new EngineException(EngineExceptionCode.FailedUpdateSession);
        }

        tokenBO.setActivePersonaId(securityToken, 0L, true);
        presenceBO.removePresence(activePersonaId);

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