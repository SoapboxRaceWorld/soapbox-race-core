package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_ResponseTypeAchievementsAwarded", propOrder = {"achievementsAwarded"})
@XmlRootElement(name = "response")
public class XMPP_ResponseTypeAchievementsAwarded
{
    @XmlElement(name = "AchievementsAwarded", required = true)
    private AchievementsAwarded achievementsAwarded;

    @XmlAttribute(name = "status")
    protected int status = 1;
    
    @XmlAttribute(name = "ticket")
    protected int ticket = 0;

    public AchievementsAwarded getAchievementsAwarded()
    {
        return achievementsAwarded;
    }

    public void setAchievementsAwarded(AchievementsAwarded achievementsAwarded)
    {
        this.achievementsAwarded = achievementsAwarded;
    }
}