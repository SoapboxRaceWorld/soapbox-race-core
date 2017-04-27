package com.soapboxrace.core.servlet.gifts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ArrayOfLevelGiftDefinition;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/Gifts/GetAndTriggerAvailableLevelGifts" })
public class GetAndTriggerAvailableLevelGifts extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2067113998670505304L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ArrayOfLevelGiftDefinition arrayOfLevelGiftDefinition = new ArrayOfLevelGiftDefinition();
		JAXBElement<ArrayOfLevelGiftDefinition> createArrayOfLevelGiftDefinition = new ObjectFactory().createArrayOfLevelGiftDefinition(arrayOfLevelGiftDefinition);
		String marshal = MarshalXML.marshal(createArrayOfLevelGiftDefinition);
		response.getOutputStream().write(marshal.getBytes());
	}

}
