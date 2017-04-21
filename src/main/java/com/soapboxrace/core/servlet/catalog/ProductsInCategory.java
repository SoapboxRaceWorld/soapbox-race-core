package com.soapboxrace.core.servlet.catalog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ArrayOfProductTrans;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/catalog/productsInCategory" })
public class ProductsInCategory extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1362958700121690314L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ArrayOfProductTrans arrayOfProductTrans = new ArrayOfProductTrans();
		QName qName = new QName("", "ArrayOfProductTrans");
		JAXBElement<ArrayOfProductTrans> jaxbElement = new JAXBElement<ArrayOfProductTrans>(qName, ArrayOfProductTrans.class, null, arrayOfProductTrans);
		String marshal = MarshalXML.marshal(jaxbElement, "http://schemas.datacontract.org/2004/07/Victory.DataLayer.Serialization");
		response.getOutputStream().write(marshal.getBytes());
	}
}
