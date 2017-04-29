package com.soapboxrace.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.PersonaFriendsList;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/getfriendlistfromuserid" })
public class GetFriendListFromUserId extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2125697541381049590L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		PersonaFriendsList personaFriendsList = new PersonaFriendsList();
		JAXBElement<PersonaFriendsList> createPersonaFriendsList = new ObjectFactory().createPersonaFriendsList(personaFriendsList);
		String xmlns = "http://schemas.datacontract.org/2004/07/Victory.TransferObjects.DriverPersona";
		String marshal = MarshalXML.marshal(createPersonaFriendsList, xmlns);
		answer(request, response, marshal);
	}

}
