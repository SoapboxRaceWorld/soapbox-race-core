package com.soapboxrace.core.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Reporting")
public class Reporting {

	@POST
	@Path("/SendHardwareInfo")
	@Produces(MediaType.APPLICATION_XML)
	public String sendHardwareInfo() {
		return "";
	}

	@POST
	@Path("/SendUserSettings")
	@Produces(MediaType.APPLICATION_XML)
	public String sendUserSettings() {
		return "";
	}

}
