/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeAchievementsAwarded", propOrder = {"achievementsAwarded"})
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeAchievementsAwarded {
    @XmlAttribute(name = "status")
    protected int status = 1;
    @XmlAttribute(name = "ticket")
    protected int ticket = 0;
    @XmlElement(name = "AchievementsAwarded", required = true)
    private AchievementsAwarded achievementsAwarded;

    public AchievementsAwarded getAchievementsAwarded() {
        return achievementsAwarded;
    }

    public void setAchievementsAwarded(AchievementsAwarded achievementsAwarded) {
        this.achievementsAwarded = achievementsAwarded;
    }
}