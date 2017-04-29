package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.jaxb.annotation.XsiSchemaLocation;
import com.soapboxrace.jaxb.http.LoginAnnouncementsDefinition;

@Path("/LoginAnnouncements")
public class LoginAnnouncements {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@XsiSchemaLocation(schemaLocation = "http://schemas.datacontract.org/2004/07/Victory.DataLayer.Serialization.LoginAnnouncement")
	public LoginAnnouncementsDefinition loginAnnouncements() {
		return new LoginAnnouncementsDefinition();
	}
}
