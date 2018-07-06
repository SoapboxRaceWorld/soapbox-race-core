package com.soapboxrace.core.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.*;
import com.soapboxrace.jaxb.http.BadgeBundle;
import com.soapboxrace.jaxb.http.BadgeInput;
import com.soapboxrace.jaxb.http.BadgePacket;
import com.soapboxrace.jaxb.http.OwnedCarTrans;

@Stateless
public class PersonaBO {

	@EJB
	private PersonaDAO personaDAO;

	@EJB
	private CarSlotDAO carSlotDAO;

	@EJB
	private LevelRepDAO levelRepDAO;

	@EJB
	private OwnedCarDAO ownedCarDAO;
	
	@EJB
	private BadgeDefinitionDAO badgeDefinitionDAO;
	
	@EJB
	private AchievementDAO achievementDAO;
	
	@EJB
	private PersonaAchievementRankDAO personaAchievementRankDAO;

	public void updateBadges(long idPersona, BadgeBundle badgeBundle) {
		PersonaEntity persona = personaDAO.findById(idPersona);

		List<BadgePacket> badgePackets = new ArrayList<>();

		for (BadgeInput input : badgeBundle.getBadgeInputs()) {
			BadgeDefinitionEntity badge = badgeDefinitionDAO.findById((long) input.getBadgeDefinitionId());
			if (badge == null) continue;

			AchievementDefinitionEntity achievement = achievementDAO.findByBadgeId(badge.getId());

			if (achievement == null) continue;

			BadgePacket packet = new BadgePacket();
			packet.setSlotId(input.getSlotId());
			packet.setBadgeDefinitionId(badge.getId().intValue());

			List<AchievementRankEntity> ranks = personaAchievementRankDAO
					.findAllForPersonaAchievement(persona, achievement)
					.stream()
					.filter(pr -> !pr.getState().equals("Locked"))
					.map(PersonaAchievementRankEntity::getRank)
					.collect(Collectors.toList());

			if (ranks.isEmpty()) {
				packet.setIsRare(false);
				packet.setRarity(0f);
				packet.setAchievementRankId(-1);
			} else {
				packet.setIsRare(ranks.get(ranks.size() - 1).isRare());
				packet.setRarity(0.0f);
				packet.setAchievementRankId(Math.toIntExact(ranks.get(ranks.size() - 1).getId()));
			}

			badgePackets.add(packet);
		}

		persona.setBadges(badgePackets);
		personaDAO.update(persona);
	}
	
	public void changeDefaultCar(Long personaId, Long defaultCarId) {
		PersonaEntity personaEntity = personaDAO.findById(personaId);
		List<CarSlotEntity> carSlotList = carSlotDAO.findByPersonaId(personaId);
		int i = 0;
		for (CarSlotEntity carSlotEntity : carSlotList) {
			if (carSlotEntity.getOwnedCar().getId().equals(defaultCarId)) {
				break;
			}
			i++;
		}
		personaEntity.setCurCarIndex(i);
		personaDAO.update(personaEntity);
	}

	public PersonaEntity getPersonaById(Long personaId) {
		return personaDAO.findById(personaId);
	}

	public CarSlotEntity getDefaultCarEntity(Long personaId) {
		PersonaEntity personaEntity = personaDAO.findById(personaId);
		List<CarSlotEntity> carSlotList = getPersonasCar(personaId);
		Integer curCarIndex = personaEntity.getCurCarIndex();
		if (!carSlotList.isEmpty()) {
			if (curCarIndex >= carSlotList.size()) {
				curCarIndex = carSlotList.size() - 1;
				CarSlotEntity ownedCarEntity = carSlotList.get(curCarIndex);
				changeDefaultCar(personaId, ownedCarEntity.getId());
			}
			CarSlotEntity carSlotEntity = carSlotList.get(curCarIndex);
			CustomCarEntity customCar = carSlotEntity.getOwnedCar().getCustomCar();
			customCar.getPaints().size();
			customCar.getPerformanceParts().size();
			customCar.getSkillModParts().size();
			customCar.getVisualParts().size();
			customCar.getVinyls().size();
			return carSlotEntity;
		}
		return null;
	}

	public OwnedCarTrans getDefaultCar(Long personaId) {
		CarSlotEntity carSlotEntity = getDefaultCarEntity(personaId);
		if (carSlotEntity == null) {
			return new OwnedCarTrans();
		}
		return OwnedCarConverter.entity2Trans(carSlotEntity.getOwnedCar());
	}

	public List<CarSlotEntity> getPersonasCar(Long personaId) {
		return carSlotDAO.findByPersonaId(personaId);
	}

	public LevelRepEntity getLevelInfoByLevel(Long level) {
		return levelRepDAO.findByLevel(level);
	}

	public OwnedCarEntity getCarByOwnedCarId(Long ownedCarId) {
		OwnedCarEntity ownedCarEntity = ownedCarDAO.findById(ownedCarId);
		CustomCarEntity customCar = ownedCarEntity.getCustomCar();
		customCar.getPaints().size();
		customCar.getPerformanceParts().size();
		customCar.getSkillModParts().size();
		customCar.getVisualParts().size();
		customCar.getVinyls().size();
		return ownedCarEntity;
	}

}
