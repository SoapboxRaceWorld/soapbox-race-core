package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.RouteArbitrationPacket;

@Stateless
public class RewardRouteBO {

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private RewardBO rewardBO;

	public Accolades getRouteAccolades(Long activePersonaId, RouteArbitrationPacket routeArbitrationPacket) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = new RewardVO(parameterBO.getBoolParam("ENABLE_ECONOMY"), parameterBO.getBoolParam("ENABLE_REPUTATION"));

		float rep = 0;
		float cash = 0;

		if (personaEntity.getLevel() < 60) {
			rep = 200 * ((personaEntity.getLevel() + 1.0f) / 5.0f);
		}
		if (personaEntity.getCash() < 9999999) {
			cash = 600 * ((personaEntity.getLevel() + 1.0f) / 5.0f);
		}
		rewardVO.add((int) rep, (int) cash, EnumRewardCategory.BASE, EnumRewardType.NONE);

		float skillCash = cash * rewardBO.getSkillMultiplicater(personaEntity.getPersonaId(), 0);
		rewardVO.add(0, (int) skillCash, EnumRewardCategory.SKILL, EnumRewardType.SKILL_MOD);

		float rankExp = routeArbitrationPacket.getRank() == 1 ? rep * 0.10f : rep * 0.05f; // + 10% if fist, + 5% else
		float rankCash = routeArbitrationPacket.getRank() == 1 ? cash * 0.10f : cash * 0.05f; // + 10% if fist, + 5% else
		rankExp = routeArbitrationPacket.getFinishReason() == 22 ? rankExp : rankExp / 10;
		rankCash = routeArbitrationPacket.getFinishReason() == 22 ? rankCash : rankCash / 10;
		rewardVO.add((int) rankExp, (int) rankCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);

		float timeRaceExp = rep * (((100000000f / routeArbitrationPacket.getEventDurationInMilliseconds()) / routeArbitrationPacket.getRank()) / 100.0f);
		float timeRaceCash = cash * (((100000000f / routeArbitrationPacket.getEventDurationInMilliseconds()) / routeArbitrationPacket.getRank()) / 100.0f);
		timeRaceExp = routeArbitrationPacket.getFinishReason() == 22 ? timeRaceExp : timeRaceExp / 10;
		timeRaceCash = routeArbitrationPacket.getFinishReason() == 22 ? timeRaceCash : timeRaceCash / 10;
		rewardVO.add((int) timeRaceExp, (int) timeRaceCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);

		if (routeArbitrationPacket.getPerfectStart() == 1) {
			float perfectStartExp = rep * 0.10f; // + 10%
			float perfectStartCash = cash * 0.10f; // + 10%
			perfectStartExp = routeArbitrationPacket.getFinishReason() == 22 ? perfectStartExp : perfectStartExp / 10;
			perfectStartCash = routeArbitrationPacket.getFinishReason() == 22 ? perfectStartCash : perfectStartCash / 10;
			rewardVO.add((int) perfectStartExp, (int) perfectStartCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}

		if (routeArbitrationPacket.getTopSpeed() >= 70.0f) {
			float highSpeedExp = rep * 0.10f; // + 10%
			float highSpeedCash = cash * 0.10f; // + 10%
			highSpeedExp = routeArbitrationPacket.getFinishReason() == 22 ? highSpeedExp : highSpeedExp / 10;
			highSpeedCash = routeArbitrationPacket.getFinishReason() == 22 ? highSpeedCash : highSpeedCash / 10;
			rewardVO.add((int) highSpeedExp, (int) highSpeedCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}

		float repMult = rewardVO.getRep() * parameterBO.getRepRewardMultiplier();
		float cashMult = rewardVO.getCash() * parameterBO.getCashRewardMultiplier();
		rewardVO.add((int) repMult, 0, EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
		rewardVO.add(0, (int) cashMult, EnumRewardCategory.AMPLIFIER, EnumRewardType.TOKEN_AMPLIFIER);

		Accolades accolades = new Accolades();
		accolades.setFinalRewards(rewardBO.getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setHasLeveledUp(rewardBO.isLeveledUp(personaEntity, rewardVO.getRep()));
		accolades.setLuckyDrawInfo(rewardBO.getLuckyDrawInfo(routeArbitrationPacket.getRank(), personaEntity.getLevel(), personaEntity));
		accolades.setOriginalRewards(rewardBO.getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());

		rewardBO.applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return accolades;
	}

}
