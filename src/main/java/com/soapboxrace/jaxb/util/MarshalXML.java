package com.soapboxrace.jaxb.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

public class MarshalXML {

	@SuppressWarnings("unchecked")
	public static String marshal(Object obj) {
		StringWriter stringWriter = new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			XmlRootElement xmlRootAnnotation = obj.getClass().getAnnotation(XmlRootElement.class);
			if (xmlRootAnnotation == null) {
				XmlType xmlTypeAnnotation = obj.getClass().getAnnotation(XmlType.class);
				QName qname = new QName("", xmlTypeAnnotation.name());
				JAXBElement<Object> jaxbElement = new JAXBElement<Object>(qname, (Class<Object>) obj.getClass(), null, obj);
				jaxbMarshaller.marshal(jaxbElement, stringWriter);
			} else {
				jaxbMarshaller.marshal(obj, stringWriter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}
}