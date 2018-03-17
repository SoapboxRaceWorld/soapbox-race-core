package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class Generic {
	
	@GET
	@Path("{path:.*}")
	@Produces(MediaType.APPLICATION_XML)
	public String genericEmptyGet(@PathParam("path") String path) {
		System.out.println("empty GET!!!");
		return "";
	}

	@POST
	@Path("{path:.*}")
	@Produces(MediaType.APPLICATION_XML)
	public String genericEmptyPost(@PathParam("path") String path) {
		System.out.println("empty POST!!!");
		return "";
	}

	@PUT
	@Path("{path:.*}")
	@Produces(MediaType.APPLICATION_XML)
	public String genericEmptyPut(@PathParam("path") String path) {
		System.out.println("empty PUT!!!");
		return "";
	}

}
