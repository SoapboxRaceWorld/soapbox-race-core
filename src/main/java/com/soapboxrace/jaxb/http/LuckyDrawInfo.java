
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
 * <p>Java class for LuckyDrawInfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LuckyDrawInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Boxes" type="{}ArrayOfLuckyDrawBox" minOccurs="0"/>
 *         &lt;element name="CardDeck" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CurrentStreak" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsStreakBroken" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Items" type="{}ArrayOfLuckyDrawItem" minOccurs="0"/>
 *         &lt;element name="NumBoxAnimations" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumCards" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuckyDrawInfo", propOrder = {
        "boxes",
        "cardDeck",
        "currentStreak",
        "isStreakBroken",
        "items",
        "numBoxAnimations",
        "numCards"
})
public class LuckyDrawInfo {

    @XmlElement(name = "Boxes")
    protected ArrayOfLuckyDrawBox boxes;
    @XmlElement(name = "CardDeck")
    protected String cardDeck;
    @XmlElement(name = "CurrentStreak")
    protected int currentStreak;
    @XmlElement(name = "IsStreakBroken")
    protected boolean isStreakBroken;
    @XmlElement(name = "Items")
    protected ArrayOfLuckyDrawItem items;
    @XmlElement(name = "NumBoxAnimations")
    protected int numBoxAnimations;
    @XmlElement(name = "NumCards")
    protected int numCards;

    /**
     * Gets the value of the boxes property.
     *
     * @return possible object is
     * {@link ArrayOfLuckyDrawBox }
     */
    public ArrayOfLuckyDrawBox getBoxes() {
        return boxes;
    }

    /**
     * Sets the value of the boxes property.
     *
     * @param value allowed object is
     *              {@link ArrayOfLuckyDrawBox }
     */
    public void setBoxes(ArrayOfLuckyDrawBox value) {
        this.boxes = value;
    }

    /**
     * Gets the value of the cardDeck property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCardDeck() {
        return cardDeck;
    }

    /**
     * Sets the value of the cardDeck property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCardDeck(String value) {
        this.cardDeck = value;
    }

    /**
     * Gets the value of the currentStreak property.
     */
    public int getCurrentStreak() {
        return currentStreak;
    }

    /**
     * Sets the value of the currentStreak property.
     */
    public void setCurrentStreak(int value) {
        this.currentStreak = value;
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
     * Gets the value of the items property.
     *
     * @return possible object is
     * {@link ArrayOfLuckyDrawItem }
     */
    public ArrayOfLuckyDrawItem getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     *
     * @param value allowed object is
     *              {@link ArrayOfLuckyDrawItem }
     */
    public void setItems(ArrayOfLuckyDrawItem value) {
        this.items = value;
    }

    /**
     * Gets the value of the numBoxAnimations property.
     */
    public int getNumBoxAnimations() {
        return numBoxAnimations;
    }

    /**
     * Sets the value of the numBoxAnimations property.
     */
    public void setNumBoxAnimations(int value) {
        this.numBoxAnimations = value;
    }

    /**
     * Gets the value of the numCards property.
     */
    public int getNumCards() {
        return numCards;
    }

    /**
     * Sets the value of the numCards property.
     */
    public void setNumCards(int value) {
        this.numCards = value;
    }

}
