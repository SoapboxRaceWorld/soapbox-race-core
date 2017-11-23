package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ACHIEVEMENT_DEFINITION")
public class AchievementDefinitionEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "friendlyIdentifier")
    private String friendlyIdentifier;

    @Column(name = "isVisible")
    private boolean isVisible;

    @Column(name = "progressText")
    private String progressText;

    @JoinColumn(name = "badgeDefinitionId", referencedColumnName = "id")
    @OneToOne
    private BadgeDefinitionEntity badgeDefinition;

    @OneToMany(mappedBy = "achievementDefinition", fetch = FetchType.EAGER, targetEntity = AchievementRankEntity.class)
    private List<AchievementRankEntity> ranks;

    @Column(name = "statConversion")
    private String statConversion;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFriendlyIdentifier()
    {
        return friendlyIdentifier;
    }

    public void setFriendlyIdentifier(String friendlyIdentifier)
    {
        this.friendlyIdentifier = friendlyIdentifier;
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    public void setVisible(boolean visible)
    {
        isVisible = visible;
    }

    public String getProgressText()
    {
        return progressText;
    }

    public void setProgressText(String progressText)
    {
        this.progressText = progressText;
    }

    public BadgeDefinitionEntity getBadgeDefinition()
    {
        return badgeDefinition;
    }

    public void setBadgeDefinition(BadgeDefinitionEntity badgeDefinition)
    {
        this.badgeDefinition = badgeDefinition;
    }

    public List<AchievementRankEntity> getRanks()
    {
        return ranks;
    }

    public void setRanks(List<AchievementRankEntity> ranks)
    {
        this.ranks = ranks;
    }

    public String getStatConversion()
    {
        return statConversion;
    }

    public void setStatConversion(String statConversion)
    {
        this.statConversion = statConversion;
    }

    public Long getMaximumValue()
    {
        if (ranks.isEmpty())
            throw new IllegalStateException("This achievement (" + friendlyIdentifier + ") has no ranks!");

        return ranks.get(ranks.size() - 1).getThresholdValue();
    }
}