package com.soapboxrace.jaxb.util;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class UnmarshalXML {

	@SuppressWarnings("unchecked")
	public static Object unMarshal(InputStream is, Class<? extends Object> classz) {
		Object objTmp = null;
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			JAXBContext jaxbContext = JAXBContext.newInstance(classz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(inputStreamReader);
			XMLReaderWithoutNamespace xr = new XMLReaderWithoutNamespace(xsr);
			JAXBElement<Object> jaxbElement = (JAXBElement<Object>) jaxbUnmarshaller.unmarshal(xr, classz);
			objTmp = jaxbElement.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objTmp;
	}

}
