package com.soapboxrace.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.jaxb.http.ArrayOfNewsArticleTrans;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/NewsArticles" })
public class NewsArticles extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2893153248392179015L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ArrayOfNewsArticleTrans arrayOfNewsArticleTrans = new ArrayOfNewsArticleTrans();
		JAXBElement<ArrayOfNewsArticleTrans> createArrayOfNewsArticleTrans = new ObjectFactory().createArrayOfNewsArticleTrans(arrayOfNewsArticleTrans);
		String marshal = MarshalXML.marshal(createArrayOfNewsArticleTrans);
		answer(request, response, marshal);
	}

}
