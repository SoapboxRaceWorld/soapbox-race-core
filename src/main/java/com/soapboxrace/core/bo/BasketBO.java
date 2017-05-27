package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.BasketDefinitionDAO;
import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.jpa.BasketDefinitionEntity;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.ArrayOfCustomPaintTrans;
import com.soapboxrace.jaxb.http.ArrayOfCustomVinylTrans;
import com.soapboxrace.jaxb.http.ArrayOfPerformancePartTrans;
import com.soapboxrace.jaxb.http.ArrayOfSkillModPartTrans;
import com.soapboxrace.jaxb.http.ArrayOfVisualPartTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;

@Stateless
public class BasketBO {

	@EJB
	private BasketDefinitionDAO basketDefinitionsDAO;

	@EJB
	private CarSlotDAO carSlotDAO;

	public OwnedCarTrans getCar(String productId) {
		BasketDefinitionEntity basketDefinitonEntity = basketDefinitionsDAO.findById(productId);
		String ownedCarTrans = basketDefinitonEntity.getOwnedCarTrans();
		return (OwnedCarTrans) UnmarshalXML.unMarshal(ownedCarTrans, OwnedCarTrans.class);
	}

	public boolean buyCar(String productId, Long personaId) {
		if (getPersonaCarCount(personaId) < 6) {
			OwnedCarTrans car = getCar(productId);
			// clear car parts to avoid other safehouse menus----
			car.getCustomCar().setVinyls(new ArrayOfCustomVinylTrans());
			// car.getCustomCar().setPaints(new ArrayOfCustomPaintTrans());
			car.getCustomCar().setSkillModParts(new ArrayOfSkillModPartTrans());
			car.getCustomCar().setPerformanceParts(new ArrayOfPerformancePartTrans());
			car.getCustomCar().setVisualParts(new ArrayOfVisualPartTrans());
			// ----clear car parts to avoid other safehouse menus
			String carXml = MarshalXML.marshal(car);
			CarSlotEntity carSlotEntity = new CarSlotEntity();
			PersonaEntity personaEntity = new PersonaEntity();
			personaEntity.setPersonaId(personaId);
			carSlotEntity.setPersona(personaEntity);
			carSlotEntity.setOwnedCarTrans(carXml);
			carSlotDAO.insert(carSlotEntity);
			return true;
		}
		return false;
	}

	private int getPersonaCarCount(Long personaId) {
		return getPersonasCar(personaId).size();
	}

	public List<CarSlotEntity> getPersonasCar(Long personaId) {
		return carSlotDAO.findByPersonaId(personaId);
	}

	public CarSlotEntity sellCar(Long personaId, Long serialNumber) {
		CarSlotEntity carSlotEntity = carSlotDAO.findById(serialNumber);
		int personaCarCount = getPersonaCarCount(personaId);
		if (personaCarCount > 1) {
			carSlotDAO.delete(carSlotEntity);
			return carSlotEntity;
		}
		return null;
	}

}
