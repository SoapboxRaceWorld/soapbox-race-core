package com.soapboxrace.core.bo;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.dao.ProductDAO;
import com.soapboxrace.core.dao.VinylProductDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.BasketItemTrans;
import com.soapboxrace.jaxb.http.CommerceResultStatus;
import com.soapboxrace.jaxb.http.CommerceSessionTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.util.MarshalXML;

@Stateless
public class CommerceBO {

	@EJB
	private PersonaBO personaBO;

	@EJB
	private CarSlotDAO carSlotDAO;

	@EJB
	private PersonaDAO personaDAO;

	@EJB
	private ProductDAO productDao;

	@EJB
	private VinylProductDAO vinylProductDao;

	public CommerceResultStatus updateCar(CommerceSessionTrans commerceSessionTrans, PersonaEntity personaEntity) {
		OwnedCarTrans ownedCarTrans = personaBO.getDefaultCar(personaEntity.getPersonaId());
		ownedCarTrans.getCustomCar().setVinyls(commerceSessionTrans.getUpdatedCar().getCustomCar().getVinyls());
		ownedCarTrans.getCustomCar().setPaints(commerceSessionTrans.getUpdatedCar().getCustomCar().getPaints());
		ownedCarTrans.getCustomCar().setSkillModParts(commerceSessionTrans.getUpdatedCar().getCustomCar().getSkillModParts());
		ownedCarTrans.getCustomCar().setPerformanceParts(commerceSessionTrans.getUpdatedCar().getCustomCar().getPerformanceParts());
		ownedCarTrans.getCustomCar().setVisualParts(commerceSessionTrans.getUpdatedCar().getCustomCar().getVisualParts());
		ownedCarTrans.setOwnershipType("CustomizedCar");
		ownedCarTrans.setHeat(1);

		CarSlotEntity carSlotEntity = carSlotDAO.findById(commerceSessionTrans.getUpdatedCar().getId());
		if (carSlotEntity != null) {
			List<BasketItemTrans> listBasketItemTrans = commerceSessionTrans.getBasket().getItems().getBasketItemTrans();
			if(!listBasketItemTrans.isEmpty()) { // if buy or install perf part
				int maxBuyPrice = 0;
				for(BasketItemTrans basketItemTrans : listBasketItemTrans) {
					if(basketItemTrans.getProductId().contains("SRV-VINYL")) {
						maxBuyPrice += (vinylProductDao.findByProductId(basketItemTrans.getProductId()).getPrice() * basketItemTrans.getQuantity());
					} else {
						maxBuyPrice += (productDao.findByProductId(basketItemTrans.getProductId()).getPrice() * basketItemTrans.getQuantity());
					}
				}
				if(maxBuyPrice > 0) {
					if(personaEntity.getCash() < maxBuyPrice) {
						return CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS;
					}
					
					personaEntity.setCash(personaEntity.getCash() - maxBuyPrice);
					personaDAO.update(personaEntity);
				}
			} else { // else i sell some part from my inventory
				
			}
			
			carSlotEntity.setOwnedCarTrans(MarshalXML.marshal(ownedCarTrans));
			carSlotDAO.update(carSlotEntity);
			return CommerceResultStatus.SUCCESS;
		}
		return CommerceResultStatus.FAIL_INVALID_BASKET;
	}

	public OwnedCarTrans responseCar(CommerceSessionTrans commerceSessionTrans) {
		OwnedCarTrans ownedCarTrans = new OwnedCarTrans();
		ownedCarTrans.setCustomCar(commerceSessionTrans.getUpdatedCar().getCustomCar());
		ownedCarTrans.setDurability(commerceSessionTrans.getUpdatedCar().getDurability());
		ownedCarTrans.setHeat(commerceSessionTrans.getUpdatedCar().getHeat());
		ownedCarTrans.setId(commerceSessionTrans.getUpdatedCar().getId());
		ownedCarTrans.setOwnershipType(commerceSessionTrans.getUpdatedCar().getOwnershipType());
		return ownedCarTrans;
	}

}
