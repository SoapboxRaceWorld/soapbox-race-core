package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Config;
import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.SessionBO;
import com.soapboxrace.jaxb.http.ChatServer;

@Path("/Session")
public class Session {
	
	@EJB
	private SessionBO bo;

	@GET
	@Secured
	@Path("/GetChatInfo")
	@Produces(MediaType.APPLICATION_XML)
	public ChatServer getChatInfo() {
		ChatServer chatServer = new ChatServer();
		chatServer.setIp(Config.getXmppIp());
		chatServer.setPort(Config.getXmppPort());
		chatServer.setPrefix("sbrw");
		chatServer.setRooms(bo.getAllChatRoom());
		return chatServer;
	}
}
