package com.soapboxrace.core.bo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.TreasureHuntDAO;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.SkillModRewardType;
import com.soapboxrace.core.jpa.TreasureHuntEntity;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.ArbitrationPacket;
import com.soapboxrace.jaxb.http.ArrayOfLuckyDrawBox;
import com.soapboxrace.jaxb.http.ArrayOfLuckyDrawItem;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.LuckyDrawBox;
import com.soapboxrace.jaxb.http.LuckyDrawInfo;
import com.soapboxrace.jaxb.http.TreasureHuntEventSession;
import com.soapboxrace.jaxb.util.MarshalXML;

@Stateless
public class EventsBO {

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private TreasureHuntDAO treasureHuntDao;

	@EJB
	private DriverPersonaBO driverPersonaBo;

	@EJB
	private RewardBO rewardBO;

	@EJB
	private ParameterBO parameterBO;

	public TreasureHuntEventSession getTreasureHuntEventSession(Long activePersonaId) {
		TreasureHuntEntity treasureHuntEntity = treasureHuntDao.findById(activePersonaId);
		if (treasureHuntEntity == null) {
			driverPersonaBo.createThInformation(personaDao.findById(activePersonaId));
			return getTreasureHuntEventSession(activePersonaId);
		}

		LocalDate thDate = treasureHuntEntity.getThDate();
		LocalDate nowDate = LocalDate.now();
		if (!thDate.equals(nowDate)) {
			Integer days = (int) ChronoUnit.DAYS.between(thDate, nowDate);
			if (days >= 2 || treasureHuntEntity.getCoinsCollected() != 32767) {
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
		if (treasureHuntEntity != null) {
			treasureHuntEntity.setCoinsCollected(coins);
			treasureHuntDao.update(treasureHuntEntity);
		}
		return coins == 32767 ? accolades(activePersonaId, false) : "";
	}

	public String accolades(Long activePersonaId, Boolean isBroken) {
		TreasureHuntEntity treasureHuntEntity = treasureHuntDao.findById(activePersonaId);

		if (isBroken) {
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

		RewardVO rewardVO = rewardBO.getRewardVO(personaEntity);

		float baseRepTh = parameterBO.getFloatParam("TH_BASE_REP");
		float baseCashTh = parameterBO.getFloatParam("TH_BASE_CASH");

		float playerLevelRepConst = rewardBO.getPlayerLevelConst(personaEntity.getLevel(), baseRepTh);
		float playerLevelCashConst = rewardBO.getPlayerLevelConst(personaEntity.getLevel(), baseCashTh);

		float repThMultiplier = parameterBO.getFloatParam("TH_REP_MULTIPLIER");
		float cashThMultiplier = parameterBO.getFloatParam("TH_CASH_MULTIPLIER");

		Float baseRep = playerLevelRepConst * repThMultiplier;
		Float baseCash = playerLevelCashConst * cashThMultiplier;

		rewardVO.setBaseRep(baseRep.intValue());
		rewardVO.setBaseCash(baseCash.intValue());

		float repDayMultiplier = parameterBO.getFloatParam("TH_REP_MULTIPLIER");
		float cashDayMultiplier = parameterBO.getFloatParam("TH_DAY_CASH_MULTIPLIER");

		Float dayRep = baseRep + (repDayMultiplier * treasureHuntEntity.getStreak());
		Float dayCash = baseCash + (cashDayMultiplier * treasureHuntEntity.getStreak());

		rewardVO.add(dayRep.intValue(), dayCash.intValue(), EnumRewardCategory.BASE, EnumRewardType.NONE);
		rewardBO.setSkillMultiplierReward(personaEntity, rewardVO, SkillModRewardType.EXPLORER);

		ArbitrationPacket arbitrationPacket = new ArbitrationPacket();
		arbitrationPacket.setRank(1);
		if (!treasureHuntEntity.getIsStreakBroken()) {
			rewardBO.applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		}
		Accolades accolades = rewardBO.getAccolades(personaEntity, arbitrationPacket, rewardVO);
		accolades.setLuckyDrawInfo(getLuckyDrawInfo(treasureHuntEntity));

		return accolades;
	}

	private LuckyDrawInfo getLuckyDrawInfo(TreasureHuntEntity treasureHuntEntity) {
		ArrayOfLuckyDrawItem arrayOfLuckyDrawItem = new ArrayOfLuckyDrawItem();
		arrayOfLuckyDrawItem.getLuckyDrawItem().add(rewardBO.getItemFromProduct(personaDao.findById(treasureHuntEntity.getPersonaId())));

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

}
