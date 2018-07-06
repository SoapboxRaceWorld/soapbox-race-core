package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AchievementProgress")
@XmlAccessorType(XmlAccessType.FIELD)
public class AchievementProgress
{
    @XmlElement(name = "AchievementDefinitionId")
    private Long achievementDefinitionId;
    
    @XmlElement(name = "CurrentValue")
    private long currentValue;

    public Long getAchievementDefinitionId()
    {
        return achievementDefinitionId;
    }

    public void setAchievementDefinitionId(Long achievementDefinitionId)
    {
        this.achievementDefinitionId = achievementDefinitionId;
    }

    public long getCurrentValue()
    {
        return currentValue;
    }

    public void setCurrentValue(long currentValue)
    {
        this.currentValue = currentValue;
    }
}