package com.soapboxrace.core.api.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import com.soapboxrace.jaxb.annotation.XsiSchemaLocation;

@Provider
@Produces(MediaType.APPLICATION_XML)
public class MarshallerInterceptor implements MessageBodyWriter<Object> {

	@Context
	protected Providers providers;

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void writeTo(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			if (annotations != null) {
				for (Annotation annotation : annotations) {
					if (annotation instanceof XsiSchemaLocation) {
						XsiSchemaLocation schemaAnnotation = (XsiSchemaLocation) annotation;
						String schemaLocation = schemaAnnotation.schemaLocation();
						jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
					}
				}
			}
			XmlType xmlTypeAnnotation = object.getClass().getAnnotation(XmlType.class);
			QName qname = new QName("", xmlTypeAnnotation.name());
			StringWriter stringWriter = new StringWriter();
			JAXBElement<Object> jaxbElement = new JAXBElement<Object>(qname, (Class<Object>) object.getClass(), null, object);
			jaxbMarshaller.marshal(jaxbElement, stringWriter);
			entityStream.write(stringWriter.toString().getBytes());
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

	@Override
	public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

}
