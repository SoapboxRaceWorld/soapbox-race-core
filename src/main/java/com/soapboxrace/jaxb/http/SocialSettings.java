
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
 * <p>Java class for SocialSettings complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SocialSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AppearOffline" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DeclineGroupInvite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DeclineIncommingFriendRequests" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DeclinePrivateInvite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HideOfflineFriends" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ShowNewsOnSignIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ShowOnlyPlayersInSameChatChannel" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocialSettings", propOrder = {
        "appearOffline",
        "declineGroupInvite",
        "declineIncommingFriendRequests",
        "declinePrivateInvite",
        "hideOfflineFriends",
        "showNewsOnSignIn",
        "showOnlyPlayersInSameChatChannel"
})
public class SocialSettings {

    @XmlElement(name = "AppearOffline")
    protected boolean appearOffline;
    @XmlElement(name = "DeclineGroupInvite")
    protected int declineGroupInvite;
    @XmlElement(name = "DeclineIncommingFriendRequests")
    protected boolean declineIncommingFriendRequests;
    @XmlElement(name = "DeclinePrivateInvite")
    protected int declinePrivateInvite;
    @XmlElement(name = "HideOfflineFriends")
    protected boolean hideOfflineFriends;
    @XmlElement(name = "ShowNewsOnSignIn")
    protected boolean showNewsOnSignIn;
    @XmlElement(name = "ShowOnlyPlayersInSameChatChannel")
    protected boolean showOnlyPlayersInSameChatChannel;

    /**
     * Gets the value of the appearOffline property.
     */
    public boolean isAppearOffline() {
        return appearOffline;
    }

    /**
     * Sets the value of the appearOffline property.
     */
    public void setAppearOffline(boolean value) {
        this.appearOffline = value;
    }

    /**
     * Gets the value of the declineGroupInvite property.
     */
    public int getDeclineGroupInvite() {
        return declineGroupInvite;
    }

    /**
     * Sets the value of the declineGroupInvite property.
     */
    public void setDeclineGroupInvite(int value) {
        this.declineGroupInvite = value;
    }

    /**
     * Gets the value of the declineIncommingFriendRequests property.
     */
    public boolean isDeclineIncommingFriendRequests() {
        return declineIncommingFriendRequests;
    }

    /**
     * Sets the value of the declineIncommingFriendRequests property.
     */
    public void setDeclineIncommingFriendRequests(boolean value) {
        this.declineIncommingFriendRequests = value;
    }

    /**
     * Gets the value of the declinePrivateInvite property.
     */
    public int getDeclinePrivateInvite() {
        return declinePrivateInvite;
    }

    /**
     * Sets the value of the declinePrivateInvite property.
     */
    public void setDeclinePrivateInvite(int value) {
        this.declinePrivateInvite = value;
    }

    /**
     * Gets the value of the hideOfflineFriends property.
     */
    public boolean isHideOfflineFriends() {
        return hideOfflineFriends;
    }

    /**
     * Sets the value of the hideOfflineFriends property.
     */
    public void setHideOfflineFriends(boolean value) {
        this.hideOfflineFriends = value;
    }

    /**
     * Gets the value of the showNewsOnSignIn property.
     */
    public boolean isShowNewsOnSignIn() {
        return showNewsOnSignIn;
    }

    /**
     * Sets the value of the showNewsOnSignIn property.
     */
    public void setShowNewsOnSignIn(boolean value) {
        this.showNewsOnSignIn = value;
    }

    /**
     * Gets the value of the showOnlyPlayersInSameChatChannel property.
     */
    public boolean isShowOnlyPlayersInSameChatChannel() {
        return showOnlyPlayersInSameChatChannel;
    }

    /**
     * Sets the value of the showOnlyPlayersInSameChatChannel property.
     */
    public void setShowOnlyPlayersInSameChatChannel(boolean value) {
        this.showOnlyPlayersInSameChatChannel = value;
    }

}
