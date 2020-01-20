/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.InventoryBO;
import com.soapboxrace.core.bo.ParameterBO;
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

    @EJB
    private OpenFireSoapBoxCli openFireSoapBoxCli;

    @EJB
    private ParameterBO parameterBO;

    @POST
    @Secured
    @Path("/activated/{powerupHash}")
    @Produces(MediaType.APPLICATION_XML)
    public String activated(@HeaderParam("securityToken") String securityToken,
                            @PathParam(value = "powerupHash") Integer powerupHash,
                            @QueryParam("targetId") Long targetId, @QueryParam("receivers") String receivers,
                            @QueryParam("eventSessionId") Long eventSessionId) {
        Long activePersonaId = tokenBO.getActivePersonaId(securityToken);
        if (parameterBO.getBoolParam("ENABLE_POWERUP_DECREASE")) {
            inventoryBO.decreaseItemCount(inventoryBO.getInventory(activePersonaId), powerupHash);
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


        return "";
    }
}
