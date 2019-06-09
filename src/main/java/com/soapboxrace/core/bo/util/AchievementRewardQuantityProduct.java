package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.ProductEntity;

import java.util.List;

public class AchievementRewardQuantityProduct extends AchievementRewardProduct {
    private Integer useCount;

    public AchievementRewardQuantityProduct(ProductEntity productEntity, Integer useCount) {
        super(productEntity);
        this.useCount = useCount;
    }

    public Integer getUseCount() {
        return useCount;
    }
}
