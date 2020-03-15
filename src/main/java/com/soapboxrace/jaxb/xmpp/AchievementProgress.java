/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AchievementProgress")
@XmlAccessorType(XmlAccessType.FIELD)
public class AchievementProgress {
    @XmlElement(name = "AchievementDefinitionId")
    private Long achievementDefinitionId;

    @XmlElement(name = "CurrentValue")
    private long currentValue;

    public Long getAchievementDefinitionId() {
        return achievementDefinitionId;
    }

    public void setAchievementDefinitionId(Long achievementDefinitionId) {
        this.achievementDefinitionId = achievementDefinitionId;
    }

    public long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(long currentValue) {
        this.currentValue = currentValue;
    }
}