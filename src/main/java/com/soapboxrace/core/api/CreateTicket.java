/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.bo.InviteTicketBO;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.jpa.InviteTicketEntity;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/CreateTicket")
public class CreateTicket {

    @EJB
    private InviteTicketBO bo;

    @EJB
    private ParameterBO parameterBO;

    @POST
    @Produces(MediaType.TEXT_HTML)
    public String createTicket(@FormParam("discordName") String discordName, @FormParam("ticketAuth") String token) {
        String ticketToken = parameterBO.getStrParam("TICKET_TOKEN");
        if (ticketToken == null || ticketToken.equals(token)) {
            InviteTicketEntity createTicket = bo.createTicket(discordName);
            return "Discord Name: [" + createTicket.getDiscordName() + "] with ticket: <strong>" + createTicket.getTicket() + "</strong>";
        } else {
            return "ERROR! invalid admin token!";
        }
    }

}
