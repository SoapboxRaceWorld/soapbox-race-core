package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.AchievementEntity;
import com.soapboxrace.core.jpa.AchievementRankEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AchievementUpdateInfo {
    private final AchievementEntity achievementEntity;
    private ProgressedAchievement progressedAchievement;
    private final List<CompletedAchievementRank> completedAchievementRanks = new ArrayList<>();
    private Integer pointsGiven = 0;

    public AchievementUpdateInfo(AchievementEntity achievementEntity) {
        this.achievementEntity = achievementEntity;
    }

    public AchievementEntity getAchievementEntity() {
        return achievementEntity;
    }

    public Integer getPointsGiven() {
        return pointsGiven;
    }

    public void setPointsGiven(Integer pointsGiven) {
        this.pointsGiven = pointsGiven;
    }

    public List<CompletedAchievementRank> getCompletedAchievementRanks() {
        return completedAchievementRanks;
    }

    public ProgressedAchievement getProgressedAchievement() {
        return progressedAchievement;
    }

    public void setProgressedAchievement(ProgressedAchievement progressedAchievement) {
        this.progressedAchievement = progressedAchievement;
    }

    public static class ProgressedAchievement {
        private final Long achievementDefinitionId;

        private final Long value;

        public ProgressedAchievement(Long achievementDefinitionId, Long value) {
            this.achievementDefinitionId = achievementDefinitionId;
            this.value = value;
        }

        public Long getAchievementDefinitionId() {
            return achievementDefinitionId;
        }

        public Long getValue() {
            return value;
        }
    }

    public static class CompletedAchievementRank {
        private final AchievementEntity achievementEntity;

        private final AchievementRankEntity achievementRankEntity;

        private final LocalDateTime achievedOn;

        public CompletedAchievementRank(AchievementEntity achievementEntity, AchievementRankEntity achievementRankEntity, LocalDateTime achievedOn) {
            this.achievementEntity = achievementEntity;
            this.achievementRankEntity = achievementRankEntity;
            this.achievedOn = achievedOn;
        }

        public AchievementEntity getAchievementEntity() {
            return achievementEntity;
        }

        public AchievementRankEntity getAchievementRankEntity() {
            return achievementRankEntity;
        }

        public LocalDateTime getAchievedOn() {
            return achievedOn;
        }
    }
}
