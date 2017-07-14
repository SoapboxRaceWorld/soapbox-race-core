package com.soapboxrace.core.bo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.codec.digest.DigestUtils;

import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.TreasureHuntDAO;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.core.jpa.TreasureHuntEntity;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.ArrayOfLuckyDrawBox;
import com.soapboxrace.jaxb.http.ArrayOfLuckyDrawItem;
import com.soapboxrace.jaxb.http.ArrayOfRewardPart;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.LuckyDrawBox;
import com.soapboxrace.jaxb.http.LuckyDrawInfo;
import com.soapboxrace.jaxb.http.LuckyDrawItem;
import com.soapboxrace.jaxb.http.Reward;
import com.soapboxrace.jaxb.http.RewardPart;
import com.soapboxrace.jaxb.http.TreasureHuntEventSession;
import com.soapboxrace.jaxb.util.MarshalXML;

@Stateless
public class EventsBO {
	
	@EJB
	private PersonaDAO personaDao;
	
	@EJB
	private TreasureHuntDAO treasureHuntDao;
	
	@EJB
	private ProductDAO productDao;
	
	@EJB
	private DriverPersonaBO driverPersonaBo;

	public TreasureHuntEventSession getTreasureHuntEventSession(Long activePersonaId) {
		TreasureHuntEntity treasureHuntEntity = treasureHuntDao.findById(activePersonaId);
		if(treasureHuntEntity == null) {
			driverPersonaBo.createThInformation(personaDao.findById(activePersonaId));
			return getTreasureHuntEventSession(activePersonaId);
		}
		
		LocalDate thDate = treasureHuntEntity.getThDate();
		LocalDate nowDate = LocalDate.now();
		if(!thDate.equals(nowDate)) {
			Integer days = (int) ChronoUnit.DAYS.between(thDate, nowDate);
			if(days >= 2 || treasureHuntEntity.getCoinsCollected() != 32767) {
				return createNewTreasureHunt(treasureHuntEntity, true);
			} else {
				return createNewTreasureHunt(treasureHuntEntity, false);
			}
		}
		
		TreasureHuntEventSession treasureHuntEventSession = new TreasureHuntEventSession();
		treasureHuntEventSession.setCoinsCollected(treasureHuntEntity.getCoinsCollected());
		treasureHuntEventSession.setIsStreakBroken(treasureHuntEntity.getIsStreakBroken());
		treasureHuntEventSession.setNumCoins(treasureHuntEntity.getNumCoins());
		treasureHuntEventSession.setSeed(treasureHuntEntity.getSeed());
		treasureHuntEventSession.setStreak(treasureHuntEntity.getStreak());
		return treasureHuntEventSession;
	}
	
	public String notifyCoinCollected(Long activePersonaId, Integer coins) {
		TreasureHuntEntity treasureHuntEntity = treasureHuntDao.findById(activePersonaId);
		if(treasureHuntEntity != null) {
			treasureHuntEntity.setCoinsCollected(coins);
			treasureHuntDao.update(treasureHuntEntity);
		}
		
		if(coins == 32767) {
			return accolades(activePersonaId, false);
		}
		
		return "";
	}
	
	public String accolades(Long activePersonaId, Boolean isBroken)
	{
		TreasureHuntEntity treasureHuntEntity = treasureHuntDao.findById(activePersonaId);
		
		if(isBroken) {
			treasureHuntEntity.setStreak(1);
			treasureHuntEntity.setIsStreakBroken(false);
		} else {
			treasureHuntEntity.setStreak(treasureHuntEntity.getStreak() + 1);
		}

		treasureHuntEntity.setThDate(LocalDate.now());
		treasureHuntDao.update(treasureHuntEntity);
		
		return MarshalXML.marshal(getAccolades(1, treasureHuntEntity));
	}
	
	private TreasureHuntEventSession createNewTreasureHunt(TreasureHuntEntity treasureHuntEntity, Boolean isBroken) {
		treasureHuntEntity.setCoinsCollected(0);
		treasureHuntEntity.setIsStreakBroken(isBroken);
		treasureHuntEntity.setSeed(new Random().nextInt());
		treasureHuntEntity.setThDate(LocalDate.now());
		treasureHuntDao.update(treasureHuntEntity);
		
		TreasureHuntEventSession treasureHuntEventSession = new TreasureHuntEventSession();
		treasureHuntEventSession.setCoinsCollected(treasureHuntEntity.getCoinsCollected());
		treasureHuntEventSession.setIsStreakBroken(treasureHuntEntity.getIsStreakBroken());
		treasureHuntEventSession.setNumCoins(treasureHuntEntity.getNumCoins());
		treasureHuntEventSession.setSeed(treasureHuntEntity.getSeed());
		treasureHuntEventSession.setStreak(treasureHuntEntity.getStreak());
		return treasureHuntEventSession;
	}
	
	private Accolades getAccolades(Integer rank, TreasureHuntEntity treasureHuntEntity) {
		Accolades accolades = new Accolades();
		accolades.setFinalRewards(getFinalReward());
		accolades.setHasLeveledUp(false);
		accolades.setLuckyDrawInfo(getLuckyDrawInfo(rank, treasureHuntEntity));
		accolades.setOriginalRewards(getOriginalReward());
		accolades.setRewardInfo(getRewardPart());
		return accolades;
	}
	
	private Reward getFinalReward() {
		Reward finalReward = new Reward();
		finalReward.setRep(7331);
		finalReward.setTokens(1337);
		return finalReward;
	}
	
	private LuckyDrawInfo getLuckyDrawInfo(Integer rank, TreasureHuntEntity treasureHuntEntity) {
		ArrayOfLuckyDrawItem arrayOfLuckyDrawItem = new ArrayOfLuckyDrawItem();
		Integer hash = 0, count = 0, price = 0;
		String desc = "", icon = "", vItem = "", vItemType = "";
		Boolean isSold = false;
		
		List<ProductEntity> getProductItems = null;
		
		Integer randomCategory = rank > 3 ? 1 : EventBO.rankDrop[rank][new Random().nextInt(10)];
		if(randomCategory == 1) { // Powerup
			getProductItems = productDao.findForEndRace("STORE_POWERUPS", "POWERUP", rank);
		} else if(randomCategory == 2) { // Perf
			getProductItems = productDao.findForEndRace("NFSW_NA_EP_PERFORMANCEPARTS", "PERFORMANCEPART", rank);
		} else if(randomCategory == 3) { // Skill
			getProductItems = productDao.findForEndRace("NFSW_NA_EP_SKILLMODPARTS", "SKILLMODPART", rank);
		} else if(randomCategory == 4) { // Visual
			getProductItems = productDao.findForEndRace(EventBO.getVisualCatgeory(new Random().nextInt(8)), "VISUALPART", rank);
		}
		
		if(getProductItems != null) { // Other part
			Integer randomDrop = new Random().nextInt(getProductItems.size());
			ProductEntity productEntity = getProductItems.get(randomDrop);
			
			desc = productEntity.getDescription();
			hash = productEntity.getHash().intValue();
			icon = productEntity.getIcon();
			count = randomCategory == 1 ? new Random().nextInt(15) + 1 : 1;
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
		arrayOfLuckyDrawItem.getLuckyDrawItem().add(luckyDrawItem);
		
		ArrayOfLuckyDrawBox arrayOfLuckyDrawBox = new ArrayOfLuckyDrawBox();
		LuckyDrawBox luckyDrawBox = new LuckyDrawBox();
		luckyDrawBox.setIsValid(true);
		luckyDrawBox.setLocalizationString("LD_CARD_SILVER");
		luckyDrawBox.setLuckyDrawSetCategoryId(1);
		arrayOfLuckyDrawBox.getLuckyDrawBox().add(luckyDrawBox);
		arrayOfLuckyDrawBox.getLuckyDrawBox().add(luckyDrawBox);
		arrayOfLuckyDrawBox.getLuckyDrawBox().add(luckyDrawBox);
		arrayOfLuckyDrawBox.getLuckyDrawBox().add(luckyDrawBox);
		arrayOfLuckyDrawBox.getLuckyDrawBox().add(luckyDrawBox);
		
		LuckyDrawInfo luckyDrawInfo = new LuckyDrawInfo();
		luckyDrawInfo.setBoxes(arrayOfLuckyDrawBox);
		luckyDrawInfo.setCurrentStreak(treasureHuntEntity.getStreak() > 1 ? (treasureHuntEntity.getStreak() - 1) : 1);
		luckyDrawInfo.setIsStreakBroken(treasureHuntEntity.getIsStreakBroken());
		luckyDrawInfo.setItems(arrayOfLuckyDrawItem);
		luckyDrawInfo.setNumBoxAnimations(180);
		return luckyDrawInfo;
	}
	
	private Reward getOriginalReward() {
		Reward originalRewards = new Reward();
		originalRewards.setRep(562);
		originalRewards.setTokens(845);
		return originalRewards;
	}
	
	private ArrayOfRewardPart getRewardPart() {
		ArrayOfRewardPart arrayOfRewardPart = new ArrayOfRewardPart();
		RewardPart rewardPart = new RewardPart();
		rewardPart.setRepPart(256);
		rewardPart.setRewardCategory(EnumRewardCategory.BASE);
		rewardPart.setRewardType(EnumRewardType.NONE);
		rewardPart.setTokenPart(852);
		arrayOfRewardPart.getRewardPart().add(rewardPart);
		return arrayOfRewardPart;
	}
}
