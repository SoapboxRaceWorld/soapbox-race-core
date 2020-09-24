/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class TimeConverter {
    private static final DatatypeFactory DATATYPE_FACTORY;

    static {
        try {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static XMLGregorianCalendar generateGregorianCalendar(GregorianCalendar calendar) {
        return DATATYPE_FACTORY.newXMLGregorianCalendar(calendar);
    }

    public static long getTicks() {
        return getTicks(System.currentTimeMillis());
    }

    public static long getTicks(LocalDateTime localDateTime) {
        return getTicks(localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
    }

    public static long getTicks(long unixTimestampMs) {
        return unixTimestampMs * 10000 + 621355968000000000L;
    }
}
