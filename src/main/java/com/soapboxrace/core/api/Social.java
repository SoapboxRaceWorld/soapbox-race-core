package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.AdminBO;
import com.soapboxrace.core.bo.SocialBO;
import com.soapboxrace.core.bo.TokenSessionBO;

@Path("/Social")
public class Social {
	
	@EJB
	private SocialBO bo;

	@EJB
	private TokenSessionBO tokenSessionBo;

	@EJB
	private AdminBO adminBo;
	
	@POST
	@Secured
	@Path("/petition")
	@Produces(MediaType.APPLICATION_XML)
	public String petition(@HeaderParam("securityToken") String securityToken, @QueryParam("personaId") Long personaId, @QueryParam("abuserPersonaId") Long abuserPersonaId, @QueryParam("petitionType") Integer petitionType, @QueryParam("description") String description, @QueryParam("customCarID") Integer customCarID, @QueryParam("chatMinutes") Integer chatMinutes) {
		if(tokenSessionBo.isAdmin(securityToken) && description.startsWith("/")) {
			adminBo.sendCommand(personaId, abuserPersonaId, description);
		} else {
			bo.sendReport(personaId, abuserPersonaId, petitionType, description, customCarID, chatMinutes, 0L);
		}
		return "";
	}
}
