/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.CarClassesEntity;

public class AchievementCommerceContext {
    private final CarClassesEntity carClassesEntity;
    private final CommerceType commerceType;
    private final int commerceTypeVal;

    public AchievementCommerceContext(CarClassesEntity carClassesEntity, CommerceType commerceType) {
        this.carClassesEntity = carClassesEntity;
        this.commerceType = commerceType;
        this.commerceTypeVal = commerceType.ordinal();
    }

    public CarClassesEntity getCarClassesEntity() {
        return carClassesEntity;
    }

    public CommerceType getCommerceType() {
        return commerceType;
    }

    public int getCommerceTypeVal() {
        return commerceTypeVal;
    }

    public enum CommerceType {
        CAR_PURCHASE, GENERIC_COMMERCE
    }
}
