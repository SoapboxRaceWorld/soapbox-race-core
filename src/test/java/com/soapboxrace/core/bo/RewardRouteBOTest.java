package com.soapboxrace.core.bo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.RouteArbitrationPacket;

public class RewardRouteBOTest {

	private RewardRouteBO rewardRouteBO;
	private EventEntity eventEntity;
	private RouteArbitrationPacket routeArbitrationPacket;
	private RewardVO rewardVO;
	private PersonaEntity personaEntity;

	@Before
	public void mock() {
		rewardRouteBO = new RewardRouteBO();
		eventEntity = new EventEntity();
		routeArbitrationPacket = new RouteArbitrationPacket();
		rewardVO = new RewardVO(true, true);
		personaEntity = new PersonaEntity();
	}

	@Test
	public void testSetBaseRewardCash() throws Exception {
		Float baseCash = 1000f;
		Float levelCashRewardMultiplier = 0.1f;
		Float playerLevel = 2f;
		Float legitTime = 10000f;
		Float routeTime = 10000f;

		eventEntity.setBaseCashReward(baseCash.intValue());
		eventEntity.setLevelCashRewardMultiplier(levelCashRewardMultiplier);
		personaEntity.setLevel(playerLevel.intValue());
		eventEntity.setLegitTime(legitTime.longValue());
		routeArbitrationPacket.setEventDurationInMilliseconds(routeTime.longValue());
		rewardRouteBO.setBaseReward(personaEntity, eventEntity, routeArbitrationPacket, rewardVO);
		Float timeConst = legitTime.floatValue() / routeTime.floatValue();
		timeConst = Math.min(timeConst, 1);
		baseCash = baseCash.floatValue() * levelCashRewardMultiplier.floatValue() * playerLevel.floatValue() * timeConst.floatValue();
		Assert.assertTrue(rewardVO.getBaseCash() == baseCash.intValue());
		System.out.println(baseCash.intValue());
	}

}
