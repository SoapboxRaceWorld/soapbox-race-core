package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.jaxb.http.ArrayOfLong;

@Path("/getblockersbyusers")
public class GetBlockersByUsers {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfLong getBlockedUserList() {
		return new ArrayOfLong();
	}
}
