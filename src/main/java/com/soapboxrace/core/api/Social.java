package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.SocialBO;

@Path("/Social")
public class Social {
	
	@EJB
	private SocialBO bo;

	@POST
	@Secured
	@Path("/petition")
	@Produces(MediaType.APPLICATION_XML)
	public String sendHardwareInfo(@QueryParam("personaId") Long personaId, @QueryParam("abuserPersonaId") Long abuserPersonaId, @QueryParam("petitionType") Integer petitionType, @QueryParam("description") String description, @QueryParam("customCarID") Integer customCarID, @QueryParam("chatMinutes") Integer chatMinutes) {
		bo.sendReport(personaId, abuserPersonaId, petitionType, description, customCarID, chatMinutes, 0L);
		return "";
	}
}
