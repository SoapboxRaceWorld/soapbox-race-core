package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.*;
import com.soapboxrace.core.jpa.BasketDefinitionEntity;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.CommerceResultStatus;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

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
	
	@EJB
	private TokenSessionBO tokenSessionBO;
	
	public OwnedCarTrans getCar(String productId) {
		BasketDefinitionEntity basketDefinitonEntity = basketDefinitionsDAO.findById(productId);
		if (basketDefinitonEntity == null) {
			throw new IllegalArgumentException(String.format("No basket definition for %s", productId));
		}
		String ownedCarTrans = basketDefinitonEntity.getOwnedCarTrans();
		return UnmarshalXML.unMarshal(ownedCarTrans, OwnedCarTrans.class);
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
	
	public CommerceResultStatus buyPowerups(String productId, PersonaEntity personaEntity)
	{
		return CommerceResultStatus.FAIL_BOOST_TRANSACTIONS_ARE_DISABLED;
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

	public boolean sellCar(String securityToken, Long personaId, Long serialNumber) {
		this.tokenSessionBO.verifyPersona(securityToken, personaId);
		
		CarSlotEntity carSlotEntity = carSlotDAO.findById(serialNumber);
		int personaCarCount = getPersonaCarCount(personaId);
		if (carSlotEntity == null || personaCarCount <= 1) {
			return false;
		}
		
		PersonaEntity personaEntity = personaDao.findById(personaId);
		final int maxCash = parameterBO.getMaxCash(securityToken);
		if(personaEntity.getCash() < maxCash) {
			OwnedCarTrans car = UnmarshalXML.unMarshal(carSlotEntity.getOwnedCarTrans(), OwnedCarTrans.class);
			int cashTotal = (int)(personaEntity.getCash() + car.getCustomCar().getResalePrice());
			personaEntity.setCash(Math.max(0, Math.min(maxCash, cashTotal)));
			personaDao.update(personaEntity);
		}
		
		carSlotDAO.delete(carSlotEntity);
		return true;
	}

}
