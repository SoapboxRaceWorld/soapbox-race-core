package com.soapboxrace.core.servlet.driverpersona;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
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
		// <PersonaBase>
		// <Badges />
		// <IconIndex>10</IconIndex>
		// <Level>60</Level>
		// <Motto></Motto>
		// <Name>Player1</Name>
		// <PersonaId>22418201</PersonaId>
		// <Presence>1</Presence>
		// <Score>5175</Score>
		// <UserId>11111111</UserId>
		// </PersonaBase>
		
		JAXBElement<ArrayOfPersonaBase> createArrayOfPersonaBase = new ObjectFactory().createArrayOfPersonaBase(arrayOfPersonaBase);
		String marshal = MarshalXML.marshal(createArrayOfPersonaBase);
		response.getOutputStream().write(marshal.getBytes());
	}
}
