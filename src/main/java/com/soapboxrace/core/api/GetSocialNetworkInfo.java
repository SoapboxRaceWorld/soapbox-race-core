package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.SocialNetworkInfo;

@Path("/getsocialnetworkinfo")
public class GetSocialNetworkInfo {

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	public SocialNetworkInfo getSocialNetworkInfo() {
		return new SocialNetworkInfo();
	}

}
