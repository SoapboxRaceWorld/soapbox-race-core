package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.AchievementsBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.jaxb.http.AchievementsPacket;

@Path("/achievements")
public class Achievements {

	@EJB
	private TokenSessionBO tokenSessionBO;
	
	@EJB
	private AchievementsBO achievementsBO;
	
	@GET
	@Secured
	@Path("/loadall")
	@Produces(MediaType.APPLICATION_XML)
	public AchievementsPacket loadall(@HeaderParam("securityToken") String securityToken) {
		return achievementsBO.loadall(tokenSessionBO.getActivePersonaId(securityToken));
	}
}
