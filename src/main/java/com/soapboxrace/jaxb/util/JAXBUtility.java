/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.util;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JAXBUtility {

    private static final Map<Class<?>, JAXBContext> contextStore = new ConcurrentHashMap<>();

    protected static JAXBContext getContextInstance(Class<?> objectClass) throws JAXBException {
        JAXBContext context = contextStore.get(objectClass);
        if (context == null) {
            context = JAXBContext.newInstance(objectClass);
            contextStore.put(objectClass, context);
        }
        return context;
    }

    @SuppressWarnings("unchecked")
    public static String marshal(Object obj) {
        if (obj == null) {
            return "";
        }

        StringWriter stringWriter = new StringWriter();
        try {
            JAXBContext jaxbContext = getContextInstance(obj.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            XmlRootElement xmlRootAnnotation = obj.getClass().getAnnotation(XmlRootElement.class);
            if (xmlRootAnnotation == null) {
                XmlType xmlTypeAnnotation = obj.getClass().getAnnotation(XmlType.class);
                QName qname = new QName("", xmlTypeAnnotation.name());
                JAXBElement<Object> jaxbElement = new JAXBElement<>(qname, (Class<Object>) obj.getClass(), null, obj);
                jaxbMarshaller.marshal(jaxbElement, stringWriter);
            } else {
                jaxbMarshaller.marshal(obj, stringWriter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    public static <T> T unMarshal(InputStream is, Class<T> classz) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            JAXBContext jaxbContext = getContextInstance(classz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(inputStreamReader);
            XMLReaderWithoutNamespace xr = new XMLReaderWithoutNamespace(xsr);
            JAXBElement<T> jaxbElement = jaxbUnmarshaller.unmarshal(xr, classz);
            return jaxbElement.getValue();
        } catch (Exception e) {
            throw new RuntimeException("Failed to unmarshal stream to " + classz.getCanonicalName() + " instance", e);
        }
    }

    public static <T> T unMarshal(String xmlStr, Class<T> classz) {
        InputStream is = new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8));
        return unMarshal(is, classz);
    }
}