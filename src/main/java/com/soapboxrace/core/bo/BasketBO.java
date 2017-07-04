package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.BasketDefinitionDAO;
import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.TokenSessionDAO;
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

	@EJB
	private PersonaBO personaBo;

	@EJB
	private TokenSessionDAO tokenDAO;

	public OwnedCarTrans getCar(String productId) {
		BasketDefinitionEntity basketDefinitonEntity = basketDefinitionsDAO.findById(productId);
		if (basketDefinitonEntity == null) {
			return new OwnedCarTrans();
		}
		String ownedCarTrans = basketDefinitonEntity.getOwnedCarTrans();
		return (OwnedCarTrans) UnmarshalXML.unMarshal(ownedCarTrans, OwnedCarTrans.class);
	}

	public OwnedCarTrans repairCar(Long personaId) {
		CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaId);
		OwnedCarTrans defaultCar = personaBo.getDefaultCar(personaId);
		defaultCar.setDurability(100);
		String marshal = MarshalXML.marshal(defaultCar);
		defaultCarEntity.setOwnedCarTrans(marshal);
		carSlotDAO.update(defaultCarEntity);
		defaultCar.setId(defaultCarEntity.getId());
		return defaultCar;
	}

	public void buyCar(String productId, Long personaId, String securityToken) {
		OwnedCarTrans car = getCar(productId);
		String carXml = MarshalXML.marshal(car);
		CarSlotEntity carSlotEntity = new CarSlotEntity();
		PersonaEntity personaEntity = new PersonaEntity();
		personaEntity.setPersonaId(personaId);
		carSlotEntity.setPersona(personaEntity);
		carSlotEntity.setOwnedCarTrans(carXml);
		carSlotDAO.insert(carSlotEntity);
		personaBo.changeDefaultCar(personaId, carSlotEntity.getId());
	}

	public int getPersonaCarCount(Long personaId) {
		return getPersonasCar(personaId).size();
	}

	public List<CarSlotEntity> getPersonasCar(Long personaId) {
		return carSlotDAO.findByPersonaId(personaId);
	}

	public boolean sellCar(Long personaId, Long serialNumber) {
		CarSlotEntity carSlotEntity = carSlotDAO.findById(serialNumber);
		int personaCarCount = getPersonaCarCount(personaId);
		if (carSlotEntity == null || personaCarCount <= 1) {
			return false;
		}

		carSlotDAO.delete(carSlotEntity);
		return true;
	}

}
