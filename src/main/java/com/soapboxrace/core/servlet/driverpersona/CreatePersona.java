package com.soapboxrace.core.servlet.driverpersona;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.ProfileData;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/DriverPersona/CreatePersona" })
public class CreatePersona extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4201305649735179306L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ProfileData profileData = new ProfileData();
		profileData.setName("NOBODY");
		profileData.setCash(5000000);
		profileData.setIconIndex(0);
		profileData.setPersonaId(100);
		profileData.setLevel(60);

		JAXBElement<ProfileData> createProfileData = new ObjectFactory().createProfileData(profileData);
		String marshal = MarshalXML.marshal(createProfileData);
		answer(request, response, marshal);
	}
}
