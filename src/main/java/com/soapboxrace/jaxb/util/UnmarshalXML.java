/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class UnmarshalXML {

    public static <T> T unMarshal(InputStream is, Class<T> classz) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            JAXBContext jaxbContext = JAXBContext.newInstance(classz);
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
