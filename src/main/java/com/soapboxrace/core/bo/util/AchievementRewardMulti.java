package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AchievementRewardMulti extends AchievementRewardBase {
    private final List<AchievementRewardBase> achievementRewardList;

    public AchievementRewardMulti(List<AchievementRewardBase> achievementRewardList) {
        this.achievementRewardList = achievementRewardList;
    }

    public List<AchievementRewardBase> getAchievementRewardList() {
        return achievementRewardList;
    }

    @Override
    public List<ProductEntity> getProducts() {
        return achievementRewardList.stream().flatMap(a -> a.getProducts().stream()).collect(Collectors.toList());
    }
}
