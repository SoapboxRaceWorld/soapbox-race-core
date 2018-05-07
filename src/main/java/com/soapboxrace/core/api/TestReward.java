package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.PersonaBO;
import com.soapboxrace.core.bo.RewardPursuitBO;
import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.EventDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.PursuitArbitrationPacket;

@Path("/testReward")
public class TestReward {

	@EJB
	private RewardPursuitBO pursuitBO;

	@EJB
	private PersonaBO personaBO;

	@EJB
	private EventDAO eventDAO;

	@POST
	@Path("/pursuit/{personaId}/{eventId}")
	@Produces(MediaType.APPLICATION_JSON)
	public RewardVO pursuit(PursuitArbitrationPacket pursuitArbitrationPacket, @PathParam(value = "personaId") Long personaId,
			@PathParam(value = "eventId") Integer eventId) {
		PersonaEntity personaEntity = personaBO.getPersonaById(personaId);
		RewardVO rewardVO = pursuitBO.getRewardVO(personaEntity);
		EventEntity eventEntity = eventDAO.findById(eventId);
		pursuitBO.setPursuitRewards(personaEntity, eventEntity, pursuitArbitrationPacket, rewardVO);
		return rewardVO;
	}

	@GET
	@Path("/pursuit")
	@Produces(MediaType.APPLICATION_JSON)
	public PursuitArbitrationPacket vai() {
		return new PursuitArbitrationPacket();
	}
}
