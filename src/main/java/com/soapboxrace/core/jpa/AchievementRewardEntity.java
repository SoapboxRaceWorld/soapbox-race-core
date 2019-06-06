package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "ACHIEVEMENT_REWARD")
@NamedQueries({
        @NamedQuery(name = "AchievementRewardEntity.findByAchievementRankId", query = "SELECT obj FROM AchievementRewardEntity obj WHERE obj.achievementRankEntity.id = :achievementRankId")
})
public class AchievementRewardEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.DETACH, targetEntity = AchievementRankEntity.class)
    @JoinColumn(name = "achievement_rank_id", referencedColumnName = "ID")
    private AchievementRankEntity achievementRankEntity;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String rewardScript;

    @Column(name = "reward_description")
    private String rewardDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AchievementRankEntity getAchievementRankEntity() {
        return achievementRankEntity;
    }

    public void setAchievementRankEntity(AchievementRankEntity achievementRankEntity) {
        this.achievementRankEntity = achievementRankEntity;
    }

    public String getRewardScript() {
        return rewardScript;
    }

    public void setRewardScript(String rewardScript) {
        this.rewardScript = rewardScript;
    }

    public String getRewardDescription() {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
    }
}
