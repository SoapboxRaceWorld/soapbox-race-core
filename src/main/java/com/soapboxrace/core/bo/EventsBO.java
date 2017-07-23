package com.soapboxrace.core.bo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.AccoladesFunc;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.TreasureHuntDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.TreasureHuntEntity;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.ArrayOfLuckyDrawBox;
import com.soapboxrace.jaxb.http.ArrayOfLuckyDrawItem;
import com.soapboxrace.jaxb.http.ArrayOfRewardPart;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.LuckyDrawBox;
import com.soapboxrace.jaxb.http.LuckyDrawInfo;
import com.soapboxrace.jaxb.http.TreasureHuntEventSession;
import com.soapboxrace.jaxb.util.MarshalXML;

@Stateless
public class EventsBO extends AccoladesFunc {
	
	@EJB
	private PersonaDAO personaDao;
	
	@EJB
	private TreasureHuntDAO treasureHuntDao;
	
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
		return coins == 32767 ? accolades(activePersonaId, false) : "";
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
		
		return MarshalXML.marshal(getTreasureHuntAccolades(activePersonaId, treasureHuntEntity));
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
	
	private Accolades getTreasureHuntAccolades(Long activePersonaId, TreasureHuntEntity treasureHuntEntity) {
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		
		// Maths begin
		ArrayOfRewardPart arrayOfRewardPart = new ArrayOfRewardPart();
		float exp = personaEntity.getLevel() >= 60 ? 0 : 200 * ((personaEntity.getLevel() + 1.0f) / 5.0f);
		float cash = personaEntity.getCash() >= 9999999 ? 0 : 600 * ((personaEntity.getLevel() + 1.0f) / 5.0f);
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)exp, (int)cash, EnumRewardCategory.BASE, EnumRewardType.NONE));
		
		cash += cash * getSkillMultiplicater(personaEntity.getPersonaId(), 2);
		arrayOfRewardPart.getRewardPart().add(getRewardPart(0, (int)cash, EnumRewardCategory.SKILL, EnumRewardType.SKILL_MOD));
		
		float dayExp = exp + (100 * treasureHuntEntity.getStreak()); // + 100 per day
		float dayCash = cash + (100 * treasureHuntEntity.getStreak()); // + 100 per day
		arrayOfRewardPart.getRewardPart().add(getRewardPart((int)dayExp, (int)dayCash, EnumRewardCategory.BASE, EnumRewardType.NONE));
		
		exp += (int)dayExp;
		cash += (int)dayCash;
		// Maths ending
		
		Accolades accolades = new Accolades();
		accolades.setFinalRewards(getFinalReward((int)exp, (int)cash));
		accolades.setHasLeveledUp(isLeveledUp(personaEntity, (int)exp));
		accolades.setLuckyDrawInfo(getLuckyDrawInfo(getRank(treasureHuntEntity.getStreak()), personaEntity.getLevel(), treasureHuntEntity));
		accolades.setOriginalRewards(getFinalReward((int)exp, (int)cash));
		accolades.setRewardInfo(arrayOfRewardPart);
		
		applyRaceReward((int)exp, (int)cash, personaEntity);
		return accolades;
	}
	
	private LuckyDrawInfo getLuckyDrawInfo(Integer rank, Integer level, TreasureHuntEntity treasureHuntEntity) {
		ArrayOfLuckyDrawItem arrayOfLuckyDrawItem = new ArrayOfLuckyDrawItem();
		arrayOfLuckyDrawItem.getLuckyDrawItem().add(getItemFromProduct(rank, level, true));
		
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
	
	private Integer getRank(Integer days) {
		if(days >= 100) {
			return 1;
		} else if(days >= 70) {
			return 2;
		} else if(days >= 50) {
			return 3;
		} else if(days >= 30) {
			return 4;
		}
		return 5;
	}
}
