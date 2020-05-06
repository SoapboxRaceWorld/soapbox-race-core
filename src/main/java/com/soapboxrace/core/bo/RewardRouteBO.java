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
import com.soapboxrace.jaxb.http.RouteArbitrationPacket;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class RewardRouteBO extends RewardBO {

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private LegitRaceBO legitRaceBO;

    public Accolades getRouteAccolades(Long activePersonaId, RouteArbitrationPacket routeArbitrationPacket,
                                       EventDataEntity eventDataEntity, EventSessionEntity eventSessionEntity, AchievementTransaction achievementTransaction) {
        int finishReason = routeArbitrationPacket.getFinishReason();
        boolean legit = legitRaceBO.isLegit(activePersonaId, routeArbitrationPacket, eventSessionEntity);
        eventDataEntity.setLegit(legit);
        if (!legit || finishReason != 22) {
            return new Accolades();
        }
        EventEntity eventEntity = eventSessionEntity.getEvent();
        PersonaEntity personaEntity = personaDao.findById(activePersonaId);
        RewardVO rewardVO = getRewardVO(personaEntity);

        setBaseReward(personaEntity, eventEntity, routeArbitrationPacket, rewardVO);
        setRankReward(eventEntity, routeArbitrationPacket, rewardVO);
        setPerfectStartReward(eventEntity, routeArbitrationPacket.getPerfectStart(), rewardVO);
        setTopSpeedReward(eventEntity, routeArbitrationPacket.getTopSpeed(), rewardVO);
        setSkillMultiplierReward(personaEntity, rewardVO, SkillModRewardType.SOCIALITE);
        setMultiplierReward(eventEntity, rewardVO);
        setAmplifierReward(personaEntity, rewardVO);

        applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity, true, achievementTransaction);
        return getAccolades(personaEntity, eventEntity, routeArbitrationPacket, rewardVO);
    }

}
