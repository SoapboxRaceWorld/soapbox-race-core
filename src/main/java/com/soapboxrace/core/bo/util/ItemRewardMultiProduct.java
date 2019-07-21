package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.ProductEntity;

import java.util.Collections;
import java.util.List;

public class ItemRewardMultiProduct extends ItemRewardBase {
    private final List<ProductEntity> productEntities;

    public ItemRewardMultiProduct(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }

    @Override
    public List<ProductEntity> getProducts() {
        return Collections.synchronizedList(productEntities);
    }
}
