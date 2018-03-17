package com.soapboxrace.core.api;

import java.net.URI;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.jaxb.http.ArrayOfUdpRelayInfo;
import com.soapboxrace.jaxb.http.UdpRelayInfo;

@Path("/getrebroadcasters")
public class GetRebroadcasters {

	@Context
	UriInfo uri;

	@EJB
	private ParameterBO parameterBO;

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfUdpRelayInfo getRebroadcasters() {
		ArrayOfUdpRelayInfo arrayOfUdpRelayInfo = new ArrayOfUdpRelayInfo();
		UdpRelayInfo udpRelayInfo = new UdpRelayInfo();
		String freeroamIp = parameterBO.getStrParam("UDP_FREEROAM_IP");
		if ("127.0.0.1".equals(freeroamIp)) {
			URI myUri = uri.getBaseUri();
			freeroamIp = myUri.getHost();
		}
		udpRelayInfo.setHost(freeroamIp);
		udpRelayInfo.setPort(parameterBO.getIntParam("UDP_FREEROAM_PORT"));
		arrayOfUdpRelayInfo.getUdpRelayInfo().add(udpRelayInfo);
		return arrayOfUdpRelayInfo;
	}
}
