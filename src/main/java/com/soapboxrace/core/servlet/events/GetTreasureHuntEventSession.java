package com.soapboxrace.core.servlet.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.TreasureHuntEventSession;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/events/gettreasurehunteventsession" })
public class GetTreasureHuntEventSession extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4591089733724919789L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		TreasureHuntEventSession treasureHuntEventSession = new TreasureHuntEventSession();
		JAXBElement<TreasureHuntEventSession> createTreasureHuntEventSession = new ObjectFactory().createTreasureHuntEventSession(treasureHuntEventSession);
		String marshal = MarshalXML.marshal(createTreasureHuntEventSession);
		response.getOutputStream().write(marshal.getBytes());
	}

}
