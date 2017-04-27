package com.soapboxrace.core.servlet.driverpersona;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ArrayOfBadgePacket;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.ProfileData;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/DriverPersona/GetPersonaInfo" })
public class GetPersonaInfo extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8518601257126970309L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ProfileData profileData = new ProfileData();
		profileData.setBadges(new ArrayOfBadgePacket());
		profileData.setCash(5000000);
		profileData.setIconIndex(0);
		profileData.setLevel(60);
		profileData.setMotto("");
		profileData.setName("NOBODY");
		profileData.setPercentToLevel(0);
		profileData.setPersonaId(100);
		profileData.setRating(0);
		profileData.setRep(0);
		profileData.setRepAtCurrentLevel(0);
		profileData.setScore(0);

		JAXBElement<ProfileData> createProfileData = new ObjectFactory().createProfileData(profileData);
		String marshal = MarshalXML.marshal(createProfileData);
		response.getOutputStream().write(marshal.getBytes());
	}
}
