/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

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
    private TreasureHuntConfigDAO treasureHuntConfigDAO;

    public TreasureHuntEventSession getTreasureHuntEventSession(Long activePersonaId) {
        TreasureHuntEntity treasureHuntEntity = treasureHuntDao.findById(activePersonaId);
        if (treasureHuntEntity == null) {
            driverPersonaBo.createThInformation(personaDao.findById(activePersonaId));
            return getTreasureHuntEventSession(activePersonaId);
        }

        LocalDate thDate = treasureHuntEntity.getThDate();
        LocalDate nowDate = LocalDate.now();
        
        if (!thDate.equals(nowDate)) {
            int days = (int) ChronoUnit.DAYS.between(thDate, nowDate);
            if (days >= 2 || treasureHuntEntity.getCoinsCollected() != 32767) {
                return createNewTreasureHunt(treasureHuntEntity, true);
            } else {
                return createNewTreasureHunt(treasureHuntEntity, false);
            }
        }

        return getTreasureHuntEventSession(treasureHuntEntity);
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
        TreasureHuntEntity treasureHuntEntity = treasureHuntDao.findById(activePersonaId);
        if (treasureHuntEntity != null) {
            if (treasureHuntEntity.getCoinsCollected() == 32767) {
                throw new EngineException("TH is not ready", EngineExceptionCode.SecurityKickedArbitration);
            }

            int difference = coins - treasureHuntEntity.getCoinsCollected();

            // coin difference should be exactly 1 (or 0)
            if ((difference & (difference - 1)) != 0) {
                throw new EngineException("Invalid coins", EngineExceptionCode.SecurityKickedArbitration);
            }

            treasureHuntEntity.setCoinsCollected(coins);
            treasureHuntDao.update(treasureHuntEntity);

            return coins == 32767 ? accolades(activePersonaId, false) : null;
        }

        return null;
    }

    public Accolades accolades(Long activePersonaId, Boolean isBroken) {
        TreasureHuntEntity treasureHuntEntity = treasureHuntDao.findById(activePersonaId);

        if (isBroken) {
            treasureHuntEntity.setStreak(1);
            treasureHuntEntity.setIsStreakBroken(false);
        } else {
            treasureHuntEntity.setStreak(treasureHuntEntity.getStreak() + 1);
        }

        treasureHuntEntity.setThDate(LocalDate.now());
        treasureHuntDao.update(treasureHuntEntity);

        return getTreasureHuntAccolades(activePersonaId, treasureHuntEntity);
    }

    private TreasureHuntEventSession createNewTreasureHunt(TreasureHuntEntity treasureHuntEntity, Boolean isBroken) {
        treasureHuntEntity.setCoinsCollected(0);
        treasureHuntEntity.setIsStreakBroken(isBroken);
        treasureHuntEntity.setSeed(new Random().nextInt());
        treasureHuntEntity.setThDate(LocalDate.now());
        treasureHuntDao.update(treasureHuntEntity);

        return getTreasureHuntEventSession(treasureHuntEntity);
    }

    private Accolades getTreasureHuntAccolades(Long activePersonaId, TreasureHuntEntity treasureHuntEntity) {
        PersonaEntity personaEntity = personaDao.findById(activePersonaId);
        TreasureHuntConfigEntity treasureHuntConfigEntity =
                treasureHuntConfigDAO.findForStreak(treasureHuntEntity.getStreak());
        RewardVO rewardVO = rewardBO.getRewardVO(personaEntity);

        float baseRepTh = treasureHuntConfigEntity.getBaseRep();
        float baseCashTh = treasureHuntConfigEntity.getBaseCash();

        float playerLevelRepConst = rewardBO.getPlayerLevelConst(personaEntity.getLevel(), baseRepTh);
        float playerLevelCashConst = rewardBO.getPlayerLevelConst(personaEntity.getLevel(), baseCashTh);

        float repThMultiplier = parameterBO.getFloatParam("TH_REP_MULTIPLIER");
        float cashThMultiplier = parameterBO.getFloatParam("TH_CASH_MULTIPLIER");

        float baseRep = playerLevelRepConst * repThMultiplier;
        float baseCash = playerLevelCashConst * cashThMultiplier;

        rewardVO.setBaseRep((int) baseRep);
        rewardVO.setBaseCash((int) baseCash);

        float repDayMultiplier = treasureHuntConfigEntity.getRepMultiplier();
        float cashDayMultiplier = treasureHuntConfigEntity.getCashMultiplier();

        float dayRep = baseRep + (repDayMultiplier * treasureHuntEntity.getStreak());
        float dayCash = baseCash + (cashDayMultiplier * treasureHuntEntity.getStreak());

        rewardVO.add((int) dayRep, (int) dayCash, EnumRewardCategory.BASE, EnumRewardType.NONE);
        rewardBO.setSkillMultiplierReward(personaEntity, rewardVO, SkillModRewardType.EXPLORER);
        rewardBO.setAmplifierReward(personaEntity, rewardVO);

        ArbitrationPacket arbitrationPacket = new ArbitrationPacket();
        arbitrationPacket.setRank(1);
        if (!treasureHuntEntity.getIsStreakBroken()) {
            rewardBO.applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity, false);
        }

        return rewardBO.getAccolades(personaEntity, treasureHuntEntity, treasureHuntConfigEntity,
                rewardVO);
    }
}