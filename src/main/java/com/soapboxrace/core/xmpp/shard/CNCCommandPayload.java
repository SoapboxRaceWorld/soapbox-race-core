package com.soapboxrace.core.xmpp.shard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "CNCCommandPayload", propOrder = {"body"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CNCCommandPayload
{
    @XmlElement(name = "Body")
    private String body;

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }
}
