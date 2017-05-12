package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.UUIDGen;
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
		// http headers:
		// userId
		// securityToken
		UserInfo userInfo = bo.getUserById(userId);
		userInfo.getUser().setSecurityToken(UUIDGen.getRandomUUID());
		bo.createXmppUser(userInfo);
		return userInfo;
	}

	@POST
	@Path("SecureLoginPersona")
	@Produces(MediaType.APPLICATION_XML)
	public String secureLoginPersona(@HeaderParam("securityToken") String securityToken, @HeaderParam("userId") Long userId, @QueryParam("personaId") Long personaId) {
		bo.secureLoginPersona(userId, personaId);
		return "";
	}

	@POST
	@Path("SecureLogoutPersona")
	@Produces(MediaType.APPLICATION_XML)
	public String secureLogoutPersona(@HeaderParam("userId") Long userId, @QueryParam("personaId") Long personaId) {
		return "";
	}

	@POST
	@Path("SecureLogout")
	@Produces(MediaType.APPLICATION_XML)
	public String secureLogout() {
		return "";
	}

}
