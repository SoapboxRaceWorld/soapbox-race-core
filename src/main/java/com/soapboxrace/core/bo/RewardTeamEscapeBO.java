/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.SkillModRewardType;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.TeamEscapeArbitrationPacket;
import org.apache.commons.lang3.RandomUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class RewardTeamEscapeBO extends RewardBO {

    @EJB
    private PersonaDAO personaDao;

    @EJB
    private ParameterBO parameterBO;

    @EJB
    private LegitRaceBO legitRaceBO;

    public Accolades getTeamEscapeAccolades(Long activePersonaId,
                                            TeamEscapeArbitrationPacket teamEscapeArbitrationPacket,
                                            EventSessionEntity eventSessionEntity) {
        int finishReason = teamEscapeArbitrationPacket.getFinishReason();
        if (!legitRaceBO.isLegit(activePersonaId, teamEscapeArbitrationPacket, eventSessionEntity) && finishReason != 22) {
            return new Accolades();
        }

        float bustedCount = teamEscapeArbitrationPacket.getBustedCount();
        bustedCount++;

        EventEntity eventEntity = eventSessionEntity.getEvent();
        PersonaEntity personaEntity = personaDao.findById(activePersonaId);
        RewardVO rewardVO = getRewardVO(personaEntity);

        setBaseReward(personaEntity, eventEntity, teamEscapeArbitrationPacket, rewardVO);
        setRankReward(eventEntity, teamEscapeArbitrationPacket, rewardVO);

        Float bustedBaseRep = rewardVO.getBaseRep() / bustedCount;
        Float bustedBaseCash = rewardVO.getBaseCash() / bustedCount;

        rewardVO.setBaseRep(bustedBaseRep.intValue());
        rewardVO.setBaseCash(bustedBaseCash.intValue());

        setPerfectStartReward(eventEntity, teamEscapeArbitrationPacket.getPerfectStart(), rewardVO);
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

        setTopSpeedReward(eventEntity, teamEscapeArbitrationPacket.getTopSpeed(), rewardVO);
        setSkillMultiplierReward(personaEntity, rewardVO, SkillModRewardType.BOUNTY_HUNTER);
        setMultiplierReward(eventEntity, rewardVO);
        setAmplifierReward(personaEntity, rewardVO);

        teamEscapeArbitrationPacket.setRank(RandomUtils.nextInt(1, 5));
        applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
        return getAccolades(personaEntity, eventEntity, teamEscapeArbitrationPacket, rewardVO);
    }
}
