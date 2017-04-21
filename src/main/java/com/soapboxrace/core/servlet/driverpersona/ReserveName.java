package com.soapboxrace.core.servlet.driverpersona;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ArrayOfString;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/DriverPersona/ReserveName" })
public class ReserveName extends GenericServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8374645591799823706L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ArrayOfString arrayOfString = new ArrayOfString();
		// arrayOfString.getString().add("NONE");
		QName qName = new QName("", "ArrayOfstring");
		JAXBElement<ArrayOfString> jaxbElement = new JAXBElement<ArrayOfString>(qName, ArrayOfString.class, null, arrayOfString);
		String marshal = MarshalXML.marshal(jaxbElement);
		System.out.println(marshal);
		response.getOutputStream().write(marshal.getBytes());
	}

}
