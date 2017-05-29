package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.BasketDefinitionDAO;
import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.jpa.BasketDefinitionEntity;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
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
		if(getPersonaCarCount(personaId) >= 6) {
			return false;
		}
		
		OwnedCarTrans car = getCar(productId);
		String carXml = MarshalXML.marshal(car);
		CarSlotEntity carSlotEntity = new CarSlotEntity();
		PersonaEntity personaEntity = new PersonaEntity();
		personaEntity.setPersonaId(personaId);
		carSlotEntity.setPersona(personaEntity);
		carSlotEntity.setOwnedCarTrans(carXml);
		carSlotDAO.insert(carSlotEntity);		
		return true;
	}

	private int getPersonaCarCount(Long personaId) {
		return getPersonasCar(personaId).size();
	}

	public List<CarSlotEntity> getPersonasCar(Long personaId) {
		return carSlotDAO.findByPersonaId(personaId);
	}

	public boolean sellCar(Long personaId, Long serialNumber) {
		CarSlotEntity carSlotEntity = carSlotDAO.findById(serialNumber);
		int personaCarCount = getPersonaCarCount(personaId);
		if(carSlotEntity == null || personaCarCount <= 1) {
			return false;
		}

		carSlotDAO.delete(carSlotEntity);
		return true;
	}

}
