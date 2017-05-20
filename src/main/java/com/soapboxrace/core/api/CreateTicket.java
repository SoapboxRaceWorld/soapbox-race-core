package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.InviteTicketBO;
import com.soapboxrace.core.jpa.InviteTicketEntity;

@Path("/CreateTicket")
public class CreateTicket {

	@EJB
	private InviteTicketBO bo;

	@POST
	@Produces(MediaType.TEXT_HTML)
	public String createUser(@FormParam("discordName") String discordName) {
		InviteTicketEntity createTicket = bo.createTicket(discordName);
		return "Discord Name: [" + createTicket.getDiscordName() + "] with ticket: <strong>" + createTicket.getTicket() + "</strong>";
	}

}
