package com.soapboxrace.core.servlet.personas.inventory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ArrayOfInventoryItemTrans;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/personas/inventory/objects" })
public class Objects extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6852946068721089851L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ArrayOfInventoryItemTrans arrayOfInventoryItemTrans = new ArrayOfInventoryItemTrans();
		JAXBElement<ArrayOfInventoryItemTrans> createArrayOfInventoryItemTrans = new ObjectFactory().createArrayOfInventoryItemTrans(arrayOfInventoryItemTrans);
		String marshal = MarshalXML.marshal(createArrayOfInventoryItemTrans);
		// http://schemas.datacontract.org/2004/07/Victory.DataLayer.Serialization
		answer(request, response, marshal);
	}
}
