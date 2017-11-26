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
import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.core.bo.PersonaBO;
import com.soapboxrace.core.bo.TokenSessionBO;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.jaxb.http.ArrayOfCommerceItemTrans;
import com.soapboxrace.jaxb.http.ArrayOfCustomPaintTrans;
import com.soapboxrace.jaxb.http.ArrayOfCustomVinylTrans;
import com.soapboxrace.jaxb.http.ArrayOfInventoryItemTrans;
import com.soapboxrace.jaxb.http.ArrayOfOwnedCarTrans;
import com.soapboxrace.jaxb.http.ArrayOfPerformancePartTrans;
import com.soapboxrace.jaxb.http.ArrayOfProductTrans;
import com.soapboxrace.jaxb.http.ArrayOfSkillModPartTrans;
import com.soapboxrace.jaxb.http.ArrayOfVisualPartTrans;
import com.soapboxrace.jaxb.http.ArrayOfWalletTrans;
import com.soapboxrace.jaxb.http.BasketTrans;
import com.soapboxrace.jaxb.http.CarSlotInfoTrans;
import com.soapboxrace.jaxb.http.CommerceResultStatus;
import com.soapboxrace.jaxb.http.CommerceResultTrans;
import com.soapboxrace.jaxb.http.CommerceSessionResultTrans;
import com.soapboxrace.jaxb.http.CommerceSessionTrans;
import com.soapboxrace.jaxb.http.CustomCarTrans;
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

	@POST
	@Secured
	@Path("/{personaId}/commerce")
	@Produces(MediaType.APPLICATION_XML)
	public CommerceSessionResultTrans commerce(InputStream commerceXml, @HeaderParam("securityToken") String securityToken, @PathParam(value = "personaId") Long personaId) {
		sessionBO.verifyPersona(securityToken, personaId);
		
		PersonaEntity personaEntity = personaBO.getPersonaById(personaId);

		CommerceSessionResultTrans commerceSessionResultTrans = new CommerceSessionResultTrans();

		ArrayOfInventoryItemTrans arrayOfInventoryItemTrans = new ArrayOfInventoryItemTrans();
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(new InventoryItemTrans());

		WalletTrans walletTrans = new WalletTrans();
		walletTrans.setBalance(personaEntity.getCash());
		walletTrans.setCurrency("CASH");

		ArrayOfWalletTrans arrayOfWalletTrans = new ArrayOfWalletTrans();
		arrayOfWalletTrans.getWalletTrans().add(walletTrans);

		CommerceSessionTrans commerceSessionTrans = (CommerceSessionTrans) UnmarshalXML.unMarshal(commerceXml, CommerceSessionTrans.class);
		commerceSessionTrans.getUpdatedCar().setDurability(100);
		
		boolean premium = sessionBO.isPremium(securityToken);
		if (parameterBO.getPremiumCarChangerProtection() && !premium) {
			CustomCarTrans customCar = commerceSessionTrans.getUpdatedCar().getCustomCar();
			if (!customCar.getPerformanceParts().getPerformancePartTrans().isEmpty()) {
				customCar.setPerformanceParts(new ArrayOfPerformancePartTrans());
				customCar.setVinyls(new ArrayOfCustomVinylTrans());
				customCar.setSkillModParts(new ArrayOfSkillModPartTrans());
				customCar.setPaints(new ArrayOfCustomPaintTrans());
				customCar.setVisualParts(new ArrayOfVisualPartTrans());
			}
		}

		commerceSessionResultTrans.setInvalidBasket(new InvalidBasketTrans());
		commerceSessionResultTrans.setInventoryItems(arrayOfInventoryItemTrans);
		commerceSessionResultTrans.setStatus(commerceBO.updateCar(commerceSessionTrans, personaEntity));
		commerceSessionResultTrans.setUpdatedCar(commerceBO.responseCar(commerceSessionTrans));
		commerceSessionResultTrans.setWallets(arrayOfWalletTrans);
		return commerceSessionResultTrans;
	}

	@POST
	@Secured
	@Path("/{personaId}/baskets")
	@Produces(MediaType.APPLICATION_XML)
	public CommerceResultTrans baskets(@HeaderParam("securityToken") String securityToken, InputStream basketXml, @PathParam(value = "personaId") Long personaId) {
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

		BasketTrans basketTrans = (BasketTrans) UnmarshalXML.unMarshal(basketXml, BasketTrans.class);
		String productId = basketTrans.getItems().getBasketItemTrans().get(0).getProductId();
		if("-1".equals(productId) || "SRV-GARAGESLOT".equals(productId) || "SRV-POWERUP".equals(productId) || "SRV-THREVIVE".equals(productId)) {
			commerceResultTrans.setStatus(CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS);
		} else if("SRV-REPAIR".equals(productId)) {
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
			String ownedCarTransXml = carSlotEntity.getOwnedCarTrans();
			OwnedCarTrans ownedCarTrans = UnmarshalXML.unMarshal(ownedCarTransXml, OwnedCarTrans.class);
			ownedCarTrans.setId(carSlotEntity.getId());
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
	public InventoryTrans inventoryObjects() {
		InventoryTrans inventoryTrans = new InventoryTrans();
		ArrayOfInventoryItemTrans arrayOfInventoryItemTrans = new ArrayOfInventoryItemTrans();
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("nosshot", -1681514783, 1842996427L, "0x9bc61ee1"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("runflattires", -537557654, 2876729160L, "0xdff5856a"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("instantcooldown", -1692359144, 2876729162L, "0x9b20a618"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("shield", -364944936, 2876729163L, "0xea3f61d8"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("slingshot", 2236629, 2876729164L, "0x2220d5"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("ready", 957701799, 2876729165L, "0x39155ea7"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("juggernaut", 1805681994, 2876729166L, "0x6ba0854a"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("emergencyevade", -611661916, 2876729167L, "0xdb8ac7a4"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("team_emergencyevade", -1564932069, 2876729168L, "0xa2b9081b"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("onemorelap", 1627606782, 2876729170L, "0x61034efe"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("team_slingshot", 1113720384, 2876729171L, "0x42620640"));
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(getPowerUpInventory("trafficmagnet", 125509666, 2880783203L, "0x77b2022"));
		inventoryTrans.setInventoryItems(arrayOfInventoryItemTrans);
		return inventoryTrans;
	}

	private InventoryItemTrans getPowerUpInventory(String tag, int hash, long invId, String strHash) {
		InventoryItemTrans inventoryItemTrans = new InventoryItemTrans();
		inventoryItemTrans.setEntitlementTag("nosshot");
		inventoryItemTrans.setHash(hash);
		inventoryItemTrans.setInventoryId(invId);
		inventoryItemTrans.setProductId("DO NOT USE ME");
		inventoryItemTrans.setRemainingUseCount(100L);
		inventoryItemTrans.setResellPrice(0.00);
		inventoryItemTrans.setStatus("ACTIVE");
		inventoryItemTrans.setStringHash(strHash);
		inventoryItemTrans.setVirtualItemType("powerup");
		return inventoryItemTrans;
	}

	@POST
	@Secured
	@Path("/{personaId}/cars")
	@Produces(MediaType.APPLICATION_XML)
	public String carsPost(@PathParam(value = "personaId") Long personaId, @QueryParam("serialNumber") Long serialNumber, @HeaderParam("securityToken") String securityToken) {
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
			String ownedCarTransXml = carSlotEntity.getOwnedCarTrans();
			OwnedCarTrans ownedCarTrans = UnmarshalXML.unMarshal(ownedCarTransXml, OwnedCarTrans.class);
			ownedCarTrans.setId(carSlotEntity.getId());
			arrayOfOwnedCarTrans.getOwnedCarTrans().add(ownedCarTrans);
		}
		return arrayOfOwnedCarTrans;
	}

	@PUT
	@Secured
	@Path("/{personaId}/cars")
	@Produces(MediaType.APPLICATION_XML)
	public String carsPut(@PathParam(value = "personaId") Long personaId, @HeaderParam("securityToken") String securityToken) {
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
	public String defaultcar(@PathParam(value = "personaId") Long personaId, @PathParam(value = "carId") Long carId, @HeaderParam("securityToken") String securityToken) {
		sessionBO.verifyPersona(securityToken, personaId);
		personaBO.changeDefaultCar(personaId, carId);
		return "";
	}

}
