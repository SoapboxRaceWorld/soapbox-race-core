/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.DragArbitrationPacket;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class RewardDragBO extends RewardEventBO<DragArbitrationPacket> {

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private LegitRaceBO legitRaceBO;

    @Override
    public Accolades getAccolades(Long activePersonaId, DragArbitrationPacket dragArbitrationPacket,
                                  EventDataEntity eventDataEntity, EventSessionEntity eventSessionEntity, AchievementTransaction achievementTransaction) {
        int finishReason = dragArbitrationPacket.getFinishReason();
        boolean legit = legitRaceBO.isLegit(activePersonaId, dragArbitrationPacket, eventSessionEntity, eventDataEntity);
        eventDataEntity.setLegit(legit);
        if (!legit || finishReason != 22) {
            return new Accolades();
        }
        PersonaEntity personaEntity = personaDao.find(activePersonaId);
        RewardVO rewardVO = getRewardVO(personaEntity);
        EventRewardEntity eventRewardEntity = getRewardConfiguration(eventSessionEntity);

        setBaseReward(personaEntity, eventSessionEntity.getEvent(), eventRewardEntity, dragArbitrationPacket, rewardVO);
        setRankReward(eventRewardEntity, dragArbitrationPacket, rewardVO);
        setPerfectStartReward(eventRewardEntity, dragArbitrationPacket.getPerfectStart(), rewardVO);
        setTopSpeedReward(eventRewardEntity, dragArbitrationPacket.getTopSpeed(), rewardVO);
        setSkillMultiplierReward(personaEntity, rewardVO, SkillModRewardType.SOCIALITE);
        setMultiplierReward(eventRewardEntity, rewardVO);
        setAmplifierReward(personaEntity, rewardVO);

        applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity, true, achievementTransaction);
        return getAccolades(personaEntity, eventRewardEntity, dragArbitrationPacket, rewardVO);
    }
}
