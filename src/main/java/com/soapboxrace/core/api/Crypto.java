package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;

@Path("/crypto")
public class Crypto {

	@GET
	@Secured
	@Path("/relaycryptoticket/{personaId}")
	@Produces(MediaType.APPLICATION_XML)
	public String relayCryptoTicket(@PathParam("personaId") Long personaId) {
		return "";
	}

	@GET
	@Path("{path:.*}")
	@Produces(MediaType.APPLICATION_XML)
	public String genericEmptyGet(@PathParam("path") String path) {
		System.out.println("empty GET!!!");
		return "";
	}
}
