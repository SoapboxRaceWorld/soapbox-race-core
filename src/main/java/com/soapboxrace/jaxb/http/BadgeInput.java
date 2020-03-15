/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "BadgeInput")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"badgeDefinitionId", "slotId"})
public class BadgeInput {
    @XmlElement(name = "BadgeDefinitionId")
    private int badgeDefinitionId;

    @XmlElement(name = "SlotId")
    private short slotId;

    public int getBadgeDefinitionId() {
        return badgeDefinitionId;
    }

    public void setBadgeDefinitionId(int badgeDefinitionId) {
        this.badgeDefinitionId = badgeDefinitionId;
    }

    public short getSlotId() {
        return slotId;
    }

    public void setSlotId(short slotId) {
        this.slotId = slotId;
    }
}