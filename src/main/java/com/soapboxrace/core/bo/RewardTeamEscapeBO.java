package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.TeamEscapeArbitrationPacket;

@Stateless
public class RewardTeamEscapeBO {

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private RewardBO rewardBO;

	public Accolades getTeamEscapeAccolades(Long activePersonaId, TeamEscapeArbitrationPacket teamEscapeArbitrationPacket) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = new RewardVO(parameterBO.getBoolParam("ENABLE_ECONOMY"), parameterBO.getBoolParam("ENABLE_REPUTATION"));

		float rep = 0;
		float cash = 0;

		if (personaEntity.getLevel() < 60) {
			rep = 200.0f * ((personaEntity.getLevel() + 1.0f) / 10.0f);
		}
		if (personaEntity.getCash() < 9999999) {
			cash = 600.0f * ((personaEntity.getLevel() + 1.0f) / 10.0f);
		}
		rewardVO.add((int) rep, (int) cash, EnumRewardCategory.BASE, EnumRewardType.NONE);

		Integer bustedCount = teamEscapeArbitrationPacket.getBustedCount();
		Integer finishReason = teamEscapeArbitrationPacket.getFinishReason();

		float skillCash = cash * rewardBO.getSkillMultiplicater(personaEntity.getPersonaId(), 1);
		rewardVO.add(0, (int) skillCash, EnumRewardCategory.SKILL, EnumRewardType.SKILL_MOD);

		float copsDeployedExp = rep * (teamEscapeArbitrationPacket.getCopsDeployed() / 175.0f);
		float copsDeployedCash = cash * (teamEscapeArbitrationPacket.getCopsDeployed() / 175.0f);
		copsDeployedExp = bustedCount > 0 && finishReason == 22 ? copsDeployedExp / (bustedCount * 2.0f)
				: finishReason != 22 ? copsDeployedExp / 10 : copsDeployedExp;
		copsDeployedCash = bustedCount > 0 && finishReason == 22 ? copsDeployedCash / (bustedCount * 2.0f)
				: finishReason != 22 ? copsDeployedCash / 10 : copsDeployedCash;
		rewardVO.add((int) copsDeployedExp, (int) copsDeployedCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DEPLOYED);

		float copsDisabledExp = rep * (teamEscapeArbitrationPacket.getCopsDisabled() / 100.0f);
		float copsDisabledCash = cash * (teamEscapeArbitrationPacket.getCopsDisabled() / 100.0f);
		copsDisabledExp = bustedCount > 0 && finishReason == 22 ? copsDisabledExp / (bustedCount * 2.0f)
				: finishReason != 22 ? copsDisabledExp / 10 : copsDisabledExp;
		copsDisabledCash = bustedCount > 0 && finishReason == 22 ? copsDisabledCash / (bustedCount * 2.0f)
				: finishReason != 22 ? copsDisabledCash / 10 : copsDisabledCash;
		rewardVO.add((int) copsDisabledExp, (int) copsDisabledCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_DISABLED);

		float copsRammedExp = rep * (teamEscapeArbitrationPacket.getCopsRammed() / 150.0f);
		float copsRammedCash = cash * (teamEscapeArbitrationPacket.getCopsRammed() / 150.0f);
		copsRammedExp = bustedCount > 0 && finishReason == 22 ? copsRammedExp / (bustedCount * 2.0f) : finishReason != 22 ? copsRammedExp / 10 : copsRammedExp;
		copsRammedCash = bustedCount > 0 && finishReason == 22 ? copsRammedCash / (bustedCount * 2.0f)
				: finishReason != 22 ? copsRammedCash / 10 : copsRammedCash;
		rewardVO.add((int) copsRammedExp, (int) copsRammedCash, EnumRewardCategory.PURSUIT, EnumRewardType.COP_CARS_RAMMED);

		float costToStateExp = rep * ((teamEscapeArbitrationPacket.getCostToState() / 2500.0f) / 100.0f);
		float costToStateCash = cash * ((teamEscapeArbitrationPacket.getCostToState() / 2500.0f) / 100.0f);
		costToStateExp = bustedCount > 0 && finishReason == 22 ? costToStateExp / (bustedCount * 2.0f)
				: finishReason != 22 ? costToStateExp / 10 : costToStateExp;
		costToStateCash = bustedCount > 0 && finishReason == 22 ? costToStateCash / (bustedCount * 2.0f)
				: finishReason != 22 ? costToStateCash / 10 : costToStateCash;
		rewardVO.add((int) costToStateExp, (int) costToStateCash, EnumRewardCategory.PURSUIT, EnumRewardType.COST_TO_STATE);

		float infractionsExp = rep * (teamEscapeArbitrationPacket.getInfractions() / 100.0f);
		float infractionsCash = cash * (teamEscapeArbitrationPacket.getInfractions() / 100.0f);
		infractionsExp = bustedCount > 0 && finishReason == 22 ? infractionsExp / (bustedCount * 2.0f)
				: finishReason != 22 ? infractionsExp / 10 : infractionsExp;
		infractionsCash = bustedCount > 0 && finishReason == 22 ? infractionsCash / (bustedCount * 2.0f)
				: finishReason != 22 ? infractionsCash / 10 : infractionsCash;
		rewardVO.add((int) infractionsExp, (int) infractionsCash, EnumRewardCategory.PURSUIT, EnumRewardType.INFRACTIONS);

		float roadBlocksDodgedExp = rep * (teamEscapeArbitrationPacket.getRoadBlocksDodged() / 50.0f);
		float roadBlocksDodgedCash = cash * (teamEscapeArbitrationPacket.getRoadBlocksDodged() / 50.0f);
		roadBlocksDodgedExp = bustedCount > 0 && finishReason == 22 ? roadBlocksDodgedExp / (bustedCount * 2.0f)
				: finishReason != 22 ? roadBlocksDodgedExp / 10 : roadBlocksDodgedExp;
		roadBlocksDodgedCash = bustedCount > 0 && finishReason == 22 ? roadBlocksDodgedCash / (bustedCount * 2.0f)
				: finishReason != 22 ? roadBlocksDodgedCash / 10 : roadBlocksDodgedCash;
		rewardVO.add((int) roadBlocksDodgedExp, (int) roadBlocksDodgedCash, EnumRewardCategory.PURSUIT, EnumRewardType.ROADBLOCKS_DODGED);

		float spikeStripsDodgedExp = rep * (teamEscapeArbitrationPacket.getSpikeStripsDodged() / 50.0f);
		float spikeStripsDodgedCash = cash * (teamEscapeArbitrationPacket.getSpikeStripsDodged() / 50.0f);
		spikeStripsDodgedExp = bustedCount > 0 && finishReason == 22 ? spikeStripsDodgedExp / (bustedCount * 2.0f)
				: finishReason != 22 ? spikeStripsDodgedExp / 10 : spikeStripsDodgedExp;
		spikeStripsDodgedCash = bustedCount > 0 && finishReason == 22 ? spikeStripsDodgedCash / (bustedCount * 2.0f)
				: finishReason != 22 ? spikeStripsDodgedCash / 10 : spikeStripsDodgedCash;
		rewardVO.add((int) spikeStripsDodgedExp, (int) spikeStripsDodgedCash, EnumRewardCategory.PURSUIT, EnumRewardType.SPIKE_STRIPS_DODGED);

		float rankExp = teamEscapeArbitrationPacket.getRank() == 1 ? rep * 0.10f : rep * 0.05f; // + 10% if fist, + 5% else
		float rankCash = teamEscapeArbitrationPacket.getRank() == 1 ? cash * 0.10f : cash * 0.05f; // + 10% if fist, + 5% else
		rankExp = bustedCount > 0 && finishReason == 22 ? rankExp / (bustedCount * 2.0f) : finishReason != 22 ? rankExp / 10 : rankExp;
		rankCash = bustedCount > 0 && finishReason == 22 ? rankCash / (bustedCount * 2.0f) : finishReason != 22 ? rankCash / 10 : rankCash;
		rewardVO.add((int) rankExp, (int) rankCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);

		if (teamEscapeArbitrationPacket.getPerfectStart() == 1) {
			float perfectStartExp = rep * 0.10f; // + 10%
			float perfectStartCash = cash * 0.10f; // + 10%
			perfectStartExp = bustedCount > 0 && finishReason == 22 ? perfectStartExp / (bustedCount * 2.0f)
					: finishReason != 22 ? perfectStartExp / 10 : perfectStartExp;
			perfectStartCash = bustedCount > 0 && finishReason == 22 ? perfectStartCash / (bustedCount * 2.0f)
					: finishReason != 22 ? perfectStartCash / 10 : perfectStartCash;
			rewardVO.add((int) perfectStartExp, (int) perfectStartCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}

		if (teamEscapeArbitrationPacket.getTopSpeed() >= 70.0f) {
			float highSpeedExp = rep * 0.10f; // + 10%
			float highSpeedCash = cash * 0.10f; // + 10%
			highSpeedExp = bustedCount > 0 && finishReason == 22 ? highSpeedExp / (bustedCount * 2.0f) : finishReason != 22 ? highSpeedExp / 10 : highSpeedExp;
			highSpeedCash = bustedCount > 0 && finishReason == 22 ? highSpeedCash / (bustedCount * 2.0f)
					: finishReason != 22 ? highSpeedCash / 10 : highSpeedCash;
			rewardVO.add((int) highSpeedExp, (int) highSpeedCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}

		float repMult = rewardVO.getRep() * parameterBO.getRepRewardMultiplier();
		float cashMult = rewardVO.getCash() * parameterBO.getCashRewardMultiplier();
		rewardVO.add((int) repMult, 0, EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
		rewardVO.add(0, (int) cashMult, EnumRewardCategory.AMPLIFIER, EnumRewardType.TOKEN_AMPLIFIER);

		Accolades accolades = new Accolades();
		accolades.setFinalRewards(rewardBO.getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setHasLeveledUp(rewardBO.isLeveledUp(personaEntity, rewardVO.getRep()));
		if (teamEscapeArbitrationPacket.getFinishReason() == 22) {
			accolades.setLuckyDrawInfo(rewardBO.getLuckyDrawInfo(teamEscapeArbitrationPacket.getRank(), personaEntity.getLevel(), personaEntity));
		}
		accolades.setOriginalRewards(rewardBO.getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());

		rewardBO.applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return accolades;
	}
}
