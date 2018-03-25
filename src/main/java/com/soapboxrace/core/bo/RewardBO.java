package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.RewardVO;
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
import com.soapboxrace.jaxb.http.SkillModPartTrans;

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

	public float getSkillMultiplicater(Long personaId, Integer type) {
		float multi = 0.0f;

		List<SkillModPartTrans> listSkillModPartTrans = personaBo.getDefaultCar(personaId).getCustomCar().getSkillModParts().getSkillModPartTrans();
		for (SkillModPartTrans skillMod : listSkillModPartTrans) {
			Integer skillModPartHash = skillMod.getSkillModPartAttribHash();
			if (type.equals(0)) {
				switch (skillModPartHash) {
				case -1253544909:
					multi += 0.24f;
					break;
				case -1810018228:
					multi += 0.23f;
					break;
				case -178864567:
					multi += 0.22f;
					break;
				case -1070749597:
					multi += 0.21f;
					break;
				case 1510853218:
					multi += 0.20f;
					break;
				case 761092816:
					multi += 0.19f;
					break;
				case -1375070995:
					multi += 0.18f;
					break;
				case 1298655836:
					multi += 0.17f;
					break;
				case -1230383337:
					multi += 0.16f;
					break;
				case 178242509:
					multi += 0.15f;
					break;
				case 177556171:
					multi += 0.14f;
					break;
				case 208768909:
					multi += 0.09f;
					break;
				case -1426720169:
					multi += 0.08f;
					break;
				case -1512835408:
					multi += 0.06f;
					break;
				case 25946355:
					multi += 0.05f;
					break;
				case -1867751699:
					multi += 0.04f;
					break;
				case 1610883347:
					multi += 0.03f;
					break;
				}
			} else if (type.equals(1)) {
				switch (skillModPartHash) {
				case -396529168:
					multi += 0.24f;
					break;
				case -185409775:
					multi += 0.23f;
					break;
				case 1804531402:
					multi += 0.22f;
					break;
				case 264948697:
					multi += 0.21f;
					break;
				case 1618683071:
					multi += 0.20f;
					break;
				case -1874412238:
					multi += 0.19f;
					break;
				case 7127040:
					multi += 0.18f;
					break;
				case -1200030819:
					multi += 0.17f;
					break;
				case -240684266:
					multi += 0.16f;
					break;
				case 647407388:
					multi += 0.15f;
					break;
				case -859142767:
					multi += 0.14f;
					break;
				case -681117003:
					multi += 0.09f;
					break;
				case -1875655157:
					multi += 0.08f;
					break;
				case 2135702575:
					multi += 0.06f;
					break;
				case -603724933:
					multi += 0.05f;
					break;
				case -1005386908:
					multi += 0.04f;
					break;
				case 639750226:
					multi += 0.03f;
					break;
				}

			} else if (type.equals(2)) {
				switch (skillModPartHash) {
				case -804203671:
					multi += 0.40f;
					break;
				case 393025796:
					multi += 0.39f;
					break;
				case 1202764048:
					multi += 0.36f;
					break;
				case 748593849:
					multi += 0.35f;
					break;
				case -261284782:
					multi += 0.33f;
					break;
				case -1911348285:
					multi += 0.31f;
					break;
				case 604572815:
					multi += 0.30f;
					break;
				case -751941732:
					multi += 0.28f;
					break;
				case -546309571:
					multi += 0.27f;
					break;
				case 163017142:
					multi += 0.26f;
					break;
				case 2004729337:
					multi += 0.25f;
					break;
				case 564562464:
					multi += 0.24f;
					break;
				case -1097809463:
					multi += 0.23f;
					break;
				case -1544986384:
					multi += 0.15f;
					break;
				case -901736213:
					multi += 0.14f;
					break;
				case -720398048:
					multi += 0.13f;
					break;
				case 1948827710:
					multi += 0.10f;
					break;
				case 393625900:
					multi += 0.09f;
					break;
				case -276596299:
					multi += 0.07f;
					break;
				case 541216806:
					multi += 0.06f;
					break;
				case 2102023947:
					multi += 0.05f;
					break;
				}

			}
		}

		if (type.equals(2)) {
			multi = multi > 0.50f ? 0.50f : multi;
		} else {
			multi = multi > 0.30f ? 0.30f : multi;
		}
		return multi;
	}

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
		if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
			Integer cashMax = (int) personaEntity.getCash() + cash;
			personaEntity.setCash(cashMax > 9999999 ? 9999999 : cashMax < 1 ? 1 : cashMax);
		}

		if (parameterBO.getBoolParam("ENABLE_REPUTATION") && personaEntity.getLevel() < 60) {
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
					if (personaEntity.getLevel() >= 60) {
						isLeveledUp = false;
					}
				}
			} else {
				personaEntity.setRepAtCurrentLevel(expMax.intValue());
			}
			personaEntity.setRep(personaEntity.getRep() + exp);
		}
		personaDao.update(personaEntity);
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
