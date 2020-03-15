/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import com.soapboxrace.jaxb.http.PersonaBase;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypePersonaBase", propOrder = {"personaBase"})
@XmlRootElement(name = "response")
public class XMPP_ResponseTypePersonaBase {
    @XmlElement(name = "PersonaBase", required = true)
    protected PersonaBase personaBase;

    @XmlAttribute(name = "status")
    protected int status = 1;
    @XmlAttribute(name = "ticket")
    protected int ticket = 0;

    public PersonaBase getPersonaBase() {
        return personaBase;
    }

    public void setPersonaBase(PersonaBase personaBase) {
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