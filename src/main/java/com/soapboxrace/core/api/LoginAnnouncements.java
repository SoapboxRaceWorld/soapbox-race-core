package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.ArrayOfLoginAnnouncementDefinition;
import com.soapboxrace.jaxb.http.LoginAnnouncementContext;
import com.soapboxrace.jaxb.http.LoginAnnouncementDefinition;
import com.soapboxrace.jaxb.http.LoginAnnouncementType;
import com.soapboxrace.jaxb.http.LoginAnnouncementsDefinition;

@Path("/LoginAnnouncements")
public class LoginAnnouncements {

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	public LoginAnnouncementsDefinition loginAnnouncements() {
		LoginAnnouncementsDefinition loginAnnouncementsDefinition = new LoginAnnouncementsDefinition();
		ArrayOfLoginAnnouncementDefinition arrayOfLoginAnnouncementDefinition = new ArrayOfLoginAnnouncementDefinition();
		LoginAnnouncementDefinition loginAnnouncementDefinition = new LoginAnnouncementDefinition();
		loginAnnouncementDefinition.setContext(LoginAnnouncementContext.NOT_APPLICABLE);
		loginAnnouncementDefinition.setId(1);
		loginAnnouncementDefinition.setImageChecksum(-1);
		loginAnnouncementDefinition.setImageUrl("GgMribk.png");
		loginAnnouncementDefinition.setType(LoginAnnouncementType.EXTERNAL_LINK);
		loginAnnouncementDefinition.setTarget("https://www.facebook.com/SoapBoxRaceWorld");
		arrayOfLoginAnnouncementDefinition.getLoginAnnouncementDefinition().add(loginAnnouncementDefinition);
		loginAnnouncementsDefinition.setAnnouncements(arrayOfLoginAnnouncementDefinition);
		loginAnnouncementsDefinition.setImagesPath("http://i.imgur.com/");
		return loginAnnouncementsDefinition;
	}
}
