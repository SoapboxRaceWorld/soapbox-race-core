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
public class RewardDragBO extends RewardBO {

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private LegitRaceBO legitRaceBO;

    public Accolades getDragAccolades(Long activePersonaId, DragArbitrationPacket dragArbitrationPacket,
                                      EventDataEntity eventDataEntity, EventSessionEntity eventSessionEntity) {
        int finishReason = dragArbitrationPacket.getFinishReason();
        boolean legit = legitRaceBO.isLegit(activePersonaId, dragArbitrationPacket, eventSessionEntity);
        eventDataEntity.setLegit(legit);
        if (!legit || finishReason != 22) {
            return new Accolades();
        }
        EventEntity eventEntity = eventSessionEntity.getEvent();
        PersonaEntity personaEntity = personaDao.findById(activePersonaId);
        RewardVO rewardVO = getRewardVO(personaEntity);

        setBaseReward(personaEntity, eventEntity, dragArbitrationPacket, rewardVO);
        setRankReward(eventEntity, dragArbitrationPacket, rewardVO);
        setPerfectStartReward(eventEntity, dragArbitrationPacket.getPerfectStart(), rewardVO);
        setTopSpeedReward(eventEntity, dragArbitrationPacket.getTopSpeed(), rewardVO);
        setSkillMultiplierReward(personaEntity, rewardVO, SkillModRewardType.SOCIALITE);
        setMultiplierReward(eventEntity, rewardVO);
        setAmplifierReward(personaEntity, rewardVO);

        applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
        return getAccolades(personaEntity, eventEntity, dragArbitrationPacket, rewardVO);
    }
}
