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
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	public String addFriendRequest() {
		return "";
	}
}
