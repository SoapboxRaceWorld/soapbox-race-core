package com.soapboxrace.core.bo.util;

import java.util.List;

import javax.ejb.EJB;

import com.soapboxrace.core.bo.DropBO;
import com.soapboxrace.core.bo.InventoryBO;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.bo.PersonaBO;
import com.soapboxrace.core.dao.InventoryDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.dao.LevelRepDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.LuckyDrawItem;
import com.soapboxrace.jaxb.http.Reward;
import com.soapboxrace.jaxb.http.RewardPart;
import com.soapboxrace.jaxb.http.SkillModPartTrans;

public class AccoladesFunc {

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private InventoryDAO inventoryDao;

	@EJB
	private InventoryItemDAO inventoryItemDao;

	@EJB
	private LevelRepDAO levelRepDao;

	@EJB
	private ProductDAO productDao;

	@EJB
	private PersonaBO personaBo;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private DropBO dropBO;

	@EJB
	private InventoryBO inventoryBO;

	public void applyRaceReward(Integer exp, Integer cash, PersonaEntity personaEntity) {
		// Cash parts
		if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
			Integer cashMax = (int) personaEntity.getCash() + cash;
			personaEntity.setCash(cashMax > 9999999 ? 9999999 : cashMax < 1 ? 1 : cashMax);
		}

		// Exp parts
		if (parameterBO.getBoolParam("ENABLE_REPUTATION")) {
			if (personaEntity.getLevel() < 60) {
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
					}
				} else {
					personaEntity.setRepAtCurrentLevel(expMax.intValue());
				}
				personaEntity.setRep(personaEntity.getRep() + exp);
			}
		}

		// Save parts
		personaDao.update(personaEntity);
	}

	public Reward getFinalReward(Integer rep, Integer cash) {
		Reward finalReward = new Reward();
		finalReward.setRep(rep);
		finalReward.setTokens(cash);
		return finalReward;
	}

	public RewardPart getRewardPart(Integer rep, Integer cash, EnumRewardCategory category, EnumRewardType type) {
		RewardPart rewardPart = new RewardPart();
		rewardPart.setRepPart(rep);
		rewardPart.setRewardCategory(category);
		rewardPart.setRewardType(type);
		rewardPart.setTokenPart(cash);
		return rewardPart;
	}

	public Boolean isLeveledUp(PersonaEntity personaEntity, Integer exp) {
		return (long) (personaEntity.getRepAtCurrentLevel() + exp) >= levelRepDao.findByLevel((long) personaEntity.getLevel()).getExpPoint();
	}

	public LuckyDrawItem getItemFromProduct(PersonaEntity personaEntity) {
		ProductEntity productEntity = dropBO.getRandomProductItem();
		
		if (productEntity == null) return new LuckyDrawItem();
		
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

	/**
	 * @param personaId
	 * @param type
	 *            [0 = race, 1 = pursuit & team escape, 2 = treasur hunter
	 *            (freeroam)]
	 * @return
	 */
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

}