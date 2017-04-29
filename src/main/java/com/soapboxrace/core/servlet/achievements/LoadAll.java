package com.soapboxrace.core.servlet.achievements;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.AchievementsPacket;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/achievements/loadall" })
public class LoadAll extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4113905366008602047L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		AchievementsPacket achievementsPacket = new AchievementsPacket();
		JAXBElement<AchievementsPacket> createAchievementsPacket = new ObjectFactory().createAchievementsPacket(achievementsPacket);
		String marshal = MarshalXML.marshal(createAchievementsPacket);
		answer(request, response, marshal);
	}

}
