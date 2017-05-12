package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.UserBO;

@Path("/CreateUser")
public class CreateUser {

	@EJB
	private UserBO bo;

	@POST
	@Produces(MediaType.TEXT_HTML)
	public String createUser(@FormParam("email") String email, @FormParam("passwd") String passwd) {
		bo.createUser(email, passwd);
		return "User [" + email + "] created";
	}

}
