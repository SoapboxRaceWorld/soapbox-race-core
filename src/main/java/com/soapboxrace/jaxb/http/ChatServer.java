
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
 * <p>Java class for chatServer complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="chatServer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Rooms" type="{}ArrayOfChatRoom" minOccurs="0"/>
 *         &lt;element name="ip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="prefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chatServer", propOrder = {
        "rooms",
        "ip",
        "port",
        "prefix"
})
public class ChatServer {

    @XmlElement(name = "Rooms")
    protected ArrayOfChatRoom rooms;
    protected String ip;
    protected int port;
    protected String prefix;

    /**
     * Gets the value of the rooms property.
     *
     * @return possible object is
     * {@link ArrayOfChatRoom }
     */
    public ArrayOfChatRoom getRooms() {
        return rooms;
    }

    /**
     * Sets the value of the rooms property.
     *
     * @param value allowed object is
     *              {@link ArrayOfChatRoom }
     */
    public void setRooms(ArrayOfChatRoom value) {
        this.rooms = value;
    }

    /**
     * Gets the value of the ip property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets the value of the ip property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIp(String value) {
        this.ip = value;
    }

    /**
     * Gets the value of the port property.
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the value of the port property.
     */
    public void setPort(int value) {
        this.port = value;
    }

    /**
     * Gets the value of the prefix property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the value of the prefix property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPrefix(String value) {
        this.prefix = value;
    }

}
