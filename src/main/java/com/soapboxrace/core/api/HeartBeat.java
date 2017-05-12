package com.soapboxrace.core.api;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;

@Path("/heartbeat")
public class HeartBeat {

	@POST
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	public com.soapboxrace.jaxb.http.HeartBeat getPermanentSession(@HeaderParam("userId") Long userId) {
		com.soapboxrace.jaxb.http.HeartBeat heartBeat = new com.soapboxrace.jaxb.http.HeartBeat();
		heartBeat.setEnabledBitField(0);
		heartBeat.setMetagameFlags(2);
		return heartBeat;
	}
}
