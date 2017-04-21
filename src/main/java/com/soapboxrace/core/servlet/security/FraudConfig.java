package com.soapboxrace.core.servlet.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/security/fraudConfig" })
public class FraudConfig extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4069389723950410092L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		com.soapboxrace.jaxb.http.FraudConfig fraudConfig = new com.soapboxrace.jaxb.http.FraudConfig();
		fraudConfig.setEnabledBitField(12);
		fraudConfig.setGameFileFreq(1000000);
		fraudConfig.setModuleFreq(360000);
		fraudConfig.setStartUpFreq(1000000);
		fraudConfig.setUserID(getUserId(request));
		JAXBElement<com.soapboxrace.jaxb.http.FraudConfig> createFraudConfig = new ObjectFactory().createFraudConfig(fraudConfig);
		String marshal = MarshalXML.marshal(createFraudConfig);
		response.getOutputStream().write(marshal.getBytes());
	}
}
