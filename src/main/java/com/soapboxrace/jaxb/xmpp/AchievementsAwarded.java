/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import com.soapboxrace.jaxb.http.BadgePacket;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "AchievementsAwarded")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"achievements", "badges", "personaId", "progressed", "score"})
public class AchievementsAwarded {
    @XmlElementWrapper(name = "Achievements")
    @XmlElement(name = "AchievementAwarded")
    private List<AchievementAwarded> achievements;

    @XmlElementWrapper(name = "Badges")
    @XmlElement(name = "Badge")
    private List<BadgePacket> badges;

    @XmlElement(name = "PersonaId")
    private long personaId;

    @XmlElementWrapper(name = "Progressed")
    @XmlElement(name = "AchievementProgress")
    private List<AchievementProgress> progressed;

    @XmlElement(name = "Score")
    private int score;

    public List<AchievementAwarded> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<AchievementAwarded> achievements) {
        this.achievements = achievements;
    }

    public List<BadgePacket> getBadges() {
        return badges;
    }

    public void setBadges(List<BadgePacket> badges) {
        this.badges = badges;
    }

    public long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(long personaId) {
        this.personaId = personaId;
    }

    public List<AchievementProgress> getProgressed() {
        return progressed;
    }

    public void setProgressed(List<AchievementProgress> progressed) {
        this.progressed = progressed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}