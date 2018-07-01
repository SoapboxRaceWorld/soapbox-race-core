package com.soapboxrace.jaxb.xmpp;

import com.soapboxrace.jaxb.http.PersonaBase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypePersonaBase", propOrder = { "personaBase" })
@XmlRootElement(name = "response")
public class XMPP_ResponseTypePersonaBase {
    @XmlElement(name = "PersonaBase", required = true)
    protected PersonaBase personaBase;

    @XmlAttribute(name = "status")
    protected int status = 1;
    @XmlAttribute(name = "ticket")
    protected int ticket = 0;

    public PersonaBase getPersonaBase()
    {
        return personaBase;
    }

    public void setPersonaBase(PersonaBase personaBase)
    {
        this.personaBase = personaBase;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }
}