package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.InventoryBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.xmpp.OpenFireSoapBoxCli;
import com.soapboxrace.jaxb.xmpp.XMPP_PowerupActivatedType;
import com.soapboxrace.jaxb.xmpp.XMPP_ResponseTypePowerupActivated;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/powerups")
public class Powerups {

	@EJB
	private TokenSessionBO tokenBO;
	
	@EJB
	private InventoryBO inventoryBO;

	private OpenFireSoapBoxCli openFireSoapBoxCli = OpenFireSoapBoxCli.getInstance();

	@POST
	@Secured
	@Path("/activated/{powerupHash}")
	@Produces(MediaType.APPLICATION_XML)
	public String activated(@HeaderParam("securityToken") String securityToken, @PathParam(value = "powerupHash") Integer powerupHash, @QueryParam("targetId") Long targetId,
			@QueryParam("receivers") String receivers, @QueryParam("eventSessionId") Long eventSessionId) {
		Long activePersonaId = tokenBO.getActivePersonaId(securityToken);
		
		if (!inventoryBO.hasItem(activePersonaId, powerupHash))
		{
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
		
		inventoryBO.decrementUsage(activePersonaId, powerupHash);
		
		return "";
	}
}
