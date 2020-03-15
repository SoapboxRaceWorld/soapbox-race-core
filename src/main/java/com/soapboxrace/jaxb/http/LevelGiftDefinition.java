
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
 * <p>Java class for LevelGiftDefinition complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LevelGiftDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Boost" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LevelGiftId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LevelGiftDefinition", propOrder = {
        "boost",
        "level",
        "levelGiftId"
})
public class LevelGiftDefinition {

    @XmlElement(name = "Boost")
    protected int boost;
    @XmlElement(name = "Level")
    protected int level;
    @XmlElement(name = "LevelGiftId")
    protected int levelGiftId;

    /**
     * Gets the value of the boost property.
     */
    public int getBoost() {
        return boost;
    }

    /**
     * Sets the value of the boost property.
     */
    public void setBoost(int value) {
        this.boost = value;
    }

    /**
     * Gets the value of the level property.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Gets the value of the levelGiftId property.
     */
    public int getLevelGiftId() {
        return levelGiftId;
    }

    /**
     * Sets the value of the levelGiftId property.
     */
    public void setLevelGiftId(int value) {
        this.levelGiftId = value;
    }

}
