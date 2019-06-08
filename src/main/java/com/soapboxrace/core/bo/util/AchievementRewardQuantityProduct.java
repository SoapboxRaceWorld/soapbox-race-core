package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.ProductEntity;

import java.util.List;

public class AchievementRewardQuantityProduct extends AchievementRewardProduct {
    private Integer useCount;

    public AchievementRewardQuantityProduct(ProductEntity productEntity, Integer useCount) {
        super(productEntity);
        this.useCount = useCount;
    }

    @Override
    public List<ProductEntity> getProducts() {
        productEntity.setUseCount(useCount);
        productEntity.setProductTitle(productEntity.getProductTitle() + " x" + useCount);
        return super.getProducts();
    }
}
