/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa.util;

public enum NewsArticleType {
    NullNews(0),
    FriendRequestReceived(1),
    FriendLeveledUp(2),
    TopSpeedEclipsed(3),
    LongestPursuitEclipsed(4),
    TotalCostToStateEclipsed(5),
    WelcomeMessage(6),
    AutoRejectedFriendRequest(7),
    DynamicNews(9),
    RareItemWonLuckyDraw(10),
    RareItemWonCardPack(11),
    RepAmplifierExpiring(12),
    RepAmplifierExpired(13),
    CashAmplifierExpiring(14),
    CashAmplifierExpired(15),
    CarRentalExpired(16),
    CarInsuranceExpiring(17),
    CarInsuranceExpired(18),
    UnopenedGift(19),
    RareItemWonStreak(20),
    StreakMilestone(21),
    CarClassChanged(22),
    ReferralBoostGiftAwarded(23);

    private final int typeId;

    NewsArticleType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }
}