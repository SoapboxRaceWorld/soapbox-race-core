package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
		udpRelayInfo.setHost("127.0.0.1");
		udpRelayInfo.setPort(9999);
		arrayOfUdpRelayInfo.getUdpRelayInfo().add(udpRelayInfo);
		return arrayOfUdpRelayInfo;
	}
}
