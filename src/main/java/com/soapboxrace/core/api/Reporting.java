package com.soapboxrace.core.api;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.HardwareInfo;
import com.soapboxrace.jaxb.util.UnmarshalXML;

@Path("/Reporting")
public class Reporting {

	@POST
	@Secured
	@Path("/SendHardwareInfo")
	@Produces(MediaType.APPLICATION_XML)
	public String sendHardwareInfo(InputStream is) {
		HardwareInfo unMarshal = (HardwareInfo) UnmarshalXML.unMarshal(is, HardwareInfo.class);
		System.out.println(unMarshal);
		return "";
	}

	@POST
	@Secured
	@Path("/SendUserSettings")
	@Produces(MediaType.APPLICATION_XML)
	public String sendUserSettings() {
		return "";
	}

	@GET
	@Secured
	@Path("/SendMultiplayerConnect")
	@Produces(MediaType.APPLICATION_XML)
	public String sendMultiplayerConnect() {
		return "";
	}

	@GET
	@Secured
	@Path("/SendClientPingTime")
	@Produces(MediaType.APPLICATION_XML)
	public String sendClientPingTime() {
		return "";
	}

	@GET
	@Secured
	@Path("/LoginAnnouncementClicked")
	@Produces(MediaType.APPLICATION_XML)
	public String loginAnnouncementClicked() {
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
