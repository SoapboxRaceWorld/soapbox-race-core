package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.LevelRepDAO;
import com.soapboxrace.core.dao.OwnedCarDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.CustomCarEntity;
import com.soapboxrace.core.jpa.LevelRepEntity;
import com.soapboxrace.core.jpa.OwnedCarEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
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
