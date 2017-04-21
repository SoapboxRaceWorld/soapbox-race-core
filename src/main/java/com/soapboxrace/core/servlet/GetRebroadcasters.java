package com.soapboxrace.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import com.soapboxrace.jaxb.http.ArrayOfUdpRelayInfo;
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
		QName qName = new QName("", "ArrayOfUdpRelayInfo");
		JAXBElement<ArrayOfUdpRelayInfo> jaxbElement = new JAXBElement<ArrayOfUdpRelayInfo>(qName, ArrayOfUdpRelayInfo.class, null, arrayOfUdpRelayInfo);
		String marshal = MarshalXML.marshal(jaxbElement);
		response.getOutputStream().write(marshal.getBytes());
	}

}
