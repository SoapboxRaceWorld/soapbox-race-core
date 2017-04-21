package com.soapboxrace.core.servlet.logging;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ClientConfigTrans;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/logging/client" })
public class Client extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7563701890401452365L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ClientConfigTrans clientConfigTrans = new ClientConfigTrans();
		JAXBElement<ClientConfigTrans> createClientConfigTrans = new ObjectFactory().createClientConfigTrans(clientConfigTrans);
		String xmlns = "http://schemas.datacontract.org/2004/07/Victory.DataLayer.Serialization";
		String marshal = MarshalXML.marshal(createClientConfigTrans, xmlns);
		response.getOutputStream().write(marshal.getBytes());
	}
}
