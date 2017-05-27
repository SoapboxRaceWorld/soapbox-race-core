package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;

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
}
