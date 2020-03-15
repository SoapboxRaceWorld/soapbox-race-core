
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FraudConfig complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FraudConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enabledBitField" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="gameFileFreq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="moduleFreq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startUpFreq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FraudConfig", propOrder = {
        "enabledBitField",
        "gameFileFreq",
        "moduleFreq",
        "startUpFreq",
        "userID"
})
public class FraudConfig {

    protected int enabledBitField;
    protected int gameFileFreq;
    protected int moduleFreq;
    protected int startUpFreq;
    protected long userID;

    /**
     * Gets the value of the enabledBitField property.
     */
    public int getEnabledBitField() {
        return enabledBitField;
    }

    /**
     * Sets the value of the enabledBitField property.
     */
    public void setEnabledBitField(int value) {
        this.enabledBitField = value;
    }

    /**
     * Gets the value of the gameFileFreq property.
     */
    public int getGameFileFreq() {
        return gameFileFreq;
    }

    /**
     * Sets the value of the gameFileFreq property.
     */
    public void setGameFileFreq(int value) {
        this.gameFileFreq = value;
    }

    /**
     * Gets the value of the moduleFreq property.
     */
    public int getModuleFreq() {
        return moduleFreq;
    }

    /**
     * Sets the value of the moduleFreq property.
     */
    public void setModuleFreq(int value) {
        this.moduleFreq = value;
    }

    /**
     * Gets the value of the startUpFreq property.
     */
    public int getStartUpFreq() {
        return startUpFreq;
    }

    /**
     * Sets the value of the startUpFreq property.
     */
    public void setStartUpFreq(int value) {
        this.startUpFreq = value;
    }

    /**
     * Gets the value of the userID property.
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     */
    public void setUserID(long value) {
        this.userID = value;
    }

}
