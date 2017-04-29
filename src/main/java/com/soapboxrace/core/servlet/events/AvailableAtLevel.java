package com.soapboxrace.core.servlet.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.EventsPacket;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/events/availableatlevel" })
public class AvailableAtLevel extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2341802500139316038L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		EventsPacket eventsPacket = new EventsPacket();
		JAXBElement<EventsPacket> createEventsPacket = new ObjectFactory().createEventsPacket(eventsPacket);
		String marshal = MarshalXML.marshal(createEventsPacket);
		answer(request, response, marshal);
	}
}
