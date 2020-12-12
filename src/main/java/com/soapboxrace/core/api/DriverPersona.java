/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.*;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.util.JAXBUtility;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.regex.Pattern;

@Path("/DriverPersona")
public class DriverPersona {

    private final Pattern NAME_PATTERN = Pattern.compile("^[A-Z0-9]{3,15}$");

    @EJB
    private DriverPersonaBO driverPersonaBO;

    @EJB
    private UserBO userBo;

    @EJB
    private TokenSessionBO tokenSessionBo;

    @EJB
    private PresenceBO presenceBO;

    @Inject
    private RequestSessionInfo requestSessionInfo;

    @GET
    @Secured
    @Path("/GetExpLevelPointsMap")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfInt getExpLevelPointsMap() {
        return driverPersonaBO.getExpLevelPointsMap();
    }

    @GET
    @Secured
    @Path("/GetPersonaInfo")
    @Produces(MediaType.APPLICATION_XML)
    public ProfileData getPersonaInfo(@QueryParam("personaId") Long personaId) {
        return driverPersonaBO.getPersonaInfo(personaId);
    }

    @POST
    @Secured
    @Path("/ReserveName")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfString reserveName(@QueryParam("name") String name) {
        return driverPersonaBO.reserveName(name);
    }

    @POST
    @Secured
    @Path("/UnreserveName")
    @Produces(MediaType.APPLICATION_XML)
    public String UnreserveName(@QueryParam("name") String name) {
        return "";
    }

    @POST
    @Secured
    @Path("/CreatePersona")
    @Produces(MediaType.APPLICATION_XML)
    public ProfileData createPersona(@HeaderParam("userId") Long userId,
                                     @HeaderParam("securityToken") String securityToken,
                                     @QueryParam("name") String name,
                                     @QueryParam("iconIndex") int iconIndex, @QueryParam("clan") String clan,
                                     @QueryParam("clanIcon") String clanIcon) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new EngineException(EngineExceptionCode.DisplayNameNotAllowed, true);
        }

        ArrayOfString nameReserveResult = driverPersonaBO.reserveName(name);

        if (!nameReserveResult.getString().isEmpty()) {
            throw new EngineException(EngineExceptionCode.DisplayNameDuplicate, true);
        }

        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setName(name);
        personaEntity.setIconIndex(iconIndex);
        ProfileData persona = driverPersonaBO.createPersona(userId, personaEntity);

        if (persona == null) {
            throw new EngineException(EngineExceptionCode.MaximumNumberOfPersonasForUserReached, true);
        }

        long personaId = persona.getPersonaId();
        requestSessionInfo.getTokenSessionEntity().getAllowedPersonaIds().add(personaId);
        userBo.createXmppUser(personaId, securityToken.substring(0, 16));
        return persona;
    }

    @POST
    @Secured
    @Path("/DeletePersona")
    @Produces(MediaType.APPLICATION_XML)
    public String deletePersona(@QueryParam("personaId") Long personaId) {
        tokenSessionBo.verifyPersonaOwnership(requestSessionInfo.getTokenSessionEntity(), personaId);
        driverPersonaBO.deletePersona(personaId);
        requestSessionInfo.getTokenSessionEntity().getAllowedPersonaIds().remove(personaId);
        return "<long>0</long>";
    }

    @POST
    @Path("/GetPersonaBaseFromList")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfPersonaBase getPersonaBaseFromList(InputStream is) {
        PersonaIdArray personaIdArray = JAXBUtility.unMarshal(is, PersonaIdArray.class);
        ArrayOfLong personaIds = personaIdArray.getPersonaIds();
        return driverPersonaBO.getPersonaBaseFromList(personaIds.getLong());
    }

    @POST
    @Secured
    @Path("/UpdatePersonaPresence")
    @Produces(MediaType.APPLICATION_XML)
    public String updatePersonaPresence(@QueryParam("personaId") Long personaId,
                                        @QueryParam("presence") Long presence) {
        tokenSessionBo.verifyPersonaOwnership(requestSessionInfo.getTokenSessionEntity(), personaId);
        presenceBO.updatePresence(personaId, presence);

        return "";
    }

    @GET
    @Secured
    @Path("/GetPersonaPresenceByName")
    @Produces(MediaType.APPLICATION_XML)
    public PersonaPresence getPersonaPresenceByName(@QueryParam("displayName") String displayName) {
        return driverPersonaBO.getPersonaPresenceByName(displayName);
    }

    @POST
    @Secured
    @Path("/UpdateStatusMessage")
    @Produces(MediaType.APPLICATION_XML)
    public PersonaMotto updateStatusMessage(InputStream statusXml) {
        PersonaMotto personaMotto = JAXBUtility.unMarshal(statusXml, PersonaMotto.class);
        tokenSessionBo.verifyPersonaOwnership(requestSessionInfo.getTokenSessionEntity(), personaMotto.getPersonaId());

        driverPersonaBO.updateStatusMessage(personaMotto.getMessage(), personaMotto.getPersonaId());
        return personaMotto;
    }
}