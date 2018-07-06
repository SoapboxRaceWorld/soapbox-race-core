package com.soapboxrace.core.bo.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

public class TimeConverter
{
    private static DatatypeFactory DATATYPE_FACTORY;

    static
    {
        try
        {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public static XMLGregorianCalendar generateGregorianCalendar(GregorianCalendar calendar)
    {
        return DATATYPE_FACTORY.newXMLGregorianCalendar(calendar);
    }
}
