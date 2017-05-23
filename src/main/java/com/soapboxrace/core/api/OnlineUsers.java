package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.xmpp.openfire.OpenFireRestApiCli;

@Path("/OnlineUsers")
public class OnlineUsers {

	private OpenFireRestApiCli openFireRestApiCli = new OpenFireRestApiCli();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public int onlineUsers() {
		return openFireRestApiCli.getTotalOnlineUsers();
	}

}
