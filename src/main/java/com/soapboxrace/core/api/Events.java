package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.EventsPacket;
import com.soapboxrace.jaxb.http.TreasureHuntEventSession;

@Path("/events")
public class Events {

	@GET
	@Secured
	@Path("/availableatlevel")
	@Produces(MediaType.APPLICATION_XML)
	public EventsPacket availableatlevel() {
		return new EventsPacket();
	}

	@GET
	@Secured
	@Path("/gettreasurehunteventsession")
	@Produces(MediaType.APPLICATION_XML)
	public TreasureHuntEventSession getTreasureHuntEventSession() {
		return new TreasureHuntEventSession();
	}
}
