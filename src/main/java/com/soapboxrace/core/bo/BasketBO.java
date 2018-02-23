package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.dao.BasketDefinitionDAO;
import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.CustomCarDAO;
import com.soapboxrace.core.dao.InventoryDAO;
import com.soapboxrace.core.dao.InventoryItemDAO;
import com.soapboxrace.core.dao.OwnedCarDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.TokenSessionDAO;
import com.soapboxrace.core.jpa.BasketDefinitionEntity;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.CustomCarEntity;
import com.soapboxrace.core.jpa.InventoryEntity;
import com.soapboxrace.core.jpa.InventoryItemEntity;
import com.soapboxrace.core.jpa.OwnedCarEntity;
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
	private OwnedCarDAO ownedCarDAO;

	@EJB
	private CustomCarDAO customCarDAO;

	@EJB
	private TokenSessionDAO tokenDAO;

	@EJB
	private ProductDAO productDao;

	@EJB
	private PersonaDAO personaDao;

	@EJB
	private TokenSessionBO tokenSessionBO;

	@EJB
	private InventoryDAO inventoryDao;

	@EJB
	private InventoryItemDAO inventoryItemDao;

	private OwnedCarTrans getCar(String productId) {
		BasketDefinitionEntity basketDefinitonEntity = basketDefinitionsDAO.findById(productId);
		if (basketDefinitonEntity == null) {
			throw new IllegalArgumentException(String.format("No basket definition for %s", productId));
		}
		String ownedCarTrans = basketDefinitonEntity.getOwnedCarTrans();
		return UnmarshalXML.unMarshal(ownedCarTrans, OwnedCarTrans.class);
	}

	public CommerceResultStatus repairCar(String productId, PersonaEntity personaEntity) {
		OwnedCarTrans defaultCar = personaBo.getDefaultCar(personaEntity.getPersonaId());

		int price = (int) (productDao.findByProductId(productId).getPrice() * (100 - defaultCar.getDurability()));
		if (personaEntity.getCash() < price) {
			return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
		}
		if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
			personaEntity.setCash(personaEntity.getCash() - price);
		}
		personaDao.update(personaEntity);

		defaultCar.setDurability(100);

		CarSlotEntity defaultCarEntity = personaBo.getDefaultCarEntity(personaEntity.getPersonaId());
		defaultCarEntity.setOwnedCarTrans(MarshalXML.marshal(defaultCar));

		carSlotDAO.update(defaultCarEntity);
		return CommerceResultStatus.SUCCESS;
	}

	public CommerceResultStatus buyPowerups(String productId, PersonaEntity personaEntity) {
		if (!parameterBO.getBoolParam("ENABLE_ECONOMY")) {
			return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
		}
		ProductEntity powerupProduct = productDao.findByProductId(productId);
		InventoryEntity inventoryEntity = inventoryDao.findByPersonaId(personaEntity.getPersonaId());

		if (powerupProduct == null) {
			return CommerceResultStatus.FAIL_INVALID_BASKET;
		}

		if (personaEntity.getCash() < powerupProduct.getPrice()) {
			return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
		}

		InventoryItemEntity item = null;

		for (InventoryItemEntity i : inventoryEntity.getItems()) {
			if (i.getHash() == powerupProduct.getHash()) {
				item = i;
				break;
			}
		}

		if (item == null) {
			return CommerceResultStatus.FAIL_INVALID_BASKET;
		}

		boolean upgradedAmount = false;

		int newUsageCount = item.getRemainingUseCount() + 15;

		if (newUsageCount > 250)
			newUsageCount = 250;

		if (item.getRemainingUseCount() != newUsageCount)
			upgradedAmount = true;

		item.setRemainingUseCount(newUsageCount);
		inventoryItemDao.update(item);

		if (upgradedAmount) {
			personaEntity.setCash(personaEntity.getCash() - powerupProduct.getPrice());
			personaDao.update(personaEntity);
		}

		return CommerceResultStatus.SUCCESS;
	}

	public CommerceResultStatus buyCar(String productId, PersonaEntity personaEntity, String securityToken) {
		if (getPersonaCarCount(personaEntity.getPersonaId()) >= parameterBO.getCarLimit(securityToken)) {
			return CommerceResultStatus.FAIL_INSUFFICIENT_CAR_SLOTS;
		}

		ProductEntity productEntity = productDao.findByProductId(productId);
		if (personaEntity.getCash() < productEntity.getPrice()) {
			return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
		}

		OwnedCarTrans ownedCarTrans = getCar(productId);
		ownedCarTrans.setId(0L);
		ownedCarTrans.getCustomCar().setId(0);
		CarSlotEntity carSlotEntity = new CarSlotEntity();
		carSlotEntity.setPersona(personaEntity);

		OwnedCarEntity ownedCarEntity = new OwnedCarEntity();
		ownedCarEntity.setCarSlot(carSlotEntity);
		CustomCarEntity customCarEntity = new CustomCarEntity();
		customCarEntity.setOwnedCar(ownedCarEntity);
		ownedCarEntity.setCustomCar(customCarEntity);
		carSlotEntity.setOwnedCar(ownedCarEntity);
		OwnedCarConverter.Trans2Entity(ownedCarTrans, ownedCarEntity);

		carSlotDAO.insert(carSlotEntity);

		if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
			personaEntity.setCash(personaEntity.getCash() - productEntity.getPrice());
		}
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
		if (personaEntity.getCash() < maxCash) {
			OwnedCarTrans car = UnmarshalXML.unMarshal(carSlotEntity.getOwnedCarTrans(), OwnedCarTrans.class);
			int cashTotal = (int) (personaEntity.getCash() + car.getCustomCar().getResalePrice());
			if (parameterBO.getBoolParam("ENABLE_ECONOMY")) {
				personaEntity.setCash(Math.max(0, Math.min(maxCash, cashTotal)));
			}
			personaDao.update(personaEntity);
		}

		carSlotDAO.delete(carSlotEntity);
		return true;
	}

}
