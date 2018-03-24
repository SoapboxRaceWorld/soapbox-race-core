package com.soapboxrace.core.api;

import java.io.InputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.BasketBO;
import com.soapboxrace.core.bo.CommerceBO;
import com.soapboxrace.core.bo.InventoryBO;
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.bo.PersonaBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.bo.util.CommerceOp;
import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.OwnedCarEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.ArrayOfCommerceItemTrans;
import com.soapboxrace.jaxb.http.ArrayOfInventoryItemTrans;
import com.soapboxrace.jaxb.http.ArrayOfOwnedCarTrans;
import com.soapboxrace.jaxb.http.ArrayOfProductTrans;
import com.soapboxrace.jaxb.http.ArrayOfWalletTrans;
import com.soapboxrace.jaxb.http.BasketItemTrans;
import com.soapboxrace.jaxb.http.BasketTrans;
import com.soapboxrace.jaxb.http.CarSlotInfoTrans;
import com.soapboxrace.jaxb.http.CommerceResultStatus;
import com.soapboxrace.jaxb.http.CommerceResultTrans;
import com.soapboxrace.jaxb.http.CommerceSessionResultTrans;
import com.soapboxrace.jaxb.http.CommerceSessionTrans;
import com.soapboxrace.jaxb.http.InvalidBasketTrans;
import com.soapboxrace.jaxb.http.InventoryItemTrans;
import com.soapboxrace.jaxb.http.InventoryTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.http.ProductTrans;
import com.soapboxrace.jaxb.http.WalletTrans;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;

@Path("/personas")
public class Personas {

	@EJB
	private BasketBO basketBO;

	@EJB
	private PersonaBO personaBO;

	@EJB
	private CommerceBO commerceBO;

	@EJB
	private TokenSessionBO sessionBO;

	@EJB
	private ParameterBO parameterBO;

	@EJB
	private InventoryBO inventoryBO;

	@POST
	@Secured
	@Path("/{personaId}/commerce")
	@Produces(MediaType.APPLICATION_XML)
	public CommerceSessionResultTrans commerce(InputStream commerceXml, @HeaderParam("securityToken") String securityToken,
			@PathParam(value = "personaId") Long personaId) {
		sessionBO.verifyPersona(securityToken, personaId);

		CommerceSessionResultTrans commerceSessionResultTrans = new CommerceSessionResultTrans();

		CommerceSessionTrans commerceSessionTrans = UnmarshalXML.unMarshal(commerceXml, CommerceSessionTrans.class);
		List<BasketItemTrans> basketItemTrans = commerceSessionTrans.getBasket().getItems().getBasketItemTrans();
		CarSlotEntity defaultCarEntity = personaBO.getDefaultCarEntity(personaId);
		CommerceOp commerceOp = commerceBO.detectCommerceOperation(commerceSessionTrans, defaultCarEntity);
		commerceBO.updateEconomy(commerceOp, basketItemTrans, commerceSessionTrans, defaultCarEntity);
		inventoryBO.updateInventory(commerceOp, basketItemTrans, commerceSessionTrans, defaultCarEntity);
		commerceBO.updateCar(commerceOp, commerceSessionTrans, defaultCarEntity);

		commerceSessionResultTrans.setInvalidBasket(new InvalidBasketTrans());
		ArrayOfInventoryItemTrans arrayOfInventoryItemTrans = new ArrayOfInventoryItemTrans();
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(new InventoryItemTrans());

		WalletTrans walletTrans = new WalletTrans();
		walletTrans.setBalance(defaultCarEntity.getPersona().getCash());
		walletTrans.setCurrency("CASH");

		ArrayOfWalletTrans arrayOfWalletTrans = new ArrayOfWalletTrans();
		arrayOfWalletTrans.getWalletTrans().add(walletTrans);
		commerceSessionResultTrans.setInventoryItems(arrayOfInventoryItemTrans);
		commerceSessionResultTrans.setStatus(CommerceResultStatus.SUCCESS);
		commerceSessionResultTrans.setUpdatedCar(OwnedCarConverter.entity2Trans(defaultCarEntity.getOwnedCar()));
		commerceSessionResultTrans.setWallets(arrayOfWalletTrans);
		return commerceSessionResultTrans;
	}

	@POST
	@Secured
	@Path("/{personaId}/baskets")
	@Produces(MediaType.APPLICATION_XML)
	public CommerceResultTrans baskets(@HeaderParam("securityToken") String securityToken, InputStream basketXml,
			@PathParam(value = "personaId") Long personaId) {
		sessionBO.verifyPersona(securityToken, personaId);

		PersonaEntity personaEntity = personaBO.getPersonaById(personaId);

		CommerceResultTrans commerceResultTrans = new CommerceResultTrans();

		ArrayOfInventoryItemTrans arrayOfInventoryItemTrans = new ArrayOfInventoryItemTrans();
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(new InventoryItemTrans());

		WalletTrans walletTrans = new WalletTrans();
		walletTrans.setBalance(personaEntity.getCash());
		walletTrans.setCurrency("CASH");

		ArrayOfWalletTrans arrayOfWalletTrans = new ArrayOfWalletTrans();
		arrayOfWalletTrans.getWalletTrans().add(walletTrans);

		commerceResultTrans.setWallets(arrayOfWalletTrans);
		commerceResultTrans.setCommerceItems(new ArrayOfCommerceItemTrans());
		commerceResultTrans.setInvalidBasket(new InvalidBasketTrans());
		commerceResultTrans.setInventoryItems(arrayOfInventoryItemTrans);

		ArrayOfOwnedCarTrans arrayOfOwnedCarTrans = new ArrayOfOwnedCarTrans();

		BasketTrans basketTrans = UnmarshalXML.unMarshal(basketXml, BasketTrans.class);
		String productId = basketTrans.getItems().getBasketItemTrans().get(0).getProductId();
		if ("-1".equals(productId) || "SRV-GARAGESLOT".equals(productId) || "SRV-THREVIVE".equals(productId)) {
			commerceResultTrans.setStatus(CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS);
		} else if (productId.contains("SRV-POWERUP")) {
			commerceResultTrans.setStatus(basketBO.buyPowerups(productId, personaEntity));
		} else if ("SRV-REPAIR".equals(productId)) {
			commerceResultTrans.setStatus(basketBO.repairCar(productId, personaEntity));
		} else { // Car
			OwnedCarTrans ownedCarTrans = new OwnedCarTrans();
			commerceResultTrans.setPurchasedCars(arrayOfOwnedCarTrans);
			arrayOfOwnedCarTrans.getOwnedCarTrans().add(ownedCarTrans);

			commerceResultTrans.setStatus(basketBO.buyCar(productId, personaEntity, securityToken));
		}
		return commerceResultTrans;
	}

	@GET
	@Secured
	@Path("/{personaId}/carslots")
	@Produces(MediaType.APPLICATION_XML)
	public CarSlotInfoTrans carslots(@PathParam(value = "personaId") Long personaId, @HeaderParam("securityToken") String securityToken) {
		sessionBO.verifyPersona(securityToken, personaId);

		PersonaEntity personaEntity = personaBO.getPersonaById(personaId);
		List<CarSlotEntity> personasCar = basketBO.getPersonasCar(personaId);
		ArrayOfOwnedCarTrans arrayOfOwnedCarTrans = new ArrayOfOwnedCarTrans();
		for (CarSlotEntity carSlotEntity : personasCar) {
			OwnedCarEntity ownedCarEntity = carSlotEntity.getOwnedCar();
			OwnedCarTrans ownedCarTrans = OwnedCarConverter.entity2Trans(ownedCarEntity);
			arrayOfOwnedCarTrans.getOwnedCarTrans().add(ownedCarTrans);
		}
		CarSlotInfoTrans carSlotInfoTrans = new CarSlotInfoTrans();
		carSlotInfoTrans.setCarsOwnedByPersona(arrayOfOwnedCarTrans);
		carSlotInfoTrans.setDefaultOwnedCarIndex(personaEntity.getCurCarIndex());
		carSlotInfoTrans.setObtainableSlots(new ArrayOfProductTrans());
		int carlimit = parameterBO.getCarLimit(securityToken);
		carSlotInfoTrans.setOwnedCarSlotsCount(carlimit);
		ArrayOfProductTrans arrayOfProductTrans = new ArrayOfProductTrans();
		ProductTrans productTrans = new ProductTrans();
		productTrans.setBundleItems(new ArrayOfProductTrans());
		productTrans.setCategoryId("");
		productTrans.setCurrency("_NS");
		productTrans.setDescription("New car slot !!");
		productTrans.setDurationMinute(0);
		productTrans.setHash(-1143680669);
		productTrans.setIcon("128_cash");
		productTrans.setLevel(70);
		productTrans.setLongDescription("New car slot !");
		productTrans.setPrice(100.0000);
		productTrans.setPriority(0);
		productTrans.setProductId("SRV-GARAGESLOT");
		productTrans.setSecondaryIcon("");
		productTrans.setUseCount(1);
		productTrans.setVisualStyle("");
		productTrans.setWebIcon("");
		productTrans.setWebLocation("");
		arrayOfProductTrans.getProductTrans().add(productTrans);
		carSlotInfoTrans.setObtainableSlots(arrayOfProductTrans);
		return carSlotInfoTrans;
	}

	@GET
	@Secured
	@Path("/inventory/objects")
	@Produces(MediaType.APPLICATION_XML)
	public InventoryTrans inventoryObjects(@HeaderParam("securityToken") String securityToken) {
		long personaId = sessionBO.getActivePersonaId(securityToken);
		return inventoryBO.getInventory(personaId);
	}

	@GET
	@Secured
	@Path("/inventory/sell/{entitlementTag}")
	@Produces(MediaType.APPLICATION_XML)
	public String sellInventoryItem(@HeaderParam("securityToken") String securityToken, @PathParam("entitlementTag") String entitlementTag) {
		long personaId = sessionBO.getActivePersonaId(securityToken);
		inventoryBO.deletePart(personaId, entitlementTag);
		return "";
	}

	@POST
	@Secured
	@Path("/{personaId}/cars")
	@Produces(MediaType.APPLICATION_XML)
	public String carsPost(@PathParam(value = "personaId") Long personaId, @QueryParam("serialNumber") Long serialNumber,
			@HeaderParam("securityToken") String securityToken) {
		sessionBO.verifyPersona(securityToken, personaId);
		if (basketBO.sellCar(securityToken, personaId, serialNumber)) {
			OwnedCarTrans ownedCarTrans = personaBO.getDefaultCar(personaId);
			return MarshalXML.marshal(ownedCarTrans);
		}
		return "";
	}

	@GET
	@Secured
	@Path("/{personaId}/cars")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfOwnedCarTrans carsGet(@PathParam(value = "personaId") Long personaId) {
		ArrayOfOwnedCarTrans arrayOfOwnedCarTrans = new ArrayOfOwnedCarTrans();
		List<CarSlotEntity> personasCar = basketBO.getPersonasCar(personaId);
		for (CarSlotEntity carSlotEntity : personasCar) {
			OwnedCarTrans ownedCarTrans = OwnedCarConverter.entity2Trans(carSlotEntity.getOwnedCar());
			arrayOfOwnedCarTrans.getOwnedCarTrans().add(ownedCarTrans);
		}
		return arrayOfOwnedCarTrans;
	}

	@GET
	@Secured
	@Path("/{personaId}/cars/{carId}")
	@Produces(MediaType.APPLICATION_XML)
	public OwnedCarTrans carsGet(@PathParam(value = "personaId") Long personaId, @PathParam(value = "carId") Long carId) {
		OwnedCarEntity ownedCarEntity = personaBO.getCarByOwnedCarId(carId);
		return OwnedCarConverter.entity2Trans(ownedCarEntity);
	}

	@PUT
	@Secured
	@Path("/{personaId}/cars")
	@Produces(MediaType.APPLICATION_XML)
	public String carsPut(@PathParam(value = "personaId") Long personaId, @HeaderParam("securityToken") String securityToken, InputStream ownedCarXml) {
		// update car (skill and performance shop)
		sessionBO.verifyPersona(securityToken, personaId);
		OwnedCarTrans ownedCarTrans = personaBO.getDefaultCar(personaId);
		return MarshalXML.marshal(ownedCarTrans);
	}

	@GET
	@Secured
	@Path("/{personaId}/defaultcar")
	@Produces(MediaType.APPLICATION_XML)
	public OwnedCarTrans defaultcarGet(@PathParam(value = "personaId") Long personaId) {
		return personaBO.getDefaultCar(personaId);
	}

	@PUT
	@Secured
	@Path("/{personaId}/defaultcar/{carId}")
	@Produces(MediaType.APPLICATION_XML)
	public String defaultcar(@PathParam(value = "personaId") Long personaId, @PathParam(value = "carId") Long carId,
			@HeaderParam("securityToken") String securityToken) {
		sessionBO.verifyPersona(securityToken, personaId);
		personaBO.changeDefaultCar(personaId, carId);
		return "";
	}

}
