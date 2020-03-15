
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
 * <p>Java class for LobbyEntrantAdded complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LobbyEntrantAdded">
 *   &lt;complexContent>
 *     &lt;extension base="{}LobbyEntrantInfo">
 *       &lt;sequence>
 *         &lt;element name="LobbyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LobbyEntrantAdded", propOrder = {
        "lobbyId"
})
public class LobbyEntrantAdded
        extends LobbyEntrantInfo {

    @XmlElement(name = "LobbyId")
    protected long lobbyId;

    /**
     * Gets the value of the lobbyId property.
     */
    public long getLobbyId() {
        return lobbyId;
    }

    /**
     * Sets the value of the lobbyId property.
     */
    public void setLobbyId(long value) {
        this.lobbyId = value;
    }

}
