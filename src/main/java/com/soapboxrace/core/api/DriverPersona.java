package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.DriverPersonaBO;
import com.soapboxrace.core.bo.PresenceBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.bo.UserBO;
import com.soapboxrace.core.dao.SocialRelationshipDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.exception.EngineException;
import com.soapboxrace.core.exception.EngineExceptionCode;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.util.UnmarshalXML;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.regex.Pattern;

@Path("/DriverPersona")
public class DriverPersona {
    private final Pattern NAME_PATTERN = Pattern.compile("^[A-Z0-9]{3,15}$");

    @EJB
    private DriverPersonaBO bo;

    @EJB
    private UserBO userBo;

    @EJB
    private TokenSessionBO tokenSessionBo;

    @EJB
    private PresenceBO presenceBO;

    @EJB
    private SocialRelationshipDAO socialRelationshipDAO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @GET
    @Secured
    @Path("/GetExpLevelPointsMap")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfInt getExpLevelPointsMap() {
        return bo.getExpLevelPointsMap();
    }

    @GET
    @Secured
    @Path("/GetPersonaInfo")
    @Produces(MediaType.APPLICATION_XML)
    public ProfileData getPersonaInfo(@QueryParam("personaId") Long personaId) {
        return bo.getPersonaInfo(personaId);
    }

    @POST
    @Secured
    @Path("/ReserveName")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfString reserveName(@QueryParam("name") String name) {
        return bo.reserveName(name);
    }

    @POST
    @Secured
    @Path("/UnreserveName")
    @Produces(MediaType.APPLICATION_XML)
    public Response UnreserveName(@QueryParam("name") String name) {
        return Response.ok().build();
    }

    @POST
    @Secured
    @Path("/CreatePersona")
    @Produces(MediaType.APPLICATION_XML)
    public ProfileData createPersona(@HeaderParam("userId") Long userId,
                                  @HeaderParam("securityToken") String securityToken,
                           @QueryParam("name") String name,
                                  @QueryParam("iconIndex") int iconIndex, @QueryParam("clan") String clan, @QueryParam("clanIcon") String clanIcon) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new EngineException(EngineExceptionCode.DisplayNameNotAllowed);
        }

        ArrayOfString nameReserveResult = bo.reserveName(name);

        if (!nameReserveResult.getString().isEmpty()) {
            throw new EngineException(EngineExceptionCode.DisplayNameDuplicate);
        }

        PersonaEntity personaEntity = new PersonaEntity();
        personaEntity.setName(name);
        personaEntity.setIconIndex(iconIndex);
        ProfileData persona = bo.createPersona(userId, personaEntity);

        if (persona == null) {
            throw new EngineException(EngineExceptionCode.MaximumNumberOfPersonasForUserReached);
        }

        long personaId = persona.getPersonaId();
        userBo.createXmppUser(personaId, securityToken.substring(0, 16));
        return persona;
    }

    @POST
    @Secured
    @Path("/DeletePersona")
    @Produces(MediaType.APPLICATION_XML)
    public String deletePersona(@QueryParam("personaId") Long personaId, @HeaderParam("securityToken") String securityToken) {
        tokenSessionBo.verifyPersona(securityToken, personaId);
        bo.deletePersona(personaId);
        return "<long>0</long>";
    }

    @POST
    @Path("/GetPersonaBaseFromList")
    @Produces(MediaType.APPLICATION_XML)
    public ArrayOfPersonaBase getPersonaBaseFromList(InputStream is) {
        PersonaIdArray personaIdArray = UnmarshalXML.unMarshal(is, PersonaIdArray.class);
        ArrayOfLong personaIds = personaIdArray.getPersonaIds();
        return bo.getPersonaBaseFromList(personaIds.getLong());
    }

    @POST
    @Secured
    @Path("/UpdatePersonaPresence")
    @Produces(MediaType.APPLICATION_XML)
    public Response updatePersonaPresence(@HeaderParam("securityToken") String securityToken,
                                   @QueryParam("presence") Long presence) {
        if (tokenSessionBo.getActivePersonaId(securityToken) == 0L)
            throw new EngineException(EngineExceptionCode.FailedSessionSecurityPolicy);
        PersonaEntity personaEntity = personaDAO.findById(tokenSessionBo.getActivePersonaId(securityToken));
        presenceBO.updatePresence(personaEntity.getPersonaId(), presence);

        return Response.ok().build();
    }

    @GET
    @Secured
    @Path("/GetPersonaPresenceByName")
    @Produces(MediaType.APPLICATION_XML)
    public PersonaPresence getPersonaPresenceByName(@QueryParam("displayName") String displayName) {
        PersonaPresence personaPresenceByName = bo.getPersonaPresenceByName(displayName);
        if (personaPresenceByName.getPersonaId() == 0) {
            throw new EngineException(EngineExceptionCode.PersonaNotFound);
        }
        return personaPresenceByName;
    }

    @POST
    @Secured
    @Path("/UpdateStatusMessage")
    @Produces(MediaType.APPLICATION_XML)
    public PersonaMotto updateStatusMessage(InputStream statusXml, @HeaderParam("securityToken") String securityToken, @Context Request request) {
        PersonaMotto personaMotto = UnmarshalXML.unMarshal(statusXml, PersonaMotto.class);
        tokenSessionBo.verifyPersona(securityToken, personaMotto.getPersonaId());

        bo.updateStatusMessage(personaMotto.getMessage(), personaMotto.getPersonaId());
        return personaMotto;
    }
}