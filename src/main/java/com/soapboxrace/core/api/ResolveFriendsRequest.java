package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.http.ArrayOfBadgePacket;
import com.soapboxrace.jaxb.http.FriendPersona;
import com.soapboxrace.jaxb.http.FriendResult;
import com.soapboxrace.jaxb.http.PersonaBase;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_FriendPersonaType;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/resolvefriendsrequest")
public class ResolveFriendsRequest {
    @EJB
    private TokenSessionBO sessionBO;

    @EJB
    private PersonaDAO personaDAO;

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_XML)
    public String resolveFriendsRequest(@HeaderParam("securityToken") String securityToken, @QueryParam("friendPersonaId") Long friendPersonaId, @QueryParam("resolution") int resolution) {
        long activePersonaId = sessionBO.getActivePersonaId(securityToken);
        
        // resolution 1 = accept, resolution 0 = deny
        System.out.println("activeId=" + activePersonaId + ", friendId=" + friendPersonaId + ", resolution=" + resolution);
        
        PersonaEntity recipientPersona = personaDAO.findById(activePersonaId);
        PersonaEntity senderPersona = personaDAO.findById(friendPersonaId);
        
        if (recipientPersona == null || senderPersona == null) {
            System.err.println("Invalid resolvefriendsrequest!");
            return "";
        }
        
        System.out.println("recipient=" + recipientPersona.getName() + ", sender=" + senderPersona.getName());
        
        FriendResult friendResult = new FriendResult();
        friendResult.setResult(1);
        
        {
            FriendPersona resultFriendPersona = new FriendPersona();
            resultFriendPersona.setIconIndex(senderPersona.getIconIndex());
            resultFriendPersona.setLevel(senderPersona.getLevel());
            resultFriendPersona.setName(senderPersona.getName());
            resultFriendPersona.setOriginalName(senderPersona.getName());
            resultFriendPersona.setPersonaId(senderPersona.getPersonaId());
            resultFriendPersona.setPresence(1);
            resultFriendPersona.setSocialNetwork(1);
            resultFriendPersona.setUserId(senderPersona.getUser().getId());
            
            friendResult.setFriendPersona(resultFriendPersona);
            
            PersonaBase personaBase = new PersonaBase();
            personaBase.setIconIndex(senderPersona.getIconIndex());
            personaBase.setLevel(senderPersona.getLevel());
            personaBase.setName(senderPersona.getName());
            personaBase.setMotto(senderPersona.getMotto());
            personaBase.setPersonaId(senderPersona.getPersonaId());
            personaBase.setPresence(1);
            personaBase.setScore(senderPersona.getScore());
            personaBase.setUserId(senderPersona.getUser().getId());

        }
        
        return MarshalXML.marshal(friendResult);
    }
}
