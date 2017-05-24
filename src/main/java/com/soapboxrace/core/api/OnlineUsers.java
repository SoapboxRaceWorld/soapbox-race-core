package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.OnlineUsersBO;

@Path("/OnlineUsers")
public class OnlineUsers {

	@EJB
	private OnlineUsersBO onlineUsersBO;

	@GET
	@Path("/getOnline")
	@Produces(MediaType.APPLICATION_JSON)
	public int onlineUsers() {
		return onlineUsersBO.getNumberOfUsersOnlineNow();
	}

}
