package com.soapboxrace.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.RegionInfo;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/getregioninfo" })
public class GetRegionInfo extends GenericServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6572084851184395112L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		RegionInfo regionInfo = new RegionInfo();
		regionInfo.setCountdownProposalInMilliseconds(3000);
		regionInfo.setDirectConnectTimeoutInMilliseconds(1000);
		regionInfo.setDropOutTimeInMilliseconds(15000);
		regionInfo.setEventLoadTimeoutInMilliseconds(30000);
		regionInfo.setHeartbeatIntervalInMilliseconds(1000);
		regionInfo.setUdpRelayBandwidthInBps(9600);
		regionInfo.setUdpRelayTimeoutInMilliseconds(60000);

		JAXBElement<RegionInfo> createRegionInfo = new ObjectFactory().createRegionInfo(regionInfo);
		String marshal = MarshalXML.marshal(createRegionInfo);
		answer(request, response, marshal);
	}
}
