package com.soapboxrace.core.servlet.personas;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import com.soapboxrace.core.servlet.GenericServlet;

//Engine.svc/personas/100/baskets
//<BasketTrans xmlns="http://schemas.datacontract.org/2004/07/Victory.DataLayer.Serialization" 
//xmlns:i="http://www.w3.org/2001/XMLSchema-instance"><Items><BasketItemTrans>
//<ProductId>SRV-SCAR7</ProductId><Quantity>1</Quantity></BasketItemTrans></Items></BasketTrans>
@WebServlet(urlPatterns = { "/Engine.svc/personas/*" })
public class Personas extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7643461666384516248L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		String[] splitUri = splitUri(request);
		Long personaId = Long.valueOf(splitUri[4]);
		System.out.println("personaId: " + personaId);
	}
}
