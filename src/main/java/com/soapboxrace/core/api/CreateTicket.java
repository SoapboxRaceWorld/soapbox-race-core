package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Config;
import com.soapboxrace.core.bo.InviteTicketBO;
import com.soapboxrace.core.jpa.InviteTicketEntity;

@Path("/CreateTicket")
public class CreateTicket {

	@EJB
	private InviteTicketBO bo;

	@POST
	@Produces(MediaType.TEXT_HTML)
	public String createTicket(@FormParam("discordName") String discordName, @FormParam("ticketAuth") String token) {
		String ticketToken = Config.getTicketToken();
		if (ticketToken == null || ticketToken.equals(token)) {
			InviteTicketEntity createTicket = bo.createTicket(discordName);
			return "Discord Name: [" + createTicket.getDiscordName() + "] with ticket: <strong>" + createTicket.getTicket() + "</strong>";
		} else {
			return "ERROR! invalid admin token!";
		}
	}

}
