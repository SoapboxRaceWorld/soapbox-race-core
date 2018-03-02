package com.soapboxrace.core.api;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.LoginAnnouncementBO;
import com.soapboxrace.jaxb.http.LoginAnnouncementsDefinition;

@Path("/LoginAnnouncements")
public class LoginAnnouncements {
	
	@EJB
	private LoginAnnouncementBO bo;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	public LoginAnnouncementsDefinition loginAnnouncements() {
		return bo.getLoginAnnouncements();
	}
}
