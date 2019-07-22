package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.FriendDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.exception.EngineException;
import com.soapboxrace.core.exception.EngineExceptionCode;
import com.soapboxrace.core.jpa.FriendEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.FriendPersona;
import com.soapboxrace.jaxb.http.FriendResult;
import com.soapboxrace.jaxb.xmpp.XMPP_FriendPersonaType;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/addfriendrequest")
public class AddFriendRequest {
    @EJB
    private TokenSessionBO sessionBO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @EJB
    private FriendDAO friendDAO;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public FriendResult addFriendRequest(@HeaderParam("securityToken") String securityToken,
                              @QueryParam("displayName") String displayName) {
        long activePersonaId = sessionBO.getActivePersonaId(securityToken);
        PersonaEntity active = personaDAO.findById(activePersonaId);
        PersonaEntity target = personaDAO.findByName(displayName);
        FriendResult friendResult = new FriendResult();

        if (active == null) {
            throw new EngineException(EngineExceptionCode.FailedSessionSecurityPolicy);
        }

        if (target == null) {
            friendResult.setResult(3);
            return friendResult;
        }

        if (friendDAO.findBySenderAndRecipient(active.getUser().getId(), target.getUser().getId()) != null) {
            friendResult.setResult(2);
            return friendResult;
        }

        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setFromPersonaId(active.getPersonaId());
        friendEntity.setFromUser(active.getUser());
        friendEntity.setUser(target.getUser());
        friendEntity.setStatus(0);

        friendDAO.insert(friendEntity);

        // resultFriendPersona is returned to the client making the request
        FriendPersona resultFriendPersona = new FriendPersona();
        resultFriendPersona.setIconIndex(target.getIconIndex());
        resultFriendPersona.setLevel(target.getLevel());
        resultFriendPersona.setName(target.getName());
        resultFriendPersona.setOriginalName(null);
        resultFriendPersona.setPersonaId(target.getPersonaId());
        resultFriendPersona.setPresence(0);
        resultFriendPersona.setSocialNetwork(0);
        resultFriendPersona.setUserId(target.getUser().getId());

        friendResult.setResult(0);
        friendResult.setFriendPersona(resultFriendPersona);

        // friendPersona is sent to the recipient of the request
        XMPP_FriendPersonaType friendPersona = new XMPP_FriendPersonaType();
        friendPersona.setIconIndex(active.getIconIndex());
        friendPersona.setLevel(active.getLevel());
        friendPersona.setName(active.getName());
        friendPersona.setOriginalName(active.getName());
        friendPersona.setPersonaId(activePersonaId);
        friendPersona.setPresence(3);
        friendPersona.setUserId(active.getUser().getId());

        openFireSoapBoxCli.send(friendPersona, target.getPersonaId());

        return friendResult;
    }
}