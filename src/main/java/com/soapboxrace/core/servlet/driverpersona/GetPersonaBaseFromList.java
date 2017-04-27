package com.soapboxrace.core.servlet.driverpersona;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ArrayOfBadgePacket;
import com.soapboxrace.jaxb.http.ArrayOfPersonaBase;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.PersonaBase;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/DriverPersona/GetPersonaBaseFromList" })
public class GetPersonaBaseFromList extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6561612397712688834L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ArrayOfPersonaBase arrayOfPersonaBase = new ArrayOfPersonaBase();
		PersonaBase personaBase = new PersonaBase();
		personaBase.setBadges(new ArrayOfBadgePacket());
		personaBase.setIconIndex(0);
		personaBase.setLevel(60);
		personaBase.setMotto("");
		personaBase.setName("NOBODY");
		personaBase.setPresence(1);
		personaBase.setPersonaId(100);
		personaBase.setScore(0);
		personaBase.setUserId(getUserId(request));

		arrayOfPersonaBase.getPersonaBase().add(personaBase);
		JAXBElement<ArrayOfPersonaBase> createArrayOfPersonaBase = new ObjectFactory().createArrayOfPersonaBase(arrayOfPersonaBase);
		String marshal = MarshalXML.marshal(createArrayOfPersonaBase);
		response.getOutputStream().write(marshal.getBytes());
	}
}
