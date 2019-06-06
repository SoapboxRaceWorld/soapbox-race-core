package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.ProductEntity;

import java.util.List;

public abstract class AchievementRewardBase {
    public abstract List<ProductEntity> getProducts();
}
