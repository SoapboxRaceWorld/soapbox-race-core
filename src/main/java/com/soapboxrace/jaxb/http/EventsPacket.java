
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EventsPacket complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="EventsPacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Events" type="{}ArrayOfEventDefinition" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventsPacket", propOrder = {
        "events"
})
public class EventsPacket {

    @XmlElement(name = "Events")
    protected ArrayOfEventDefinition events;

    /**
     * Gets the value of the events property.
     *
     * @return possible object is
     * {@link ArrayOfEventDefinition }
     */
    public ArrayOfEventDefinition getEvents() {
        return events;
    }

    /**
     * Sets the value of the events property.
     *
     * @param value allowed object is
     *              {@link ArrayOfEventDefinition }
     */
    public void setEvents(ArrayOfEventDefinition value) {
        this.events = value;
    }

}
