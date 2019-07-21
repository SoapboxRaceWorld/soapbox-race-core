package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.ProductEntity;

import java.util.List;

public class ItemRewardCash extends ItemRewardBase {
    private final int cash;

    public ItemRewardCash(int cash) {
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }

    @Override
    public List<ProductEntity> getProducts() {
        throw new UnsupportedOperationException();
    }
}
