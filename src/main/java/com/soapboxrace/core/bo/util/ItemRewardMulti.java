package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ItemRewardMulti extends ItemRewardBase {
    private final List<ItemRewardBase> achievementRewardList;

    public ItemRewardMulti(List<ItemRewardBase> achievementRewardList) {
        this.achievementRewardList = achievementRewardList;
    }

    public List<ItemRewardBase> getAchievementRewardList() {
        return achievementRewardList;
    }

    @Override
    public List<ProductEntity> getProducts() {
        return achievementRewardList.stream().flatMap(a -> a.getProducts().stream()).collect(Collectors.toList());
    }
}
