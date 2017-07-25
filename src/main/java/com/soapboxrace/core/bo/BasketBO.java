package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.BasketDefinitionDAO;
import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.TokenSessionDAO;
import com.soapboxrace.core.jpa.BasketDefinitionEntity;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.CommerceResultStatus;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;

@Stateless
public class BasketBO {

	@EJB
	private PersonaBO personaBo;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private BasketDefinitionDAO basketDefinitionsDAO;

	@EJB
	private CarSlotDAO carSlotDAO;

	@EJB
	private TokenSessionDAO tokenDAO;

	@EJB
	private ProductDAO productDao;

	@EJB
	private PersonaDAO personaDao;

	public OwnedCarTrans getCar(String productId) {
		BasketDefinitionEntity basketDefinitonEntity = basketDefinitionsDAO.findById(productId);
		if (basketDefinitonEntity == null) {
			return new OwnedCarTrans();
		}
		String ownedCarTrans = basketDefinitonEntity.getOwnedCarTrans();
		return (OwnedCarTrans) UnmarshalXML.unMarshal(ownedCarTrans, OwnedCarTrans.class);
	}

	public CommerceResultStatus repairCar(String productId, PersonaEntity personaEntity) {
		OwnedCarTrans defaultCar = personaBo.getDefaultCar(personaEntity.getPersonaId());
		
		int price = (int)(productDao.findByProductId(productId).getPrice() * (100 - defaultCar.getDurability()));
		if(personaEntity.getCash() < price) {
			return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
		}
		personaEntity.setCash(personaEntity.getCash() - price);
		personaDao.update(personaEntity);
		
		defaultCar.setDurability(100);
		
		CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaEntity.getPersonaId());
		defaultCarEntity.setOwnedCarTrans(MarshalXML.marshal(defaultCar));
		
		carSlotDAO.update(defaultCarEntity);
		return CommerceResultStatus.SUCCESS;
	}

	public CommerceResultStatus buyCar(String productId, PersonaEntity personaEntity, String securityToken) {
		if(getPersonaCarCount(personaEntity.getPersonaId()) >= parameterBO.getCarLimit(securityToken))
			return CommerceResultStatus.FAIL_INSUFFICIENT_CAR_SLOTS;
		
		ProductEntity productEntity = productDao.findByProductId(productId);
		if(personaEntity.getCash() < productEntity.getPrice())
			return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
		
		String carXml = MarshalXML.marshal(getCar(productId));
		CarSlotEntity carSlotEntity = new CarSlotEntity();
		carSlotEntity.setPersona(personaEntity);
		carSlotEntity.setOwnedCarTrans(carXml);
		carSlotDAO.insert(carSlotEntity);
		
		personaEntity.setCash(personaEntity.getCash() - productEntity.getPrice());
		personaDao.update(personaEntity);
		
		personaBo.changeDefaultCar(personaEntity.getPersonaId(), carSlotEntity.getId());
		return CommerceResultStatus.SUCCESS;
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
		
		PersonaEntity personaEntity = personaDao.findById(personaId);
		if(personaEntity.getCash() < 9999999) {
			OwnedCarTrans defaultCar = personaBo.getDefaultCar(personaId);
			int cashTotal = (int)(personaEntity.getCash() + defaultCar.getCustomCar().getResalePrice());
			personaEntity.setCash(cashTotal >= 9999999 ? 9999999 : cashTotal);
			personaDao.update(personaEntity);
		}
		
		carSlotDAO.delete(carSlotEntity);
		return true;
	}

}
