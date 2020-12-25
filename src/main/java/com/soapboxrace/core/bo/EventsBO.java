/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.AchievementProgressionContext;
import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.TreasureHuntConfigDAO;
import com.soapboxrace.core.dao.TreasureHuntDAO;
import com.soapboxrace.core.engine.EngineException;
import com.soapboxrace.core.engine.EngineExceptionCode;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.SkillModRewardType;
import com.soapboxrace.core.jpa.TreasureHuntConfigEntity;
import com.soapboxrace.core.jpa.TreasureHuntEntity;
import com.soapboxrace.jaxb.http.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;

@Stateless
public class EventsBO {

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private TreasureHuntDAO treasureHuntDao;

    @EJB
    private DriverPersonaBO driverPersonaBo;

    @EJB
    private RewardBO rewardBO;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private AchievementBO achievementBO;

    @EJB
    private TreasureHuntConfigDAO treasureHuntConfigDAO;

    public TreasureHuntEventSession getTreasureHuntEventSession(Long activePersonaId) {
        TreasureHuntEntity treasureHuntEntity = treasureHuntDao.find(activePersonaId);
        if (treasureHuntEntity == null) {
            driverPersonaBo.createThInformation(personaDao.find(activePersonaId));
            return getTreasureHuntEventSession(activePersonaId);
        }

        LocalDate thDate = treasureHuntEntity.getThDate();
        LocalDate nowDate = LocalDate.now();

        if (!thDate.equals(nowDate)) {
            return createNewTreasureHunt(treasureHuntEntity, isStreakBroken(treasureHuntEntity));
        }

        return getTreasureHuntEventSession(treasureHuntEntity);
    }

    private boolean isStreakBroken(TreasureHuntEntity treasureHuntEntity) {
        LocalDate thDate = treasureHuntEntity.getThDate();
        LocalDate nowDate = LocalDate.now();

        if (!thDate.equals(nowDate)) {
            int days = (int) ChronoUnit.DAYS.between(thDate, nowDate);

            return days >= 2 || treasureHuntEntity.getCoinsCollected() != 32767;
        }

        return false;
    }

    private TreasureHuntEventSession getTreasureHuntEventSession(TreasureHuntEntity treasureHuntEntity) {
        TreasureHuntEventSession treasureHuntEventSession = new TreasureHuntEventSession();
        treasureHuntEventSession.setCoinsCollected(treasureHuntEntity.getCoinsCollected());
        treasureHuntEventSession.setIsStreakBroken(treasureHuntEntity.getIsStreakBroken());
        treasureHuntEventSession.setNumCoins(treasureHuntEntity.getNumCoins());
        treasureHuntEventSession.setSeed(treasureHuntEntity.getSeed());
        treasureHuntEventSession.setStreak(treasureHuntEntity.getStreak());
        return treasureHuntEventSession;
    }

    public Accolades notifyCoinCollected(Long activePersonaId, Integer coins) {
        TreasureHuntEntity treasureHuntEntity = treasureHuntDao.find(activePersonaId);
        if (treasureHuntEntity != null) {
            if (treasureHuntEntity.getCoinsCollected() == 32767) {
                throw new EngineException("TH is not ready", EngineExceptionCode.SecurityKickedArbitration, true);
            }

            int difference = coins - treasureHuntEntity.getCoinsCollected();

            // coin difference should be exactly 1 (or 0)
            if ((difference & (difference - 1)) != 0) {
                throw new EngineException("Invalid coins", EngineExceptionCode.SecurityKickedArbitration, true);
            }

            treasureHuntEntity.setCoinsCollected(coins);
            treasureHuntDao.update(treasureHuntEntity);

            return coins == 32767 ? accolades(activePersonaId, true) : null;
        }

        return null;
    }

    public Accolades accolades(Long activePersonaId, boolean isCoinCollected) {
        /*
            active streak -> you get rewards for your streak, no need to revive
            broken streak ->
                choose to revive -> you get rewards for your streak, back to green
                don't revive -> reset to day 1 + get day 1 rewards, back to green
         */

        PersonaEntity personaEntity = personaDao.find(activePersonaId);
        TreasureHuntEntity treasureHuntEntity = treasureHuntDao.find(activePersonaId);

        if (treasureHuntEntity == null) {
            throw new EngineException("TreasureHunt does not exist for driver: " + activePersonaId, EngineExceptionCode.InvalidOperation, true);
        }

        if (treasureHuntEntity.getCoinsCollected() != 32767 || treasureHuntEntity.getCompleted()) {
            throw new EngineException("TH is not ready", EngineExceptionCode.SecurityKickedArbitration, true);
        }

        if (!treasureHuntEntity.getIsStreakBroken()) {
            // active streak -> you get rewards for your streak, no need to revive
            AchievementTransaction transaction = achievementBO.createTransaction(activePersonaId);
            Integer originalStreak = treasureHuntEntity.getStreak();
            Accolades accolades = getTreasureHuntAccolades(activePersonaId, treasureHuntEntity, transaction, true);
            treasureHuntEntity.setSeed(new Random().nextInt());
            treasureHuntEntity.setIsStreakBroken(false);
            treasureHuntEntity.setStreak(treasureHuntEntity.getStreak() + 1);
            treasureHuntEntity.setThDate(LocalDate.now());
            treasureHuntEntity.setCompleted(true);
            treasureHuntDao.update(treasureHuntEntity);
            updateTreasureHuntStreak(personaEntity, treasureHuntEntity, transaction, !treasureHuntEntity.getStreak().equals(originalStreak));
            achievementBO.commitTransaction(personaEntity, transaction);
            return accolades;
        }

        // don't revive -> reset to day 1 + get day 1 rewards, back to green

        if (isCoinCollected) {
            // we don't want to give the driver a reward just yet
            return getTreasureHuntAccolades(activePersonaId, treasureHuntEntity, null, false);
        }

        // if we get here, the driver hasn't revived their streak
        treasureHuntEntity.setIsStreakBroken(false);
        treasureHuntEntity.setStreak(1);
        treasureHuntEntity.setThDate(LocalDate.now());
        treasureHuntEntity.setCompleted(true);
        treasureHuntDao.update(treasureHuntEntity);
        AchievementTransaction transaction = achievementBO.createTransaction(activePersonaId);
        updateTreasureHuntStreak(personaEntity, treasureHuntEntity, transaction, true);
        Accolades accolades = getTreasureHuntAccolades(activePersonaId, treasureHuntEntity, transaction, true);
        achievementBO.commitTransaction(personaEntity, transaction);
        return accolades;
    }

    private TreasureHuntEventSession createNewTreasureHunt(TreasureHuntEntity treasureHuntEntity, Boolean isBroken) {
        treasureHuntEntity.setCoinsCollected(0);
        treasureHuntEntity.setIsStreakBroken(isBroken);
        treasureHuntEntity.setSeed(new Random().nextInt());
        treasureHuntEntity.setThDate(LocalDate.now());
        treasureHuntEntity.setCompleted(false);
        treasureHuntDao.update(treasureHuntEntity);

        return getTreasureHuntEventSession(treasureHuntEntity);
    }

    private Accolades getTreasureHuntAccolades(Long activePersonaId, TreasureHuntEntity treasureHuntEntity, AchievementTransaction achievementTransaction, boolean giveReward) {
        PersonaEntity personaEntity = personaDao.find(activePersonaId);
        TreasureHuntConfigEntity treasureHuntConfigEntity =
                treasureHuntConfigDAO.findForStreak(treasureHuntEntity.getStreak());
        RewardVO rewardVO = rewardBO.getRewardVO(personaEntity);

        float baseRepTh = treasureHuntConfigEntity.getBaseRep();
        float baseCashTh = treasureHuntConfigEntity.getBaseCash();

        float playerLevelCashConst = rewardBO.getPlayerLevelConst(personaEntity.getLevel() + parameterBO.getIntParam("REWARD_CASH_BASELINE_LEVEL", 0),
                baseCashTh);
        float playerLevelRepConst = rewardBO.getPlayerLevelConst(personaEntity.getLevel() + parameterBO.getIntParam("REWARD_REP_BASELINE_LEVEL", 0),
                baseRepTh);

        float repThMultiplier = parameterBO.getFloatParam("TH_REP_MULTIPLIER");
        float cashThMultiplier = parameterBO.getFloatParam("TH_CASH_MULTIPLIER");

        float globalRepMultiplier = parameterBO.getFloatParam("REP_REWARD_MULTIPLIER", 1.0f);
        float globalCashMultiplier = parameterBO.getFloatParam("CASH_REWARD_MULTIPLIER", 1.0f);

        float baseRep = playerLevelRepConst * repThMultiplier * globalRepMultiplier;
        float baseCash = playerLevelCashConst * cashThMultiplier * globalCashMultiplier;

        float repDayMultiplier = treasureHuntConfigEntity.getRepMultiplier();
        float cashDayMultiplier = treasureHuntConfigEntity.getCashMultiplier();

        float dayRep = baseRep + (repDayMultiplier * treasureHuntEntity.getStreak());
        float dayCash = baseCash + (cashDayMultiplier * treasureHuntEntity.getStreak());

        ArbitrationPacket arbitrationPacket = new ArbitrationPacket();
        arbitrationPacket.setRank(1);
        if (!treasureHuntEntity.getIsStreakBroken() && giveReward) {
            rewardVO.setBaseRep((int) baseRep);
            rewardVO.setBaseCash((int) baseCash);
            rewardVO.add((int) dayRep, (int) dayCash, EnumRewardCategory.BASE, EnumRewardType.NONE);
            rewardBO.setSkillMultiplierReward(personaEntity, rewardVO, SkillModRewardType.EXPLORER);
            rewardBO.setAmplifierReward(personaEntity, rewardVO);

            rewardBO.applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity, false, achievementTransaction);
        }

        return rewardBO.getAccolades(personaEntity, treasureHuntEntity, treasureHuntConfigEntity,
                rewardVO, giveReward);
    }

    private void updateTreasureHuntStreak(PersonaEntity personaEntity, TreasureHuntEntity treasureHuntEntity, AchievementTransaction achievementTransaction, boolean streakChanged) {
        AchievementProgressionContext progressionContext = new AchievementProgressionContext(0, 0,
                personaEntity.getLevel(), personaEntity.getScore(), treasureHuntEntity.getStreak(), false, false, streakChanged, false);

        achievementTransaction.add("PROGRESSION", Map.of("persona", personaEntity, "progression", progressionContext));
    }
}