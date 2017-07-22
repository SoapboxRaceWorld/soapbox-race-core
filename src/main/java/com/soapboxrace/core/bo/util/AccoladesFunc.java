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