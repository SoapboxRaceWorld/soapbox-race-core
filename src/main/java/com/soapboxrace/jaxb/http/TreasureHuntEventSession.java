
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
 * <p>Java class for TreasureHuntEventSession complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TreasureHuntEventSession">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CoinsCollected" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsStreakBroken" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="NumCoins" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Seed" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Streak" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreasureHuntEventSession", propOrder = {
        "coinsCollected",
        "isStreakBroken",
        "numCoins",
        "seed",
        "streak"
})
public class TreasureHuntEventSession {

    @XmlElement(name = "CoinsCollected")
    protected int coinsCollected;
    @XmlElement(name = "IsStreakBroken")
    protected boolean isStreakBroken;
    @XmlElement(name = "NumCoins")
    protected int numCoins;
    @XmlElement(name = "Seed")
    protected int seed;
    @XmlElement(name = "Streak")
    protected int streak;

    /**
     * Gets the value of the coinsCollected property.
     */
    public int getCoinsCollected() {
        return coinsCollected;
    }

    /**
     * Sets the value of the coinsCollected property.
     */
    public void setCoinsCollected(int value) {
        this.coinsCollected = value;
    }

    /**
     * Gets the value of the isStreakBroken property.
     */
    public boolean isIsStreakBroken() {
        return isStreakBroken;
    }

    /**
     * Sets the value of the isStreakBroken property.
     */
    public void setIsStreakBroken(boolean value) {
        this.isStreakBroken = value;
    }

    /**
     * Gets the value of the numCoins property.
     */
    public int getNumCoins() {
        return numCoins;
    }

    /**
     * Sets the value of the numCoins property.
     */
    public void setNumCoins(int value) {
        this.numCoins = value;
    }

    /**
     * Gets the value of the seed property.
     */
    public int getSeed() {
        return seed;
    }

    /**
     * Sets the value of the seed property.
     */
    public void setSeed(int value) {
        this.seed = value;
    }

    /**
     * Gets the value of the streak property.
     */
    public int getStreak() {
        return streak;
    }

    /**
     * Sets the value of the streak property.
     */
    public void setStreak(int value) {
        this.streak = value;
    }

}
