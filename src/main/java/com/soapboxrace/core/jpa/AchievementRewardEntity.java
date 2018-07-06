package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "ACHIEVEMENT_REWARD")
@NamedQueries({
        @NamedQuery(name = "AchievementRewardEntity.findByDescription", 
                query = "SELECT obj FROM AchievementRewardEntity obj WHERE obj.rewardDescription = :description")
})
public class AchievementRewardEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "rewardDescription")
    private String rewardDescription;
    
    @Column(name = "rewardTitle")
    private String rewardTitle;
    
    @Column(name = "value", length = 2048)
    private String value;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRewardDescription()
    {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription)
    {
        this.rewardDescription = rewardDescription;
    }

    public String getRewardTitle()
    {
        return rewardTitle;
    }

    public void setRewardTitle(String rewardTitle)
    {
        this.rewardTitle = rewardTitle;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
