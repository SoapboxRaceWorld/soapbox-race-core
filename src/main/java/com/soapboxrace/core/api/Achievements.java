package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.jaxb.http.AchievementsPacket;

@Path("/achievements")
public class Achievements {

	@GET
	@Path("/loadall")
	@Produces(MediaType.APPLICATION_XML)
	public AchievementsPacket loadall() {
		return new AchievementsPacket();
	}
}
