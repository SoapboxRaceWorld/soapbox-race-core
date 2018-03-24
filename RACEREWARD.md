event reward parameters:

 - legitTime
 - baseCashReward
 - baseRepReward
 - finalCashRewardMultiplier
 - finalRepRewardMultiplier
 - levelCashRewardMultiplier
 - levelRepRewardMultiplier
 - minTopSpeedTrigger
 - perfectStartCashMultiplier
 - perfectStartRepMultiplier
 - rank1CashMultiplier
 - rank1RepMultiplier
 - rank2CashMultiplier
 - rank2RepMultiplier
 - rank3CashMultiplier
 - rank3RepMultiplier
 - rank4CashMultiplier
 - rank4RepMultiplier
 - rank5CashMultiplier
 - rank5RepMultiplier
 - rank6CashMultiplier
 - rank6RepMultiplier
 - rank7CashMultiplier
 - rank7RepMultiplier
 - rank8CashMultiplier
 - rank8RepMultiplier
 - topSpeedCashMultiplier
 - topSpeedRepMultiplier

the math:

	
setBaseReward

    timeConst = legitTime / raceTime (max 1)
    playerLevelRepConst = playerLevel * levelRepRewardMultiplier
    playerLevelCashConst = playerLevel * levelCashRewardMultiplier
    
    baseRep = baseCashReward * playerLevelRepConst * timeConst
    baseCash = baseCashReward * playerLevelCashConst * timeConst


setRankReward

    rankRepMultiplier = rank1RepMultiplier or rank2RepMultiplier... or rank8RepMultiplier
    rankCashMultiplier = rank1CashMultiplier or rank2CashMultiplier... or rank8CashMultiplier
    
    rankRep = baseRep * rankRepMultiplier
    rankCash = baseRep * rankCashMultiplier
    
setPerfectStartReward (if perfectStart == true)

    perfectStartRep = baseRep * perfectStartRepMultiplier
    perfectStartCash = baseRep * perfectStartCashMultiplier
    
setTopSpeedReward (if topSpeed >= minTopSpeedTrigger)

    topSpeedRep = baseRep * topSpeedRepMultiplier;
    topSpeedCash = baseCash * topSpeedCashMultiplier;

setMultiplierReward

    finalRep = repSum * finalRepRewardMultiplier
    finalCash = cashSum * finalCashRewardMultiplier

