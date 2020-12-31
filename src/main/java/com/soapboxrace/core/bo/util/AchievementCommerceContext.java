/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.CarClassesEntity;
import com.soapboxrace.core.jpa.ProductEntity;

public class AchievementCommerceContext {
    private CarClassesEntity carClassesEntity;
    private ProductEntity productEntity;
    private final CommerceType commerceType;

    public AchievementCommerceContext(CommerceType commerceType) {
        this.commerceType = commerceType;
    }

    public CarClassesEntity getCarClassesEntity() {
        return carClassesEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public CommerceType getCommerceType() {
        return commerceType;
    }

    public int getCommerceTypeVal() {
        return commerceType.ordinal();
    }

    public void setCarClassesEntity(CarClassesEntity carClassesEntity) {
        this.carClassesEntity = carClassesEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public enum CommerceType {
        CAR_PURCHASE, BUNDLE_PURCHASE
    }
}
