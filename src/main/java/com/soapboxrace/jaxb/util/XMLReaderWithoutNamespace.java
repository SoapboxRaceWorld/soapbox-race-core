/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.util;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

public class XMLReaderWithoutNamespace extends StreamReaderDelegate {
    public XMLReaderWithoutNamespace(XMLStreamReader reader) {
        super(reader);
    }

    @Override
    public String getAttributeNamespace(int arg0) {
        return "";
    }

    @Override
    public String getNamespaceURI() {
        return "";
    }
}
