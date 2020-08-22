/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

public enum CardDecks {
    GoldCar("LD_CARD_SPECIAL_GOLD"),
    Gold("LD_CARD_GOLD"),
    Silver("LD_CARD_SILVER"),
    Bronze("LD_CARD_BRONZE"),
    Blue("LD_CARD_BLUE");

    private final String cardDeckType;

    CardDecks(String cardDeckType) {
        this.cardDeckType = cardDeckType;
    }

    public static String forRank(Integer rank) {
        switch (rank) {
            case 0:
                return GoldCar.getCardDeckType();
            case 1:
                return Gold.getCardDeckType();
            case 2:
                return Silver.getCardDeckType();
            case 3:
                return Bronze.getCardDeckType();
            default:
                return Blue.getCardDeckType();
        }
    }

    public String getCardDeckType() {
        return cardDeckType;
    }
}