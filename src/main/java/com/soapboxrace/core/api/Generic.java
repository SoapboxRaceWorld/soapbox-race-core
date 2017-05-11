package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class Generic {

	@GET
	@Path("{path:.*}")
	@Produces(MediaType.APPLICATION_XML)
	public String createUser(@PathParam("path") String path) {
		System.out.println("empty!!!");
		return "";
	}

}
