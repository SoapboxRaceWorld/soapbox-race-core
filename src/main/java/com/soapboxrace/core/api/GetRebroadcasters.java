package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Config;
import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.jaxb.http.ArrayOfUdpRelayInfo;
import com.soapboxrace.jaxb.http.UdpRelayInfo;

@Path("/getrebroadcasters")
public class GetRebroadcasters {

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfUdpRelayInfo getRebroadcasters() {
		ArrayOfUdpRelayInfo arrayOfUdpRelayInfo = new ArrayOfUdpRelayInfo();
		UdpRelayInfo udpRelayInfo = new UdpRelayInfo();
		udpRelayInfo.setHost(Config.getUdpFreeroamIp());
		udpRelayInfo.setPort(Config.getUdpFreeroamPort());
		arrayOfUdpRelayInfo.getUdpRelayInfo().add(udpRelayInfo);
		return arrayOfUdpRelayInfo;
	}
}
