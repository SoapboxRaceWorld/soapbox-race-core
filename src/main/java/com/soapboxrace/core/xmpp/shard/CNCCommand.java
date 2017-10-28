package com.soapboxrace.core.xmpp.shard;

import javax.xml.bind.annotation.*;

@XmlType(name = "CNCCommand")
@XmlAccessorType(XmlAccessType.FIELD)
public class CNCCommand
{
    @XmlAttribute(name = "Token")
    private String token;
    
    @XmlAttribute(name = "ToPersonaId")
    private Long toPersonaId;
    
    @XmlElement(name = "Payload")
    private CNCCommandPayload payload;

    public CNCCommandPayload getPayload()
    {
        return payload;
    }

    public void setPayload(CNCCommandPayload payload)
    {
        this.payload = payload;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public Long getToPersonaId()
    {
        return toPersonaId;
    }

    public void setToPersonaId(Long toPersonaId)
    {
        this.toPersonaId = toPersonaId;
    }
}
