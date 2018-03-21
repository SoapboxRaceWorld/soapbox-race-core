package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.PursuitArbitrationPacket;

@Stateless
public class RewardPursuitBO {

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private RewardBO rewardBO;

	public Accolades getPursuitAccolades(Long activePersonaId, PursuitArbitrationPacket pursuitArbitrationPacket, Boolean isBusted) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = new RewardVO(parameterBO.getBoolParam("ENABLE_ECONOMY"), parameterBO.getBoolParam("ENABLE_REPUTATION"));

		float rep = 0;
		float cash = 0;

		if (!isBusted) {
			if (personaEntity.getLevel() < 60) {
				rep = 200.0f * (personaEntity.getLevel() / 10.0f);
			}
			if (personaEntity.getCash() < 9999999) {
				cash = 600.0f * (personaEntity.getLevel() / 10.0f);
			}
			rewardVO.add((int) rep, (int) cash, EnumRewardCategory.PURSUIT, EnumRewardType.EVADED);
			float skillCash = cash * rewardBO.getSkillMultiplicater(personaEntity.getPersonaId(), 1);
			rewardVO.add(0, (int) skillCash, EnumRewardCategory.SKILL, EnumRewardType.SKILL_MOD);

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

		}

		float repMult = rewardVO.getRep() * parameterBO.getRepRewardMultiplier();
		float cashMult = rewardVO.getCash() * parameterBO.getCashRewardMultiplier();
		rewardVO.add((int) repMult, 0, EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
		rewardVO.add(0, (int) cashMult, EnumRewardCategory.AMPLIFIER, EnumRewardType.TOKEN_AMPLIFIER);

		Accolades accolades = new Accolades();

		accolades.setFinalRewards(rewardBO.getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setHasLeveledUp(rewardBO.isLeveledUp(personaEntity, rewardVO.getRep()));
		if (!isBusted) {
			accolades.setLuckyDrawInfo(rewardBO.getLuckyDrawInfo(1, personaEntity.getLevel(), personaEntity));
		}
		accolades.setOriginalRewards(rewardBO.getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());

		rewardBO.applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return accolades;
	}
}
