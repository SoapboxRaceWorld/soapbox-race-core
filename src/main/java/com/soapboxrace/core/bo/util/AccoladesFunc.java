package com.soapboxrace.core.bo.util;

import java.util.List;
import java.util.Random;

import javax.ejb.EJB;

import org.apache.commons.codec.digest.DigestUtils;

import com.soapboxrace.core.bo.PersonaBO;
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
	private LevelRepDAO levelRepDao;

	@EJB
	private ProductDAO productDao;
	
	@EJB
	private PersonaBO personaBo;
	
	private int[][] rankDrop = new int[][] {
		new int[] { },
		new int[] { 0, 4, 2, 4, 2, 3, 2, 3, 2, 2 },
		new int[] { 1, 3, 1, 1, 0, 3, 1, 1, 2, 2 },
		new int[] { 2, 1, 1, 1, 1, 1, 2, 2, 2, 0 }
	};
	
	private int[][] rankDropTH = new int[][] {
		new int[] { },
		new int[] { 2, 3, 4, 4, 4, 2, 3, 4, 4, 3 },
		new int[] { 2, 3, 2, 4, 4, 4, 3, 3, 4, 2 },
		new int[] { 3, 2, 3, 3, 3, 4, 2, 2, 4, 4 },
		new int[] { 3, 2, 2, 4, 2, 2, 3, 3, 4, 4 },
		new int[] { 2, 2, 3, 2, 2, 3, 2, 4, 3, 4 }
	};
	
	public void applyRaceReward(Integer exp, Integer cash, PersonaEntity personaEntity) {
		// Cash parts
		Integer cashMax = (int)personaEntity.getCash() + cash;
		personaEntity.setCash(cashMax > 9999999 ? 9999999 : cashMax < 1 ? 1 : cashMax);
		
		// Exp parts
		if(personaEntity.getLevel() < 60) {
			Long expToNextLevel = levelRepDao.findByLevel((long)personaEntity.getLevel()).getExpPoint();
			Long expMax = (long)(personaEntity.getRepAtCurrentLevel() + exp);
			if(expMax >= expToNextLevel) {
				Boolean isLeveledUp = true;
				while(isLeveledUp) {
					personaEntity.setLevel(personaEntity.getLevel() + 1);
					personaEntity.setRepAtCurrentLevel((int)(expMax - expToNextLevel));
					
					expToNextLevel = levelRepDao.findByLevel((long)personaEntity.getLevel()).getExpPoint();
					expMax = (long)(personaEntity.getRepAtCurrentLevel() + exp);
					
					isLeveledUp = (expMax >= expToNextLevel);
				}
			} else {
				personaEntity.setRepAtCurrentLevel(expMax.intValue());
			}
			personaEntity.setRep(personaEntity.getRep() + exp);
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
		return (long)(personaEntity.getRepAtCurrentLevel() + exp) >= levelRepDao.findByLevel((long)personaEntity.getLevel()).getExpPoint() ? true : false;
	}
	
	public LuckyDrawItem getItemFromProduct(Integer rank, Integer level, Boolean isTH) {
		Integer hash = 0, count = 0, price = 0;
		String desc = "", icon = "", vItem = "", vItemType = "";
		Boolean isSold = false;
		
		List<ProductEntity> getProductItems = null;
		
		Integer randomCategory = getRandomCat(rank, isTH, new Random().nextInt(10));
		if(randomCategory == 1) { // Powerup
			getProductItems = productDao.findForEndRace("STORE_POWERUPS", "POWERUP", level);
		} else if(randomCategory == 2) { // Perf
			getProductItems = productDao.findForEndRace("NFSW_NA_EP_PERFORMANCEPARTS", "PERFORMANCEPART", level);
		} else if(randomCategory == 3) { // Skill
			getProductItems = productDao.findForEndRace("NFSW_NA_EP_SKILLMODPARTS", "SKILLMODPART", level);
		} else if(randomCategory == 4) { // Visual
			getProductItems = productDao.findForEndRace(getVisualCatgeory(new Random().nextInt(8)), "VISUALPART", level);
		}
		
		if(getProductItems != null) { // Other part
			Integer randomDrop = new Random().nextInt(getProductItems.size());
			ProductEntity productEntity = getProductItems.get(randomDrop);
			
			if(randomCategory == 1) {
				String strCut = productEntity.getProductTitle().replace("x15", "");
				count = new Random().nextInt(15) + 1;
				desc = strCut + " x" + count;
			} else {
				desc = productEntity.getProductTitle();
				count = 1;
			}
			hash = productEntity.getHash().intValue();
			icon = productEntity.getIcon();
			price = (int)(productEntity.getPrice() / 3.5);
			vItem = DigestUtils.md5Hex(productEntity.getHash().toString());
			vItemType = productEntity.getProductType();
		} else { // Cash part
			Integer cashBonus = new Random().nextInt(25000) + 1;
			desc = String.valueOf(cashBonus) + " CASH";
			icon = "128_cash";
			vItemType = "CASH";
		}
		
		LuckyDrawItem luckyDrawItem = new LuckyDrawItem();
		luckyDrawItem.setDescription(desc);
		luckyDrawItem.setHash(hash);
		luckyDrawItem.setIcon(icon);
		luckyDrawItem.setRemainingUseCount(count);
		luckyDrawItem.setResellPrice(price);
		luckyDrawItem.setVirtualItem(vItem);
		luckyDrawItem.setVirtualItemType(vItemType);
		luckyDrawItem.setWasSold(isSold);
		return luckyDrawItem;
	}
	
	
	/**
	 * @param personaId
	 * @param type [0 = race, 1 = pursuit & team escape, 2 = treasur hunter (freeroam)]
	 * @return
	 */
	public float getSkillMultiplicater(Long personaId, Integer type) {
		float multi = 0.0f;
		
		List<SkillModPartTrans> listSkillModPartTrans = personaBo.getDefaultCar(personaId).getCustomCar().getSkillModParts().getSkillModPartTrans();
		for(SkillModPartTrans skillMod : listSkillModPartTrans) {
			Integer skillModPartHash = skillMod.getSkillModPartAttribHash();
			if(type.equals(0)) {
				switch(skillModPartHash) {
					case -1253544909: multi += 0.24f; break;
					case -1810018228: multi += 0.23f; break;
					case -178864567:  multi += 0.22f; break;
					case -1070749597: multi += 0.21f; break;
					case 1510853218:  multi += 0.20f; break;
					case 761092816:   multi += 0.19f; break;
					case -1375070995: multi += 0.18f; break;
					case 1298655836:  multi += 0.17f; break;
					case -1230383337: multi += 0.16f; break;
					case 178242509:   multi += 0.15f; break;
					case 177556171:   multi += 0.14f; break;
					case 208768909:   multi += 0.09f; break;
					case -1426720169: multi += 0.08f; break;
					case -1512835408: multi += 0.06f; break;
					case 25946355:    multi += 0.05f; break;
					case -1867751699: multi += 0.04f; break;
					case 1610883347:  multi += 0.03f; break;
				}
			} else if(type.equals(1)) {
				switch(skillModPartHash) {
					case -396529168:  multi += 0.24f; break;
					case -185409775:  multi += 0.23f; break;
					case 1804531402:  multi += 0.22f; break;
					case 264948697:   multi += 0.21f; break;
					case 1618683071:  multi += 0.20f; break;
					case -1874412238: multi += 0.19f; break;
					case 7127040:     multi += 0.18f; break;
					case -1200030819: multi += 0.17f; break;
					case -240684266:  multi += 0.16f; break;
					case 647407388:   multi += 0.15f; break;
					case -859142767:  multi += 0.14f; break;
					case -681117003:  multi += 0.09f; break;
					case -1875655157: multi += 0.08f; break;
					case 2135702575:  multi += 0.06f; break;
					case -603724933:  multi += 0.05f; break;
					case -1005386908: multi += 0.04f; break;
					case 639750226:   multi += 0.03f; break;
				}
				
			} else if(type.equals(2)) {
				switch(skillModPartHash) {
					case -804203671:  multi += 0.40f; break;
					case 393025796:   multi += 0.39f; break;
					case 1202764048:  multi += 0.36f; break;
					case 748593849:   multi += 0.35f; break;
					case -261284782:  multi += 0.33f; break;
					case -1911348285: multi += 0.31f; break;
					case 604572815:   multi += 0.30f; break;
					case -751941732:  multi += 0.28f; break;
					case -546309571:  multi += 0.27f; break;
					case 163017142:   multi += 0.26f; break;
					case 2004729337:  multi += 0.25f; break;
					case 564562464:   multi += 0.24f; break;
					case -1097809463: multi += 0.23f; break;
					case -1544986384: multi += 0.15f; break;
					case -901736213:  multi += 0.14f; break;
					case -720398048:  multi += 0.13f; break;
					case 1948827710:  multi += 0.10f; break;
					case 393625900:   multi += 0.09f; break;
					case -276596299:  multi += 0.07f; break;
					case 541216806:   multi += 0.06f; break;
					case 2102023947:  multi += 0.05f; break;
				}
				
			}
		}
		
		if(type.equals(2)) {
			multi = multi > 0.50f ? 0.50f : multi;
		} else {
			multi = multi > 0.30f ? 0.30f : multi;
		}
		return multi;
	}
	
	private String getVisualCatgeory(Integer nRandom) {
		switch(nRandom) {
			case 0: return "NFSW_NA_EP_VISUALPARTS_LICENSEPLATES";
			case 1: return "NFSW_NA_EP_VISUALPARTS_NEONS";
			case 2: return "NFSW_NA_EP_VISUALPARTS_WHEELS";
			case 3: return "STORE_VANITY_LICENSE_PLATE";
			case 4: return "STORE_VANITY_LOWERING_KIT";
			case 5: return "STORE_VANITY_NEON";
			case 6: return "STORE_VANITY_WHEEL";
			case 7: return "STORE_VANITY_WINDOW";
			default: return "STORE_VANITY_LICENSE_PLATE";
		}
	}
	
	private Integer getRandomCat(Integer rank, Boolean isTH, Integer random) {
		if(isTH) {
			return rankDropTH[rank][random];
		} else {
			return rank > 3 ? 1 : rankDrop[rank][random];
		}
	}
}