/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

public enum SkillModRewardType {
    EXPLORER, SOCIALITE, BOUNTY_HUNTER;

    @Override
    public String toString() {
        return super.toString().replace("_", " ");
    }
}
