package com.soapboxrace.core.bo;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.RouteArbitrationPacket;

public class RewardRouteBOTest {

	private static RewardRouteBO rewardRouteBO;
	private static EventEntity eventEntity;
	private static RouteArbitrationPacket routeArbitrationPacket;
	private static RewardVO rewardVO;
	private static PersonaEntity personaEntity;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rewardRouteBO = new RewardRouteBO();
		eventEntity = new EventEntity();
		routeArbitrationPacket = new RouteArbitrationPacket();
		rewardVO = new RewardVO(true, true);
		personaEntity = new PersonaEntity();
	}

	@Test
	public void testGetPlayerLevelConst() throws Exception {
		Float levelCashRewardMultiplier = 0.1f;
		Float playerLevel = 2f;
		Float playerLevelConst = levelCashRewardMultiplier.floatValue() * playerLevel.floatValue();
		Float playerLevelConstResult = rewardRouteBO.getPlayerLevelConst(playerLevel.intValue(), levelCashRewardMultiplier);
		Assert.assertTrue(playerLevelConst.equals(playerLevelConstResult));
	}

	@Test
	public void testGetTimeConst() throws Exception {
		Float legitTime = 10000F;
		Float routeTime = 20000F;
		Float timeConst = Math.min(legitTime.floatValue() / routeTime.floatValue(), 1f);
		Float timeConstResult = rewardRouteBO.getTimeConst(legitTime.longValue(), routeTime.longValue());
		Assert.assertTrue(timeConst.equals(timeConstResult));
	}

	@Test
	public void testGetBaseReward() throws Exception {
		float baseReward = 1000f;
		float playerLevelConst = 1f;
		float timeConst = 0.99999f;
		Float baseRewardTest = baseReward * playerLevelConst * timeConst;
		int baseRewardResult = rewardRouteBO.getBaseReward(baseReward, playerLevelConst, timeConst);
		Assert.assertTrue(baseRewardTest.intValue() == baseRewardResult);
	}

	@Test
	public void testSetBaseReward() throws Exception {
		Float baseCash = 100000f;
		Float baseRep = 1000f;
		Float levelCashRewardMultiplier = 0.1f;
		Float levelRepRewardMultiplier = 0.1f;
		Integer playerLevel = 6;
		Float legitTime = 100000f;
		Float routeTime = 10000f;

		Float playerLevelCashConst = rewardRouteBO.getPlayerLevelConst(playerLevel, levelCashRewardMultiplier);
		Float timeConst = rewardRouteBO.getTimeConst(legitTime.longValue(), routeTime.longValue());
		Float playerLevelRepConst = rewardRouteBO.getPlayerLevelConst(playerLevel, levelRepRewardMultiplier);
		int resultCashTest = rewardRouteBO.getBaseReward(baseCash, playerLevelCashConst, timeConst);
		int resultRepTest = rewardRouteBO.getBaseReward(baseRep, playerLevelRepConst, timeConst);

		eventEntity.setBaseCashReward(baseCash.intValue());
		eventEntity.setBaseRepReward(baseRep.intValue());
		eventEntity.setLevelCashRewardMultiplier(levelCashRewardMultiplier);
		eventEntity.setLevelRepRewardMultiplier(levelRepRewardMultiplier);
		personaEntity.setLevel(playerLevel.intValue());
		eventEntity.setLegitTime(legitTime.longValue());
		routeArbitrationPacket.setEventDurationInMilliseconds(routeTime.longValue());

		rewardRouteBO.setBaseReward(personaEntity, eventEntity, routeArbitrationPacket, rewardVO);

		Assert.assertTrue(rewardVO.getBaseRep() == resultRepTest);
		Assert.assertTrue(rewardVO.getBaseCash() == resultCashTest);

	}

}
