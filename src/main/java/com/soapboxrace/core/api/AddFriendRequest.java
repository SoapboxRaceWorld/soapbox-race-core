package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.dao.FriendDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.FriendEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.core.xmpp.XmppChat;
import com.soapboxrace.jaxb.http.ArrayOfBadgePacket;
import com.soapboxrace.jaxb.http.FriendPersona;
import com.soapboxrace.jaxb.http.FriendResult;
import com.soapboxrace.jaxb.http.PersonaBase;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.xmpp.XMPP_FriendPersonaType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypePersonaBase;

import java.util.Objects;

@Path("/addfriendrequest")
public class AddFriendRequest
{
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
	public String addFriendRequest(@HeaderParam("securityToken") String securityToken, @QueryParam("displayName") String displayName)
	{
		long activePersonaId = sessionBO.getActivePersonaId(securityToken);
		PersonaEntity active = personaDAO.findById(activePersonaId);
		PersonaEntity target = personaDAO.findByName(displayName);

		if (target == null || active == null || Objects.equals(active.getPersonaId(), target.getPersonaId()))
			return "";

		if (friendDAO.findBySenderAndRecipient(target.getUser().getId(), activePersonaId) != null)
		{
			openFireSoapBoxCli.send(XmppChat.createSystemMessage("You've already sent a friend request to this driver."), activePersonaId);
			return "";
		}

//		openFireSoapBoxCli.send(XmppChat.createSystemMessage("Sent friend request!"), activePersonaId);
//		openFireSoapBoxCli.send(XmppChat.createSystemMessage(String.format("You received a friend request from %s!", active.getName())), target.getPersonaId());

		FriendResult friendResult = new FriendResult();
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

		XMPP_FriendPersonaType friendPersona = new XMPP_FriendPersonaType();
		friendPersona.setIconIndex(active.getIconIndex());
		friendPersona.setLevel(active.getLevel());
		friendPersona.setName(active.getName());
		friendPersona.setOriginalName(active.getName());
		friendPersona.setPersonaId(activePersonaId);
		friendPersona.setPresence(3);
		friendPersona.setUserId(active.getUser().getId());

		openFireSoapBoxCli.send(MarshalXML.marshal(friendPersona), target.getPersonaId());

		FriendEntity friendEntity = new FriendEntity();
		friendEntity.setPersonaId(active.getPersonaId());
		friendEntity.setUser(target.getUser());
		friendEntity.setStatus(0);

		friendDAO.insert(friendEntity);

		return MarshalXML.marshal(friendResult);
	}
}