package com.soapboxrace.core.servlet.driverpersona;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ArrayOfInt;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/DriverPersona/GetExpLevelPointsMap" })
public class GetExpLevelPointsMap extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7900043925324697940L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		ArrayOfInt arrayOfInt = new ArrayOfInt();
		arrayOfInt.getInt().add(100);
		arrayOfInt.getInt().add(975);
		arrayOfInt.getInt().add(2025);
		arrayOfInt.getInt().add(3625);
		arrayOfInt.getInt().add(5875);
		arrayOfInt.getInt().add(8875);
		arrayOfInt.getInt().add(12725);
		arrayOfInt.getInt().add(17525);
		arrayOfInt.getInt().add(23375);
		arrayOfInt.getInt().add(30375);
		arrayOfInt.getInt().add(39375);
		arrayOfInt.getInt().add(50575);
		arrayOfInt.getInt().add(64175);
		arrayOfInt.getInt().add(80375);
		arrayOfInt.getInt().add(99375);
		arrayOfInt.getInt().add(121375);
		arrayOfInt.getInt().add(146575);
		arrayOfInt.getInt().add(175175);
		arrayOfInt.getInt().add(207375);
		arrayOfInt.getInt().add(243375);
		arrayOfInt.getInt().add(283375);
		arrayOfInt.getInt().add(327575);
		arrayOfInt.getInt().add(376175);
		arrayOfInt.getInt().add(429375);
		arrayOfInt.getInt().add(487375);
		arrayOfInt.getInt().add(550375);
		arrayOfInt.getInt().add(618575);
		arrayOfInt.getInt().add(692175);
		arrayOfInt.getInt().add(771375);
		arrayOfInt.getInt().add(856375);
		arrayOfInt.getInt().add(950875);
		arrayOfInt.getInt().add(1055275);
		arrayOfInt.getInt().add(1169975);
		arrayOfInt.getInt().add(1295375);
		arrayOfInt.getInt().add(1431875);
		arrayOfInt.getInt().add(1579875);
		arrayOfInt.getInt().add(1739775);
		arrayOfInt.getInt().add(1911975);
		arrayOfInt.getInt().add(2096875);
		arrayOfInt.getInt().add(2294875);
		arrayOfInt.getInt().add(2506375);
		arrayOfInt.getInt().add(2731775);
		arrayOfInt.getInt().add(2971475);
		arrayOfInt.getInt().add(3225875);
		arrayOfInt.getInt().add(3495375);
		arrayOfInt.getInt().add(3780375);
		arrayOfInt.getInt().add(4081275);
		arrayOfInt.getInt().add(4398475);
		arrayOfInt.getInt().add(4732375);
		arrayOfInt.getInt().add(5083375);
		arrayOfInt.getInt().add(5481355);
		arrayOfInt.getInt().add(5898805);
		arrayOfInt.getInt().add(6336165);
		arrayOfInt.getInt().add(6793875);
		arrayOfInt.getInt().add(7272375);
		arrayOfInt.getInt().add(7772105);
		arrayOfInt.getInt().add(8293505);
		arrayOfInt.getInt().add(8837015);
		arrayOfInt.getInt().add(9403075);
		arrayOfInt.getInt().add(9992125);

		JAXBElement<ArrayOfInt> createArrayOfInt = new ObjectFactory().createArrayOfInt(arrayOfInt);
		String marshal = MarshalXML.marshal(createArrayOfInt);
		response.getOutputStream().write(marshal.getBytes());
	}
}
