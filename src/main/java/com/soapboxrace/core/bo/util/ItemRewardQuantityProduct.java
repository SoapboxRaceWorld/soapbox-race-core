package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.ProductEntity;

public class ItemRewardQuantityProduct extends ItemRewardProduct {
    private Integer useCount;

    public ItemRewardQuantityProduct(ProductEntity productEntity, Integer useCount) {
        super(productEntity);
        this.useCount = useCount;
    }

    public Integer getUseCount() {
        return useCount;
    }
}
