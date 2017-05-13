package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.InviteTicketBO;

@Path("/CreateTicket")
public class CreateTicket {

	@EJB
	private InviteTicketBO bo;

	@POST
	@Produces(MediaType.TEXT_HTML)
	public String createUser(@FormParam("ticket") String ticket) {
		if (bo.createTicket(ticket)) {
			return "Ticket [" + ticket + "] created";
		}
		return "Ticket [" + ticket + "] creation failed! already exist or server error";
	}

}
