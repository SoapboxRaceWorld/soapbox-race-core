package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.RewardVO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.EventEntity;
import com.soapboxrace.core.jpa.EventSessionEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.SkillModRewardType;
import com.soapboxrace.jaxb.http.Accolades;
import com.soapboxrace.jaxb.http.DragArbitrationPacket;

@Stateless
public class RewardDragBO extends RewardBO {

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private LegitRaceBO legitRaceBO;

	public Accolades getDragAccolades(Long activePersonaId, DragArbitrationPacket dragArbitrationPacket, EventSessionEntity eventSessionEntity) {
		if (!legitRaceBO.isLegit(activePersonaId, dragArbitrationPacket, eventSessionEntity)) {
			return new Accolades();
		}
		EventEntity eventEntity = eventSessionEntity.getEvent();
		PersonaEntity personaEntity = personaDao.findById(activePersonaId);
		RewardVO rewardVO = getRewardVO(personaEntity);

		setBaseReward(personaEntity, eventEntity, dragArbitrationPacket, rewardVO);
		setRankReward(eventEntity, dragArbitrationPacket, rewardVO);
		setPerfectStartReward(eventEntity, dragArbitrationPacket.getPerfectStart(), rewardVO);
		setTopSpeedReward(eventEntity, dragArbitrationPacket.getTopSpeed(), rewardVO);
		setSkillMultiplierReward(personaEntity, rewardVO, SkillModRewardType.SOCIALITE);
		setMultiplierReward(eventEntity, rewardVO);

		applyRaceReward(rewardVO.getRep(), rewardVO.getCash(), personaEntity);
		return getAccolades(personaEntity, dragArbitrationPacket, rewardVO);
	}
}
