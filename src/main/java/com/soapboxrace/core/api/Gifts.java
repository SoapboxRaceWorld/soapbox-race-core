package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.ArrayOfLevelGiftDefinition;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Gifts")
public class Gifts {
	@POST
	@Secured
	@Path("/GetAndTriggerAvailableLevelGifts")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfLevelGiftDefinition getAndTriggerAvailableLevelGifts() {
		return new ArrayOfLevelGiftDefinition();
	}
}
