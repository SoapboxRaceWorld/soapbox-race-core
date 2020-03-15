/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FriendPersona", propOrder = {
        "iconIndex",
        "level",
        "name",
        "originalName",
        "personaId",
        "presence",
        "socialNetwork",
        "userId"
})
@XmlRootElement(name = "FriendPersona")
public class XMPP_FriendPersonaType {

    protected int iconIndex;
    protected int level;
    protected String name;
    protected String originalName;
    protected long personaId;
    protected Long presence;
    protected int socialNetwork;
    protected long userId;

    public int getIconIndex() {
        return iconIndex;
    }

    public void setIconIndex(int value) {
        this.iconIndex = value;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int value) {
        this.level = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String value) {
        this.originalName = value;
    }

    public long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(long value) {
        this.personaId = value;
    }

    public Long getPresence() {
        return presence;
    }

    public void setPresence(Long value) {
        this.presence = value;
    }

    public int getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(int value) {
        this.socialNetwork = value;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long value) {
        this.userId = value;
    }

}