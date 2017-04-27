package com.soapboxrace.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.jaxb.http.ArrayOfLong;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/getblockersbyusers" })
public class GetBlockersByUsers extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1871531283362206712L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ArrayOfLong arrayOfLong = new ArrayOfLong();
		JAXBElement<ArrayOfLong> createArrayOflong = new ObjectFactory().createArrayOflong(arrayOfLong);
		String marshal = MarshalXML.marshal(createArrayOflong);
		response.getOutputStream().write(marshal.getBytes());
	}
}
