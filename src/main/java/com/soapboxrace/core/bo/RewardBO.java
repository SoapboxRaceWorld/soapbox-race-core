package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.AchievementDAO;
import com.soapboxrace.core.dao.LevelRepDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.CardDecks;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.core.jpa.SkillModPartEntity;
import com.soapboxrace.core.jpa.SkillModRewardType;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.ArbitrationPacket;
import com.soapboxrace.jaxb.http.ArrayOfLuckyDrawItem;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.LuckyDrawInfo;
import com.soapboxrace.jaxb.http.LuckyDrawItem;
import com.soapboxrace.jaxb.http.Reward;
import com.soapboxrace.jaxb.http.RewardPart;

@Stateless
public class RewardBO {

	@EJB
	private PersonaBO personaBo;

	@EJB
	private LevelRepDAO levelRepDao;

	@EJB
	private DropBO dropBO;

	@EJB
	private InventoryBO inventoryBO;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private ProductDAO productDAO;
	
	@EJB
	private AchievementsBO achievementsBO;
	
	@EJB
	private AchievementDAO achievementDAO;

	public Reward getFinalReward(Integer rep, Integer cash) {
		Reward finalReward = new Reward();
		finalReward.setRep(rep);
		finalReward.setTokens(cash);
		return finalReward;
	}

	public Boolean isLeveledUp(PersonaEntity personaEntity, Integer exp) {
		return (long) (personaEntity.getRepAtCurrentLevel() + exp) >= levelRepDao.findByLevel((long) personaEntity.getLevel()).getExpPoint();
	}

	public LuckyDrawInfo getLuckyDrawInfo(Integer rank, Integer level, PersonaEntity personaEntity) {
		LuckyDrawInfo luckyDrawInfo = new LuckyDrawInfo();
		if (!parameterBO.getBoolParam("ENABLE_DROP_ITEM")) {
			return luckyDrawInfo;
		}
		ArrayOfLuckyDrawItem arrayOfLuckyDrawItem = new ArrayOfLuckyDrawItem();
		arrayOfLuckyDrawItem.getLuckyDrawItem().add(getItemFromProduct(personaEntity));
		luckyDrawInfo.setCardDeck(CardDecks.forRank(rank));
		luckyDrawInfo.setItems(arrayOfLuckyDrawItem);
		return luckyDrawInfo;
	}

	public LuckyDrawItem getItemFromProduct(PersonaEntity personaEntity) {
		ProductEntity productEntity = dropBO.getRandomProductItem();
		LuckyDrawItem luckyDrawItem = dropBO.copyProduct2LuckyDraw(productEntity);
		boolean inventoryFull = inventoryBO.isInventoryFull(productEntity, personaEntity);
		if (inventoryFull) {
			luckyDrawItem.setWasSold(true);
			if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
				float resalePrice = productEntity.getResalePrice();
				double cash = personaEntity.getCash();
				personaEntity.setCash(cash + resalePrice);
				personaDao.update(personaEntity);
			}
		} else {
			inventoryBO.addDroppedItem(productEntity, personaEntity);
		}
		return luckyDrawItem;
	}

	public void applyRaceReward(Integer exp, Integer cash, PersonaEntity personaEntity) {
		int maxLevel = 60;
		if (personaEntity.getUser().isPremium()) {
			maxLevel = parameterBO.getIntParam("MAX_LEVEL_PREMIUM");
		} else {
			maxLevel = parameterBO.getIntParam("MAX_LEVEL_FREE");
		}
		if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
			Integer cashMax = (int) personaEntity.getCash() + cash;
			personaEntity.setCash(cashMax > 9999999 ? 9999999 : cashMax < 1 ? 1 : cashMax);
		}

		if (parameterBO.getBoolParam("ENABLE_REPUTATION") && personaEntity.getLevel() < maxLevel) {
			Long expToNextLevel = levelRepDao.findByLevel((long) personaEntity.getLevel()).getExpPoint();
			Long expMax = (long) (personaEntity.getRepAtCurrentLevel() + exp);
			if (expMax >= expToNextLevel) {
				Boolean isLeveledUp = true;
				while (isLeveledUp) {
					personaEntity.setLevel(personaEntity.getLevel() + 1);
					personaEntity.setRepAtCurrentLevel((int) (expMax - expToNextLevel));

					expToNextLevel = levelRepDao.findByLevel((long) personaEntity.getLevel()).getExpPoint();
					expMax = (long) (personaEntity.getRepAtCurrentLevel() + exp);

					isLeveledUp = (expMax >= expToNextLevel);
					if (personaEntity.getLevel() >= maxLevel) {
						isLeveledUp = false;
					}
				}
			} else {
				personaEntity.setRepAtCurrentLevel(expMax.intValue());
			}
			personaEntity.setRep(personaEntity.getRep() + exp);
		}
		personaDao.update(personaEntity);
		achievementsBO.update(personaEntity,
				achievementDAO.findByName("achievement_ACH_REACH_DRIVERLEVEL"),
				(long) personaEntity.getLevel(),
				false);
		achievementsBO.update(personaEntity,
				achievementDAO.findByName("achievement_ACH_EARN_CASH_EVENT"),
				cash.longValue());
	}

	public RewardPart getRewardPart(Integer rep, Integer cash, EnumRewardCategory category, EnumRewardType type) {
		RewardPart rewardPart = new RewardPart();
		rewardPart.setRepPart(rep);
		rewardPart.setRewardCategory(category);
		rewardPart.setRewardType(type);
		rewardPart.setTokenPart(cash);
		return rewardPart;
	}

	public void setTopSpeedReward(EventEntity eventEntity, float topSpeed, RewardVO rewardVO) {
		float minTopSpeedTrigger = eventEntity.getMinTopSpeedTrigger();
		if (topSpeed >= minTopSpeedTrigger) {
			float baseRep = rewardVO.getBaseRep();
			float baseCash = rewardVO.getBaseCash();
			float topSpeedCashMultiplier = eventEntity.getTopSpeedCashMultiplier();
			float topSpeedRepMultiplier = eventEntity.getTopSpeedRepMultiplier();
			Float highSpeedRep = baseRep * topSpeedRepMultiplier;
			Float highSpeedCash = baseCash * topSpeedCashMultiplier;
			rewardVO.add(highSpeedRep.intValue(), highSpeedCash.intValue(), EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}
	}

	public void setSkillMultiplierReward(PersonaEntity personaEntity, RewardVO rewardVO, SkillModRewardType skillModRewardType) {
		CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaEntity.getPersonaId());
		List<SkillModPartEntity> skillModParts = defaultCarEntity.getOwnedCar().getCustomCar().getSkillModParts();
		float skillMultiplier = 0f;
		float maxSkillMultiplier = 30f;
		if (SkillModRewardType.EXPLORER.equals(skillModRewardType)) {
			maxSkillMultiplier = 50f;
		}
		for (SkillModPartEntity skillModPartEntity : skillModParts) {
			ProductEntity productEntity = productDAO.findByHash(skillModPartEntity.getSkillModPartAttribHash());
			if (productEntity != null && productEntity.getProductTitle().equals(skillModRewardType.toString())) {
				float skillValue = productEntity.getSkillValue();
				skillMultiplier = skillMultiplier + skillValue;
			}
		}
		float finalSkillMultiplier = Math.min(maxSkillMultiplier, skillMultiplier) / 100;
		float cash = rewardVO.getCash();
		Float finalCash = cash * finalSkillMultiplier;
		rewardVO.add(0, finalCash.intValue(), EnumRewardCategory.SKILL_MOD, EnumRewardType.TOKEN_AMPLIFIER);
	}

	public Accolades getAccolades(PersonaEntity personaEntity, ArbitrationPacket arbitrationPacket, RewardVO rewardVO) {
		Accolades accolades = new Accolades();
		accolades.setFinalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setHasLeveledUp(isLeveledUp(personaEntity, rewardVO.getRep()));
		accolades.setLuckyDrawInfo(getLuckyDrawInfo(arbitrationPacket.getRank(), personaEntity.getLevel(), personaEntity));
		accolades.setOriginalRewards(getFinalReward(rewardVO.getRep(), rewardVO.getCash()));
		accolades.setRewardInfo(rewardVO.getArrayOfRewardPart());
		return accolades;
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

	public void setPerfectStartReward(EventEntity eventEntity, int perfectStart, RewardVO rewardVO) {
		if (perfectStart == 1) {
			float baseRep = rewardVO.getBaseRep();
			float baseCash = rewardVO.getBaseCash();
			float perfectStartCashMultiplier = eventEntity.getPerfectStartCashMultiplier();
			float perfectStartRepMultiplier = eventEntity.getPerfectStartRepMultiplier();
			Float perfectStartRep = baseRep * perfectStartRepMultiplier;
			Float perfectStartCash = baseCash * perfectStartCashMultiplier;
			rewardVO.add(perfectStartRep.intValue(), perfectStartCash.intValue(), EnumRewardCategory.BONUS, EnumRewardType.NONE);
		}
	}

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

	public void setBaseReward(PersonaEntity personaEntity, EventEntity eventEntity, ArbitrationPacket arbitrationPacket, RewardVO rewardVO) {
		Float baseRep = (float) eventEntity.getBaseRepReward();
		Float baseCash = (float) eventEntity.getBaseCashReward();
		Float playerLevelRepConst = getPlayerLevelConst(personaEntity.getLevel(), eventEntity.getLevelRepRewardMultiplier());
		Float playerLevelCashConst = getPlayerLevelConst(personaEntity.getLevel(), eventEntity.getLevelCashRewardMultiplier());
		Float timeConst = getTimeConst(eventEntity.getLegitTime(), arbitrationPacket.getEventDurationInMilliseconds());
		rewardVO.setBaseRep(getBaseReward(baseRep, playerLevelRepConst, timeConst));
		rewardVO.setBaseCash(getBaseReward(baseCash, playerLevelCashConst, timeConst));
	}

	public void setRankReward(EventEntity eventEntity, ArbitrationPacket routeArbitrationPacket, RewardVO rewardVO) {
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

	public RewardVO getRewardVO(PersonaEntity personaEntity) {
		Boolean enableEconomy = parameterBO.getBoolParam("ENABLE_ECONOMY");
		Boolean enableReputation = parameterBO.getBoolParam("ENABLE_REPUTATION");
		if (personaEntity.getLevel() >= 60) {
			enableReputation = false;
		}
		if (personaEntity.getBoost() > 9999999) {
			enableEconomy = false;
		}
		return new RewardVO(enableEconomy, enableReputation);
	}

	public void setPursitParamReward(float rewardValue, EnumRewardType enumRewardType, RewardVO rewardVO) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("PURSUIT_");
		stringBuilder.append(enumRewardType.toString());
		String rewardMultiplierStr = stringBuilder.toString().concat("_REP_MULTIPLIER");
		String cashMultiplierStr = stringBuilder.toString().concat("_CASH_MULTIPLIER");
		float rewardMultiplier = parameterBO.getFloatParam(rewardMultiplierStr);
		float cashMultiplier = parameterBO.getFloatParam(cashMultiplierStr);
		float baseRep = rewardVO.getBaseRep();
		float baseCash = rewardVO.getBaseCash();
		Float repReward = baseRep * rewardValue * rewardMultiplier;
		Float cashReward = baseCash * rewardValue * cashMultiplier;
		rewardVO.add(repReward.intValue(), cashReward.intValue(), EnumRewardCategory.PURSUIT, enumRewardType);
	}
}
