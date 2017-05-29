package com.soapboxrace.core.bo;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.soapboxrace.core.dao.CarSlotDAO;
import com.soapboxrace.core.dao.PersonaDAO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.jaxb.http.CommerceSessionTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.util.MarshalXML;

@Stateless
public class CommerceBO {

	@EJB
	private CarSlotDAO carSlotDAO;
	
	@EJB
	private PersonaDAO personaDAO;
	
	@EJB
	private PersonaBO personaBO;

	public void updateCar(CommerceSessionTrans commerceSessionTrans, Long personaId) {
		OwnedCarTrans ownedCarTrans = personaBO.getDefaultCar(personaId);
		ownedCarTrans.getCustomCar().setVinyls(commerceSessionTrans.getUpdatedCar().getCustomCar().getVinyls());
		ownedCarTrans.getCustomCar().setPaints(commerceSessionTrans.getUpdatedCar().getCustomCar().getPaints());
		ownedCarTrans.getCustomCar().setSkillModParts(commerceSessionTrans.getUpdatedCar().getCustomCar().getSkillModParts());
		ownedCarTrans.getCustomCar().setPerformanceParts(commerceSessionTrans.getUpdatedCar().getCustomCar().getPerformanceParts());
		ownedCarTrans.getCustomCar().setVisualParts(commerceSessionTrans.getUpdatedCar().getCustomCar().getVisualParts());
		ownedCarTrans.setOwnershipType("CustomizedCar");
		ownedCarTrans.setHeat(1);

		CarSlotEntity carSlotEntity = carSlotDAO.findById(commerceSessionTrans.getUpdatedCar().getId());
		if(carSlotEntity != null) {
			carSlotEntity.setOwnedCarTrans(MarshalXML.marshal(ownedCarTrans));
			carSlotDAO.update(carSlotEntity);
		}
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
