package com.soapboxrace.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.jaxb.http.LoginAnnouncementsDefinition;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/LoginAnnouncements" })
public class LoginAnnouncements extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7099857012592329120L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		LoginAnnouncementsDefinition loginAnnouncementsDefinition = new LoginAnnouncementsDefinition();
		JAXBElement<LoginAnnouncementsDefinition> createLoginAnnouncementsDefinition = new ObjectFactory().createLoginAnnouncementsDefinition(loginAnnouncementsDefinition);
		String xmlns = "http://schemas.datacontract.org/2004/07/Victory.DataLayer.Serialization.LoginAnnouncement";
		String marshal = MarshalXML.marshal(createLoginAnnouncementsDefinition, xmlns);
		response.getOutputStream().write(marshal.getBytes());
	}
}
