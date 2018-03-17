package com.soapboxrace.core.api;

import java.net.URI;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.bo.SessionBO;
import com.soapboxrace.jaxb.http.ChatServer;

@Path("/Session")
public class Session {

	@Context
	UriInfo uri;

	@EJB
	private SessionBO bo;

	@EJB
	private ParameterBO parameterBO;

	@GET
	@Secured
	@Path("/GetChatInfo")
	@Produces(MediaType.APPLICATION_XML)
	public ChatServer getChatInfo() {
		ChatServer chatServer = new ChatServer();
		String xmppIp = parameterBO.getStrParam("XMPP_IP");
		if ("127.0.0.1".equals(parameterBO.getStrParam("XMPP_IP"))) {
			URI myUri = uri.getBaseUri();
			xmppIp = myUri.getHost();
		}
		chatServer.setIp(xmppIp);
		chatServer.setPort(parameterBO.getIntParam("XMPP_PORT"));
		chatServer.setPrefix("sbrw");
		chatServer.setRooms(bo.getAllChatRoom());
		return chatServer;
	}
}
