package com.soapboxrace.core.jpa;

import com.soapboxrace.jaxb.http.AchievementRankPacket;

import javax.persistence.*;

@Entity
@Table(name = "ACHIEVEMENT_RANK")
public class AchievementRankEntity
{
    private static final long serialVersionUID = 5314835854384144788L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @JoinColumn(name = "achievementId", referencedColumnName = "id")
    @ManyToOne
    private AchievementDefinitionEntity achievementDefinition;

    @Column(name = "isRare")
    private boolean isRare;

    @Column(name = "points")
    private short points;

    @Column(name = "rank")
    private short rank;

	@Column(name = "rarity")
	private float rarity = 0.000f;

    @Column(name = "rewardDescription")
    private String rewardDescription;

    @Column(name = "rewardType")
    private String rewardType;

    @Column(name = "rewardVisualStyle")
    private String rewardVisualStyle;

    @Column(name = "thresholdValue")
    private Long thresholdValue;

    public AchievementRankPacket toBasePacket()
    {
        AchievementRankPacket packet = new AchievementRankPacket();
        packet.setAchievementRankId(id);
        packet.setIsRare(isRare);
        packet.setRarity(getRarity());
        packet.setRank(rank);
        packet.setThresholdValue(thresholdValue);
        packet.setRewardDescription(rewardDescription);
        packet.setRewardType(rewardType);
        packet.setRewardVisualStyle(rewardVisualStyle);
        packet.setPoints(points);

        return packet;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public AchievementDefinitionEntity getAchievementDefinition()
    {
        return achievementDefinition;
    }

    public void setAchievementDefinition(AchievementDefinitionEntity achievementDefinition)
    {
        this.achievementDefinition = achievementDefinition;
    }

    public boolean isRare()
    {
        return isRare;
    }

    public void setRare(boolean rare)
    {
        isRare = rare;
    }

    public short getPoints()
    {
        return points;
    }

    public void setPoints(short points)
    {
        this.points = points;
    }

    public short getRank()
    {
        return rank;
    }

    public void setRank(short rank)
    {
        this.rank = rank;
    }


    public float getRarity()
	{
		return rarity;
	}

	public void setRarity(float rarity)
	{
		this.rarity = rarity;
	}

    public String getRewardDescription()
    {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription)
    {
        this.rewardDescription = rewardDescription;
    }

    public String getRewardType()
    {
        return rewardType;
    }

    public void setRewardType(String rewardType)
    {
        this.rewardType = rewardType;
    }

    public String getRewardVisualStyle()
    {
        return rewardVisualStyle;
    }

    public void setRewardVisualStyle(String rewardVisualStyle)
    {
        this.rewardVisualStyle = rewardVisualStyle;
    }

    public Long getThresholdValue()
    {
        return thresholdValue;
    }

    public void setThresholdValue(Long thresholdValue)
    {
        this.thresholdValue = thresholdValue;
    }
}