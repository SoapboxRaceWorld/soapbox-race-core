package com.soapboxrace.core.bo;

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
import com.soapboxrace.jaxb.http.RouteArbitrationPacket;

@Stateless
public class RewardRouteBO {

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private RewardBO rewardBO;

	@EJB
	private LegitRaceBO legitRaceBO;

	public Float getPlayerLevelConst(int playerLevel, float levelCashRewardMultiplier) {
		Float level = (float) playerLevel;
		return levelCashRewardMultiplier * level.floatValue();
	}

	public Float getTimeConst(Long legitTime, Long routeTime) {
		Float timeConst = legitTime.floatValue() / routeTime.floatValue();
		return Math.min(timeConst, 1f);
	}

	public int getBaseReward(float baseReward, float playerLevelConst, float timeConst) {
		Float baseRewardResult = baseReward * playerLevelConst * timeConst;
		return baseRewardResult.intValue();
	}

	public void setBaseReward(PersonaEntity personaEntity, EventEntity eventEntity, RouteArbitrationPacket routeArbitrationPacket, RewardVO rewardVO) {
		Float baseRep = (float) eventEntity.getBaseRepReward();
		Float baseCash = (float) eventEntity.getBaseCashReward();
		Float playerLevelRepConst = getPlayerLevelConst(personaEntity.getLevel(), eventEntity.getLevelRepRewardMultiplier());
		Float playerLevelCashConst = getPlayerLevelConst(personaEntity.getLevel(), eventEntity.getLevelCashRewardMultiplier());
		Float timeConst = getTimeConst(eventEntity.getLegitTime(), routeArbitrationPacket.getEventDurationInMilliseconds());
		rewardVO.setBaseRep(getBaseReward(baseRep, playerLevelRepConst, timeConst));
		rewardVO.setBaseCash(getBaseReward(baseCash, playerLevelCashConst, timeConst));
	}

	public void setRankReward(EventEntity eventEntity, RouteArbitrationPacket routeArbitrationPacket, RewardVO rewardVO) {
		float rankRepMultiplier = 0f;
		float rankCashMultiplier = 0f;
		switch (routeArbitrationPacket.getRank()) {
		case 1:
			rankRepMultiplier = eventEntity.getRank1RepMultiplier();
			rankCashMultiplier = eventEntity.getRank1CashMultiplier();
			break;
		case 2:
			rankRepMultiplier = eventEntity.getRank2RepMultiplier();
			rankCashMultiplier = eventEntity.getRank2CashMultiplier();
			break;
		case 3:
			rankRepMultiplier = eventEntity.getRank3RepMultiplier();
			rankCashMultiplier = eventEntity.getRank3CashMultiplier();
			break;
		case 4:
			rankRepMultiplier = eventEntity.getRank4RepMultiplier();
			rankCashMultiplier = eventEntity.getRank4CashMultiplier();
			break;
		case 5:
			rankRepMultiplier = eventEntity.getRank5RepMultiplier();
			rankCashMultiplier = eventEntity.getRank5CashMultiplier();
			break;
		case 6:
			rankRepMultiplier = eventEntity.getRank6RepMultiplier();
			rankCashMultiplier = eventEntity.getRank6CashMultiplier();
			break;
		case 7:
			rankRepMultiplier = eventEntity.getRank7RepMultiplier();
			rankCashMultiplier = eventEntity.getRank7CashMultiplier();
			break;
		case 8:
			rankRepMultiplier = eventEntity.getRank8RepMultiplier();
			rankCashMultiplier = eventEntity.getRank8CashMultiplier();
			break;
		default:
			break;
		}
		float baseRep = rewardVO.getBaseRep();
		float baseCash = rewardVO.getBaseCash();
		Float rankRepResult = baseRep * rankRepMultiplier;
		Float cashRepRestul = baseCash * rankCashMultiplier;
		rewardVO.add(rankRepResult.intValue(), cashRepRestul.intValue(), EnumRewardCategory.BONUS, EnumRewardType.NONE);
	}

	public void setPerfectStartReward(EventEntity eventEntity, RouteArbitrationPacket routeArbitrationPacket, RewardVO rewardVO) {
		if (routeArbitrationPacket.getPerfectStart() == 1) {
			float baseRep = rewardVO.getBaseRep();
			float baseCash = rewardVO.getBaseCash();
			float perfectStartCashMultiplier = eventEntity.getPerfectStartCashMultiplier();
			float perfectStartRepMultiplier = eventEntity.getPerfectStartRepMultiplier();
			Float perfectStartRep = baseRep * perfectStartRepMultiplier;
			Float perfectStartCash = baseCash * perfectStartCashMultiplier;
			rewardVO.add(perfectStartRep.intValue(), perfectStartCash.intValue(), EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}
	}

	public void setTopSpeedReward(EventEntity eventEntity, RouteArbitrationPacket routeArbitrationPacket, RewardVO rewardVO) {
		float minTopSpeedTrigger = eventEntity.getMinTopSpeedTrigger();
		if (routeArbitrationPacket.getTopSpeed() >= minTopSpeedTrigger) {
			float baseRep = rewardVO.getBaseRep();
			float baseCash = rewardVO.getBaseCash();
			float topSpeedCashMultiplier = eventEntity.getTopSpeedCashMultiplier();
			float topSpeedRepMultiplier = eventEntity.getTopSpeedRepMultiplier();
			Float highSpeedRep = baseRep * topSpeedRepMultiplier;
			Float highSpeedCash = baseCash * topSpeedCashMultiplier;
			rewardVO.add(highSpeedRep.intValue(), highSpeedCash.intValue(), EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}
	}

	public void setSkillMultiplierReward(PersonaEntity personaEntity, RewardVO rewardVO) {
		// TODO getCarSkillMultipliers from db
		rewardVO.add(0, 0, EnumRewardCategory.SKILL_MOD, EnumRewardType.REP_AMPLIFIER);
		rewardVO.add(0, 0, EnumRewardCategory.SKILL_MOD, EnumRewardType.TOKEN_AMPLIFIER);
	}

	public void setMultiplierReward(EventEntity eventEntity, RewardVO rewardVO) {
		float rep = rewardVO.getRep();
		float cash = rewardVO.getCash();
		float finalRepRewardMultiplier = eventEntity.getFinalRepRewardMultiplier();
		float finalCashRewardMultiplier = eventEntity.getFinalCashRewardMultiplier();
		Float finalRep = rep * finalRepRewardMultiplier;
		Float finalCash = cash * finalCashRewardMultiplier;
		rewardVO.add(finalRep.intValue(), 0, EnumRewardCategory.AMPLIFIER, EnumRewardType.REP_AMPLIFIER);
		rewardVO.add(0, finalCash.intValue(), EnumRewardCategory.AMPLIFIER, EnumRewardType.TOKEN_AMPLIFIER);
	}

	public Accolades getAccolades(PersonaEntity personaEntity, RouteArbitrationPacket routeArbitrationPacket, RewardVO rewardVO) {
		Accolades accolades = new Accolades();
		accolades.setFinalRewards(rewardBO.getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setHasLeveledUp(rewardBO.isLeveledUp(personaEntity, rewardVO.getRep()));
		accolades.setLuckyDrawInfo(rewardBO.getLuckyDrawInfo(routeArbitrationPacket.getRank(), personaEntity.getLevel(), personaEntity));
		accolades.setOriginalRewards(rewardBO.getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());
		return accolades;
	}

	public Accolades getRouteAccolades(Long activePersonaId, RouteArbitrationPacket routeArbitrationPacket, EventSessionEntity eventSessionEntity) {
		if (!legitRaceBO.isLegit(activePersonaId, routeArbitrationPacket, eventSessionEntity)) {
			return new Accolades();
		}
		EventEntity eventEntity = eventSessionEntity.getEvent();
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = new RewardVO(parameterBO.getBoolParam("ENABLE_ECONOMY"), parameterBO.getBoolParam("ENABLE_REPUTATION"));

		setBaseReward(personaEntity, eventEntity, routeArbitrationPacket, rewardVO);
		setRankReward(eventEntity, routeArbitrationPacket, rewardVO);
		setPerfectStartReward(eventEntity, routeArbitrationPacket, rewardVO);
		setTopSpeedReward(eventEntity, routeArbitrationPacket, rewardVO);
		setSkillMultiplierReward(personaEntity, rewardVO);
		setMultiplierReward(eventEntity, rewardVO);

		rewardBO.applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return getAccolades(personaEntity, routeArbitrationPacket, rewardVO);
	}

}
