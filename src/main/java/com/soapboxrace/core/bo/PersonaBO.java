package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.util.UnmarshalXML;

@Stateless
public class PersonaBO {

	@EJB
	private PersonaDAO personaDAO;

	@EJB
	private CarSlotDAO carSlotDAO;

	public void changeDefaultCar(Long personaId, Long defaultCarId) {
		PersonaEntity personaEntity = personaDAO.findById(personaId);
		List<CarSlotEntity> carSlotList = carSlotDAO.findByPersonaId(personaId);
		int i = 0;
		for (CarSlotEntity carSlotEntity : carSlotList) {
			if (carSlotEntity.getId().equals(defaultCarId)) {
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

	public OwnedCarTrans getDefaultCar(Long personaId) {
		PersonaEntity personaEntity = personaDAO.findById(personaId);
		List<CarSlotEntity> carSlotList = getPersonasCar(personaId);
		Integer curCarIndex = personaEntity.getCurCarIndex();
		if (carSlotList.size() > 0) {
			if (curCarIndex >= carSlotList.size()) {
				curCarIndex--;
				CarSlotEntity ownedCarEntity = carSlotList.get(curCarIndex);
				changeDefaultCar(personaId, ownedCarEntity.getId());
			}
			CarSlotEntity carSlotEntity = carSlotList.get(curCarIndex);
			OwnedCarTrans ownedCarTrans = (OwnedCarTrans) UnmarshalXML.unMarshal(carSlotEntity.getOwnedCarTrans(), OwnedCarTrans.class);
			ownedCarTrans.setId(carSlotEntity.getId());
			return ownedCarTrans;
		}
		return new OwnedCarTrans();
	}

	public List<CarSlotEntity> getPersonasCar(Long personaId) {
		return carSlotDAO.findByPersonaId(personaId);
	}

}
