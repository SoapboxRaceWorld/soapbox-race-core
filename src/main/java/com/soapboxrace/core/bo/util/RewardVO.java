/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

import com.soapboxrace.jaxb.http.ArrayOfRewardPart;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.RewardPart;

import java.io.Serializable;

public class RewardVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -884222055031833226L;
    int baseRep = 0;
    int baseCash = 0;
    int rep = 0;
    int cash = 0;
    final ArrayOfRewardPart arrayOfRewardPart = new ArrayOfRewardPart();
    private final boolean enableEconomy;
    private final boolean enableReputation;

    public RewardVO(boolean enableEconomy, boolean enableReputation) {
        this.enableEconomy = enableEconomy;
        this.enableReputation = enableReputation;
    }

    public int getBaseRep() {
        return baseRep;
    }

    public void setBaseRep(int baseRep) {
        this.baseRep = baseRep;
    }

    public int getBaseCash() {
        return baseCash;
    }

    public void setBaseCash(int baseCash) {
        this.baseCash = baseCash;
    }

    public void add(int rep, int cash, EnumRewardCategory enumRewardCategory, EnumRewardType enumRewardType) {
        if (!enableReputation) {
            rep = 0;
        }
        if (!enableEconomy) {
            cash = 0;
        }
        RewardPart rewardPart = new RewardPart();
        rewardPart.setRepPart(rep);
        rewardPart.setTokenPart(cash);
        rewardPart.setRewardCategory(enumRewardCategory);
        rewardPart.setRewardType(enumRewardType);
        this.rep += rep;
        this.cash += cash;
        arrayOfRewardPart.getRewardPart().add(rewardPart);
    }

    public int getRep() {
        return rep;
    }

    public int getCash() {
        return cash;
    }

    public ArrayOfRewardPart getArrayOfRewardPart() {
        return arrayOfRewardPart;
    }

}
