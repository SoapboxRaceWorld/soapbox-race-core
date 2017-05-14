package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.ExitPath;
import com.soapboxrace.jaxb.http.LuckyDrawInfo;
import com.soapboxrace.jaxb.http.PursuitEventResult;
import com.soapboxrace.jaxb.http.Reward;

@Path("/event")
public class Event {

	@EJB
	private TokenSessionBO tokenBO;

	@POST
	@Secured
	@Path("/abort")
	@Produces(MediaType.APPLICATION_XML)
	public String abort(@QueryParam("eventSessionId") Long eventSessionId) {
		return "";
	}

	@PUT
	@Secured
	@Path("/launched")
	@Produces(MediaType.APPLICATION_XML)
	public String launched(@QueryParam("eventSessionId") Long eventSessionId) {
		return "";
	}

	@POST
	@Secured
	@Path("/arbitration")
	@Produces(MediaType.APPLICATION_XML)
	public Object arbitration(@HeaderParam("securityToken") String securityToken, @QueryParam("eventSessionId") Long eventSessionId) {
		if (eventSessionId.equals(1000000001L)) {
			Long activePersonaId = tokenBO.getActivePersonaId(securityToken);
			return getPursitEnd(eventSessionId, activePersonaId);
		}
		return "";
	}

	@POST
	@Secured
	@Path("/bust")
	@Produces(MediaType.APPLICATION_XML)
	public PursuitEventResult bust(@HeaderParam("securityToken") String securityToken, @QueryParam("eventSessionId") Long eventSessionId) {
		PursuitEventResult pursuitEventResult = new PursuitEventResult();
		Long activePersonaId = tokenBO.getActivePersonaId(securityToken);
		pursuitEventResult = getPursitEnd(eventSessionId, activePersonaId);
		return pursuitEventResult;
	}

	private PursuitEventResult getPursitEnd(Long eventSessionId, Long activePersonaId) {
		PursuitEventResult pursuitEventResult = new PursuitEventResult();
		Accolades accolades = new Accolades();
		Reward finalReward = new Reward();
		finalReward.setRep(0);
		finalReward.setTokens(0);
		accolades.setFinalRewards(finalReward);
		accolades.setHasLeveledUp(false);
		accolades.setLuckyDrawInfo(new LuckyDrawInfo());
		pursuitEventResult.setDurability(100);
		pursuitEventResult.setEventId(387); // get from session
		pursuitEventResult.setEventSessionId(eventSessionId);
		pursuitEventResult.setExitPath(ExitPath.EXIT_TO_FREEROAM);
		pursuitEventResult.setInviteLifetimeInMilliseconds(0);
		pursuitEventResult.setLobbyInviteId(0);
		pursuitEventResult.setPersonaId(activePersonaId);
		pursuitEventResult.setHeat(1);
		Reward originalRewards = new Reward();
		originalRewards.setRep(0);
		originalRewards.setTokens(0);
		accolades.setOriginalRewards(originalRewards);
		pursuitEventResult.setAccolades(accolades);
		return pursuitEventResult;
	}
}
