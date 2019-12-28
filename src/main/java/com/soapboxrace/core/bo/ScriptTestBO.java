/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.bo;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class ScriptTestBO {
    @EJB
    private ItemRewardBO itemRewardBO;

    @PostConstruct
    public void test() {
//        ItemRewardBase irb = itemRewardBO.getGenerator().findWeightedRandomItemByProdType("visualpart");
    }

    public class TestEventResultStructure {
        private int rank;
        private int sumOfJumpsDurationInMilliseconds;

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getSumOfJumpsDurationInMilliseconds() {
            return sumOfJumpsDurationInMilliseconds;
        }

        public void setSumOfJumpsDurationInMilliseconds(int sumOfJumpsDurationInMilliseconds) {
            this.sumOfJumpsDurationInMilliseconds = sumOfJumpsDurationInMilliseconds;
        }
    }
}
