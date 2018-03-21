package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.OwnedCarDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.OwnedCarEntity;

@Stateless
public class CarDamageBO {

	@EJB
	private CarSlotDAO carSlotDao;

	@EJB
	private OwnedCarDAO ownedCarDAO;

	@EJB
	private ParameterBO parameterBO;

	public Integer updateDamageCar(Long personaId, Long carId, Integer numberOfCollision, Long eventDuration) {
		if (!parameterBO.getBoolParam("ENABLE_CAR_DAMAGE")) {
			return 100;
		}
		OwnedCarEntity ownedCarEntity = ownedCarDAO.findById(carId);
		CarSlotEntity carSlotEntity = ownedCarEntity.getCarSlot();
		int durability = ownedCarEntity.getDurability();
		if (durability > 10) {
			Integer calcDamage = numberOfCollision + ((int) (eventDuration / 60000)) * 2;
			Integer newCarDamage = durability - calcDamage;
			ownedCarEntity.setDurability(newCarDamage < 10 ? 10 : newCarDamage);
			if (carSlotEntity != null) {
				carSlotDao.update(carSlotEntity);
			}
		}
		return ownedCarEntity.getDurability();
	}
}
