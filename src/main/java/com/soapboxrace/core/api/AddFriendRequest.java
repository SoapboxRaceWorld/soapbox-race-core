package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/addfriendrequest")
public class AddFriendRequest {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String addFriendRequest() {
		return "";
	}

}
