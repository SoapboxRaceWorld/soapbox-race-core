package com.soapboxrace.core.servlet;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/systeminfo" })
public class SystemInfo extends GenericServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -207709800210654924L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		com.soapboxrace.jaxb.http.SystemInfo systemInfo = new com.soapboxrace.jaxb.http.SystemInfo();
		systemInfo.setBranch("production");
		systemInfo.setChangeList("620384");
		systemInfo.setClientVersion("637");
		systemInfo.setClientVersionCheck(true);
		systemInfo.setDeployed("08/20/2013 11:24:40");
		systemInfo.setEntitlementsToDownload(true);
		systemInfo.setForcePermanentSession(true);
		systemInfo.setJidPrepender("sbrw");
		systemInfo.setLauncherServiceUrl("http://127.0.0.1");
		systemInfo.setNucleusNamespace("sbrw-live");
		systemInfo.setNucleusNamespaceWeb("sbr_web");
		systemInfo.setPersonaCacheTimeout(900);
		systemInfo.setPortalDomain("soapboxraceworld.com");
		systemInfo.setPortalStoreFailurePage("soapboxraceworld.com/fail");
		systemInfo.setPortalTimeOut("6000");
		systemInfo.setShardName("CORE");
		GregorianCalendar c = new GregorianCalendar();
		try {
			XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			systemInfo.setTime(date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		systemInfo.setVersion("1599");

		JAXBElement<com.soapboxrace.jaxb.http.SystemInfo> createSystemInfo = new ObjectFactory().createSystemInfo(systemInfo);
		String marshal = MarshalXML.marshal(createSystemInfo);
		answer(request, response, marshal);
	}
}
