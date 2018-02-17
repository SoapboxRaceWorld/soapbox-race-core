package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.InventoryBO;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.xmpp.XMPP_PowerupActivatedType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypePowerupActivated;

@Path("/powerups")
public class Powerups {

	@EJB
	private TokenSessionBO tokenBO;

	@EJB
	private InventoryBO inventoryBO;

	@EJB
	private OpenFireSoapBoxCli openFireSoapBoxCli;

	@EJB
	private ParameterBO parameterBO;

	@POST
	@Secured
	@Path("/activated/{powerupHash}")
	@Produces(MediaType.APPLICATION_XML)
	public String activated(@HeaderParam("securityToken") String securityToken, @PathParam(value = "powerupHash") Integer powerupHash,
			@QueryParam("targetId") Long targetId, @QueryParam("receivers") String receivers, @QueryParam("eventSessionId") Long eventSessionId) {
		Long activePersonaId = tokenBO.getActivePersonaId(securityToken);

		if (!inventoryBO.hasItem(activePersonaId, powerupHash)) {
			System.out.println(String.format("Persona %d doesn't have powerup %d", activePersonaId, powerupHash));
			return "";
		}

		XMPP_ResponseTypePowerupActivated powerupActivatedResponse = new XMPP_ResponseTypePowerupActivated();
		XMPP_PowerupActivatedType powerupActivated = new XMPP_PowerupActivatedType();
		powerupActivated.setId(Long.valueOf(powerupHash));
		powerupActivated.setTargetPersonaId(targetId);
		powerupActivated.setPersonaId(activePersonaId);
		powerupActivatedResponse.setPowerupActivated(powerupActivated);
		for (String receiver : receivers.split("-")) {
			Long receiverPersonaId = Long.valueOf(receiver);
			if (receiverPersonaId > 10) {
				openFireSoapBoxCli.send(powerupActivatedResponse, receiverPersonaId);
			}
		}

		if (parameterBO.getBoolParam("ENABLE_POWERUP_DECREASE")) {
			inventoryBO.decrementUsage(activePersonaId, powerupHash);
		}

		return "";
	}
}
