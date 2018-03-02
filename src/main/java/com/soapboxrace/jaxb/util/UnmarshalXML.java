package com.soapboxrace.jaxb.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class UnmarshalXML {

	@SuppressWarnings("unchecked")
	public static <T> T unMarshal(InputStream is, Class<T> classz) {
		T objTmp = null;
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			JAXBContext jaxbContext = JAXBContext.newInstance(classz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(inputStreamReader);
			XMLReaderWithoutNamespace xr = new XMLReaderWithoutNamespace(xsr);
			JAXBElement<Object> jaxbElement = (JAXBElement<Object>) jaxbUnmarshaller.unmarshal(xr, classz);
			objTmp = (T) jaxbElement.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objTmp;
	}

	public static <T> T unMarshal(String xmlStr, Class<T> classz) {
		InputStream is = new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8));
		return unMarshal(is, classz);
	}
}
