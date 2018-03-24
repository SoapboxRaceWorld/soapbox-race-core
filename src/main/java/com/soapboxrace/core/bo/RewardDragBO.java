package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.DragArbitrationPacket;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;

@Stateless
public class RewardDragBO {

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private RewardBO rewardBO;

	@EJB
	private LegitRaceBO legitRaceBO;

	public Accolades getDragAccolades(Long activePersonaId, DragArbitrationPacket dragArbitrationPacket, EventSessionEntity eventSessionEntity) {
		if (!legitRaceBO.isLegit(activePersonaId, dragArbitrationPacket, eventSessionEntity)) {
			return new Accolades();
		}
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = new RewardVO(parameterBO.getBoolParam("ENABLE_ECONOMY"), parameterBO.getBoolParam("ENABLE_REPUTATION"));

		float rep = 0;
		float cash = 0;
		if (personaEntity.getLevel() < 60) {
			rep = 100 * (personaEntity.getLevel() / 5.0f);
		}
		if (personaEntity.getCash() < 9999999) {
			cash = 200 * (personaEntity.getLevel() / 5.0f);
		}
		rewardVO.add((int) rep, (int) cash, EnumRewardCategory.BASE, EnumRewardType.NONE);

		float skillCash = cash * rewardBO.getSkillMultiplicater(personaEntity.getPersonaId(), 0);
		rewardVO.add(0, (int) skillCash, EnumRewardCategory.SKILL, EnumRewardType.SKILL_MOD);

		float rankExp = dragArbitrationPacket.getRank() == 1 ? rep * 0.10f : rep * 0.05f; // + 10% if fist, + 5% else
		float rankCash = dragArbitrationPacket.getRank() == 1 ? cash * 0.10f : cash * 0.05f; // + 10% if fist, + 5% else
		rankExp = dragArbitrationPacket.getFinishReason() == 22 ? rankExp : rankExp / 10;
		rankCash = dragArbitrationPacket.getFinishReason() == 22 ? rankCash : rankCash / 10;
		rewardVO.add((int) rankExp, (int) rankCash, EnumRewardCategory.OBJECTIVE, EnumRewardType.NONE);

		float timeRaceExp = rep * ((60000.0f / dragArbitrationPacket.getEventDurationInMilliseconds()) / 4.0f);
		float timeRaceCash = cash * ((60000.0f / dragArbitrationPacket.getEventDurationInMilliseconds()) / 4.0f);
		timeRaceExp = dragArbitrationPacket.getFinishReason() == 22 ? timeRaceExp : timeRaceExp / 10;
		timeRaceCash = dragArbitrationPacket.getFinishReason() == 22 ? timeRaceCash : timeRaceCash / 10;
		rewardVO.add((int) timeRaceExp, (int) timeRaceCash, EnumRewardCategory.OBJECTIVE, EnumRewardType.NONE);

		if (dragArbitrationPacket.getPerfectStart() == 1) {
			float perfectStartExp = rep * 0.10f; // + 10%
			float perfectStartCash = cash * 0.10f; // + 10%
			perfectStartExp = dragArbitrationPacket.getFinishReason() == 22 ? perfectStartExp : perfectStartExp / 10;
			perfectStartCash = dragArbitrationPacket.getFinishReason() == 22 ? perfectStartCash : perfectStartCash / 10;
			rewardVO.add((int) perfectStartExp, (int) perfectStartCash, EnumRewardCategory.OBJECTIVE, EnumRewardType.NONE);
		}

		if (dragArbitrationPacket.getTopSpeed() >= 70.0f) {
			float highSpeedExp = rep * 0.10f; // + 10%
			float highSpeedCash = cash * 0.10f; // + 10%
			highSpeedExp = dragArbitrationPacket.getFinishReason() == 22 ? highSpeedExp : highSpeedExp / 10;
			highSpeedCash = dragArbitrationPacket.getFinishReason() == 22 ? highSpeedCash : highSpeedCash / 10;
			rewardVO.add((int) highSpeedExp, (int) highSpeedCash, EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}

		float repMult = rewardVO.getRep() * parameterBO.getRepRewardMultiplier();
		float cashMult = rewardVO.getCash() * parameterBO.getCashRewardMultiplier();
		rewardVO.add((int) repMult, 0, EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
		rewardVO.add(0, (int) cashMult, EnumRewardCategory.AMPLIFIER, EnumRewardType.TOKEN_AMPLIFIER);

		Accolades accolades = new Accolades();
		accolades.setFinalRewards(rewardBO.getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());
		accolades.setHasLeveledUp(rewardBO.isLeveledUp(personaEntity, rewardVO.getRep()));
		accolades.setLuckyDrawInfo(rewardBO.getLuckyDrawInfo(dragArbitrationPacket.getRank(), personaEntity.getLevel(), personaEntity));
		accolades.setOriginalRewards(rewardBO.getFinalReward(rewardVO.getRep(), rewardVO.getCash()));

		rewardBO.applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return accolades;
	}
}
