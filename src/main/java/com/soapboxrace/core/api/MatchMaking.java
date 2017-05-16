package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.EventBO;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.jaxb.http.SecurityChallenge;
import com.soapboxrace.jaxb.http.SessionInfo;

@Path("/matchmaking")
public class MatchMaking {

	@EJB
	private EventBO eventBO;

	@PUT
	@Secured
	@Path("/joinqueueracenow")
	@Produces(MediaType.APPLICATION_XML)
	public String joinQueueRaceNow() {
		return "";
	}

	@PUT
	@Secured
	@Path("/joinqueueevent/{eventId}")
	@Produces(MediaType.APPLICATION_XML)
	public String joinQueueEvent(@PathParam("eventId") Long eventId) {
		return "";
	}

	@PUT
	@Secured
	@Path("/leavequeue")
	@Produces(MediaType.APPLICATION_XML)
	public String leaveQueue() {
		return "";
	}

	@PUT
	@Secured
	@Path("/leavelobby")
	@Produces(MediaType.APPLICATION_XML)
	public String leavelobby() {
		return "";
	}

	@GET
	@Secured
	@Path("/launchevent/{eventId}")
	@Produces(MediaType.APPLICATION_XML)
	public SessionInfo launchEvent(@PathParam("eventId") int eventId) {
		SessionInfo sessionInfo = new SessionInfo();
		SecurityChallenge securityChallenge = new SecurityChallenge();
		securityChallenge.setChallengeId("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		securityChallenge.setLeftSize(14);
		securityChallenge.setPattern("FFFFFFFFFFFFFFFF");
		securityChallenge.setRightSize(50);
		sessionInfo.setChallenge(securityChallenge);
		sessionInfo.setEventId(eventId);
		EventSessionEntity createEventSession = eventBO.createEventSession(eventId);
		sessionInfo.setSessionId(createEventSession.getId());
		return sessionInfo;
	}

	@PUT
	@Secured
	@Path("/makeprivatelobby/{eventId}")
	@Produces(MediaType.APPLICATION_XML)
	public String makePrivateLobby(@PathParam("eventId") int eventId) {
		return "";
	}

}
