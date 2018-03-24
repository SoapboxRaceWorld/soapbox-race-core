package com.soapboxrace.core.bo;

import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.PursuitArbitrationPacket;

@Stateless
public class RewardPursuitBO extends RewardBO {

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private LegitRaceBO legitRaceBO;

	public Accolades getPursuitAccolades(Long activePersonaId, PursuitArbitrationPacket pursuitArbitrationPacket, EventSessionEntity eventSessionEntity,
			Boolean isBusted) {
		if (!legitRaceBO.isLegit(activePersonaId, pursuitArbitrationPacket, eventSessionEntity) || isBusted) {
			return new Accolades();
		}
		EventEntity eventEntity = eventSessionEntity.getEvent();
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = getRewardVO(personaEntity);

		setBaseReward(personaEntity, eventEntity, pursuitArbitrationPacket, rewardVO);
		float rep = rewardVO.getBaseRep();
		float cash = rewardVO.getBaseCash();

		float copsDeployedExp = rep * (pursuitArbitrationPacket.getCopsDeployed() / 200.0f);
		float copsDeployedCash = cash * (pursuitArbitrationPacket.getCopsDeployed() / 200.0f);
		rewardVO.add((int) copsDeployedExp, (int) copsDeployedCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DEPLOYED);

		float copsDisabledExp = rep * (pursuitArbitrationPacket.getCopsDisabled() / 100.0f);
		float copsDisabledCash = cash * (pursuitArbitrationPacket.getCopsDisabled() / 100.0f);
		rewardVO.add((int) copsDisabledExp, (int) copsDisabledCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DISABLED);

		float copsRammedExp = rep * (pursuitArbitrationPacket.getCopsRammed() / 150.0f);
		float copsRammedCash = cash * (pursuitArbitrationPacket.getCopsRammed() / 150.0f);
		rewardVO.add((int) copsRammedExp, (int) copsRammedCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_RAMMED);

		float costToStateExp = rep * ((pursuitArbitrationPacket.getCostToState() / 5000.0f) / 150.0f);
		float costToStateCash = cash * ((pursuitArbitrationPacket.getCostToState() / 5000.0f) / 150.0f);
		rewardVO.add((int) costToStateExp, (int) costToStateCash, EnumRewardCategory.PURSUIT, EnumRewardType.COST_TO_STATE);

		float eventDurationExp = rep * ((pursuitArbitrationPacket.getEventDurationInMilliseconds() / 2500.0f) / 150.0f);
		float eventDurationCash = cash * ((pursuitArbitrationPacket.getEventDurationInMilliseconds() / 2500.0f) / 150.0f);
		rewardVO.add((int) eventDurationExp, (int) eventDurationCash, EnumRewardCategory.PURSUIT, EnumRewardType.PURSUIT_LENGTH);

		float heatExp = rep * (pursuitArbitrationPacket.getHeat() / 5.0f);
		float heatCash = cash * (pursuitArbitrationPacket.getHeat() / 5.0f);
		rewardVO.add((int) heatExp, (int) heatCash, EnumRewardCategory.PURSUIT, EnumRewardType.HEAT_LEVEL);

		float infractionsExp = rep * (pursuitArbitrationPacket.getInfractions() / 100.0f);
		float infractionsCash = cash * (pursuitArbitrationPacket.getInfractions() / 100.0f);
		rewardVO.add((int) infractionsExp, (int) infractionsCash, EnumRewardCategory.PURSUIT, EnumRewardType.INFRACTIONS);

		float roadBlocksDodgedExp = rep * (pursuitArbitrationPacket.getRoadBlocksDodged() / 50.0f);
		float roadBlocksDodgedCash = cash * (pursuitArbitrationPacket.getRoadBlocksDodged() / 50.0f);
		rewardVO.add((int) roadBlocksDodgedExp, (int) roadBlocksDodgedCash, EnumRewardCategory.PURSUIT, EnumRewardType.ROADBLOCKS_DODGED);

		float spikeStripsDodgedExp = rep * (pursuitArbitrationPacket.getSpikeStripsDodged() / 50.0f);
		float spikeStripsDodgedCash = cash * (pursuitArbitrationPacket.getSpikeStripsDodged() / 50.0f);
		rewardVO.add((int) spikeStripsDodgedExp, (int) spikeStripsDodgedCash, EnumRewardCategory.PURSUIT, EnumRewardType.SPIKE_STRIPS_DODGED);

		setTopSpeedReward(eventEntity, pursuitArbitrationPacket.getTopSpeed(), rewardVO);
		setSkillMultiplierReward(personaEntity, rewardVO);
		setMultiplierReward(eventEntity, rewardVO);

		Random random = new Random();
		pursuitArbitrationPacket.setRank(random.nextInt(4));
		applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return getAccolades(personaEntity, pursuitArbitrationPacket, rewardVO);
	}
}
