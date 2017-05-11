package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.UserBO;
import com.soapboxrace.jaxb.http.UserInfo;

@Path("User")
public class User {

	@EJB
	private UserBO bo;

	@POST
	@Path("GetPermanentSession")
	@Produces(MediaType.APPLICATION_XML)
	public UserInfo getPermanentSession(@HeaderParam("userId") Long userId) {
		return bo.getUserById(userId);
	}

	@POST
	@Path("SecureLoginPersona")
	@Produces(MediaType.APPLICATION_XML)
	public UserInfo secureLoginPersona(@HeaderParam("userId") Long userId, @QueryParam("personaId") Long personaId) {
		return bo.secureLoginPersona(userId, personaId);
	}

	@POST
	@Path("SecureLogout")
	@Produces(MediaType.APPLICATION_XML)
	public String secureLogout() {
		return "";
	}

}
