/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

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
