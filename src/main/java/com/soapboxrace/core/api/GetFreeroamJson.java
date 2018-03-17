package com.soapboxrace.core.api;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.bo.ParameterBO;

@Path("/GetFreeroamJson")
public class GetFreeroamJson {
	@EJB
	private ParameterBO parameterBO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getServerInformation() {
		try {
			String strParam = parameterBO.getStrParam("FREEROAM_JSON_PATH");
			return new String(Files.readAllBytes(Paths.get(strParam)));
		} catch (Exception e) {
			System.err.println("freeroam json file not found");
		}
		return "";
	}
}
