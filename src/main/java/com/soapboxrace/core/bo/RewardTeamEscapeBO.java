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
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.TeamEscapeArbitrationPacket;
import org.apache.commons.lang3.RandomUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class RewardTeamEscapeBO extends RewardEventBO<TeamEscapeArbitrationPacket> {

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private LegitRaceBO legitRaceBO;

    public Accolades getAccolades(Long activePersonaId,
                                  TeamEscapeArbitrationPacket teamEscapeArbitrationPacket,
                                  EventDataEntity eventDataEntity, EventSessionEntity eventSessionEntity, AchievementTransaction achievementTransaction) {
        int finishReason = teamEscapeArbitrationPacket.getFinishReason();
        boolean legit = legitRaceBO.isLegit(activePersonaId, teamEscapeArbitrationPacket, eventSessionEntity, eventDataEntity);
        eventDataEntity.setLegit(legit);
        if (!legit || finishReason != 22) {
            return new Accolades();
        }

        float bustedCount = teamEscapeArbitrationPacket.getBustedCount();
        bustedCount++;

        PersonaEntity personaEntity = personaDao.find(activePersonaId);
        RewardVO rewardVO = getRewardVO(personaEntity);
        EventRewardEntity eventRewardEntity = getRewardConfiguration(eventSessionEntity);

        setBaseReward(personaEntity, eventDataEntity.getEvent(), eventRewardEntity, teamEscapeArbitrationPacket, rewardVO);
        setRankReward(eventRewardEntity, teamEscapeArbitrationPacket, rewardVO);

        float bustedBaseRep = rewardVO.getBaseRep() / bustedCount;
        float bustedBaseCash = rewardVO.getBaseCash() / bustedCount;

        rewardVO.setBaseRep((int) bustedBaseRep);
        rewardVO.setBaseCash((int) bustedBaseCash);

        setPerfectStartReward(eventRewardEntity, teamEscapeArbitrationPacket.getPerfectStart(), rewardVO);
        setPursitParamReward(teamEscapeArbitrationPacket.getCopsDeployed(), EnumRewardType.COP_CARS_DEPLOYED, rewardVO);
        setPursitParamReward(teamEscapeArbitrationPacket.getCopsDisabled(), EnumRewardType.COP_CARS_DISABLED, rewardVO);
        setPursitParamReward(teamEscapeArbitrationPacket.getCopsRammed(), EnumRewardType.COP_CARS_RAMMED, rewardVO);
        setPursitParamReward(teamEscapeArbitrationPacket.getCostToState(), EnumRewardType.COST_TO_STATE, rewardVO);
        setPursitParamReward(teamEscapeArbitrationPacket.getEventDurationInMilliseconds(),
                EnumRewardType.PURSUIT_LENGTH, rewardVO);
        setPursitParamReward(teamEscapeArbitrationPacket.getInfractions(), EnumRewardType.INFRACTIONS, rewardVO);
        setPursitParamReward(teamEscapeArbitrationPacket.getRoadBlocksDodged(), EnumRewardType.ROADBLOCKS_DODGED,
                rewardVO);
        setPursitParamReward(teamEscapeArbitrationPacket.getSpikeStripsDodged(), EnumRewardType.SPIKE_STRIPS_DODGED,
                rewardVO);

        setTopSpeedReward(eventRewardEntity, teamEscapeArbitrationPacket.getTopSpeed(), rewardVO);
        setSkillMultiplierReward(personaEntity, rewardVO, SkillModRewardType.BOUNTY_HUNTER);
        setMultiplierReward(eventRewardEntity, rewardVO);
        setAmplifierReward(personaEntity, rewardVO);

        teamEscapeArbitrationPacket.setRank(RandomUtils.nextInt(1, 5));
        applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity, true, achievementTransaction);
        return getAccolades(personaEntity, eventRewardEntity, teamEscapeArbitrationPacket, rewardVO);
    }
}
