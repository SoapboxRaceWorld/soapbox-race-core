package com.soapboxrace.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.jaxb.http.ArrayOfUdpRelayInfo;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.UdpRelayInfo;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/getrebroadcasters" })
public class GetRebroadcasters extends GenericServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8191751979102454234L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ArrayOfUdpRelayInfo arrayOfUdpRelayInfo = new ArrayOfUdpRelayInfo();
		UdpRelayInfo udpRelayInfo = new UdpRelayInfo();
		udpRelayInfo.setHost("127.0.0.1");
		udpRelayInfo.setPort(9999);
		arrayOfUdpRelayInfo.getUdpRelayInfo().add(udpRelayInfo);

		JAXBElement<ArrayOfUdpRelayInfo> createArrayOfUdpRelayInfo = new ObjectFactory().createArrayOfUdpRelayInfo(arrayOfUdpRelayInfo);
		String marshal = MarshalXML.marshal(createArrayOfUdpRelayInfo);
		answer(request, response, marshal);
	}

}
