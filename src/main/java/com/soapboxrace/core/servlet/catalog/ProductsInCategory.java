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
import com.soapboxrace.jaxb.http.ProductTrans;
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
		// categoryName=Starting_Cars&clientProductType=PRESETCAR
		String categoryName = request.getParameter("categoryName");
		String clientProductType = request.getParameter("clientProductType");
		if ("Starting_Cars".equals(categoryName) && "PRESETCAR".equals(clientProductType)) {
			ProductTrans productTrans = new ProductTrans();
			productTrans.setCurrency("CASH");
			productTrans.setDurationMinute(0);
			productTrans.setHash(1133182666);
			productTrans.setIcon("Black_64x64");
			productTrans.setLevel(0);
			productTrans.setPrice(200000.00D);
			productTrans.setPriority(0);
			productTrans.setProductId("SRV-SCAR7");
			productTrans.setProductTitle("BLACK");
			productTrans.setProductType("PRESETCAR");
			productTrans.setUseCount(1);
			arrayOfProductTrans.getProductTrans().add(productTrans);
		}

		QName qName = new QName("", "ArrayOfProductTrans");
		JAXBElement<ArrayOfProductTrans> jaxbElement = new JAXBElement<ArrayOfProductTrans>(qName, ArrayOfProductTrans.class, null, arrayOfProductTrans);
		String marshal = MarshalXML.marshal(jaxbElement, "http://schemas.datacontract.org/2004/07/Victory.DataLayer.Serialization");
		response.getOutputStream().write(marshal.getBytes());
	}
}
