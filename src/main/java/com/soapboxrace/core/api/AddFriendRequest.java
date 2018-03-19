package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppChat;
import com.soapboxrace.jaxb.http.ArrayOfBadgePacket;
import com.soapboxrace.jaxb.http.PersonaBase;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_FriendPersonaType;

@Path("/addfriendrequest")
public class AddFriendRequest {
	@EJB
	private TokenSessionBO sessionBO;
	
	@EJB
	private PersonaDAO personaDAO;

	@EJB
	private OpenFireSoapBoxCli openFireSoapBoxCli;
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	public String addFriendRequest(@HeaderParam("securityToken") String securityToken, @QueryParam("displayName") String displayName) {
		long activePersonaId = sessionBO.getActivePersonaId(securityToken);
		PersonaEntity active = personaDAO.findById(activePersonaId);
		PersonaEntity target = personaDAO.findByName(displayName);
		
		if (target == null) 
			return "";

		openFireSoapBoxCli.send(XmppChat.createSystemMessage("Sent friend request!"), activePersonaId);
		openFireSoapBoxCli.send(XmppChat.createSystemMessage(String.format("You received a friend request from %s!", active.getName())), target.getPersonaId());
		
		{
			XMPP_FriendPersonaType friendPersona = new XMPP_FriendPersonaType();
			friendPersona.setIconIndex(active.getIconIndex());
			friendPersona.setLevel(active.getLevel());
			friendPersona.setName(active.getName());
			friendPersona.setOriginalName(active.getName());
			friendPersona.setPersonaId(activePersonaId);
			friendPersona.setPresence(3);
			friendPersona.setUserId(active.getUser().getId());
			
			openFireSoapBoxCli.send(MarshalXML.marshal(friendPersona), target.getPersonaId());
		}
		
		return "";
	}
}
