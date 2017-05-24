package com.soapboxrace.core.api;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.soapboxrace.core.api.util.Secured;
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
import com.soapboxrace.jaxb.http.CustomCarTrans;
import com.soapboxrace.jaxb.http.CustomPaintTrans;
import com.soapboxrace.jaxb.http.InvalidBasketTrans;
import com.soapboxrace.jaxb.http.InventoryItemTrans;
import com.soapboxrace.jaxb.http.InventoryTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.http.ProductTrans;
import com.soapboxrace.jaxb.http.WalletTrans;
import com.soapboxrace.jaxb.util.UnmarshalXML;

@Path("/personas")
public class Personas {

	@POST
	@Secured
	@Path("/{personaId}/baskets")
	@Produces(MediaType.APPLICATION_XML)
	public CommerceResultTrans baskets(InputStream basketXml, @PathParam(value = "personaId") Long personaId) {
		CommerceResultTrans commerceResultTrans = new CommerceResultTrans();

		ArrayOfInventoryItemTrans arrayOfInventoryItemTrans = new ArrayOfInventoryItemTrans();
		arrayOfInventoryItemTrans.getInventoryItemTrans().add(new InventoryItemTrans());

		WalletTrans walletTrans = new WalletTrans();
		walletTrans.setBalance(5000000);
		walletTrans.setCurrency("CASH");

		ArrayOfWalletTrans arrayOfWalletTrans = new ArrayOfWalletTrans();
		arrayOfWalletTrans.getWalletTrans().add(walletTrans);

		commerceResultTrans.setWallets(arrayOfWalletTrans);
		commerceResultTrans.setCommerceItems(new ArrayOfCommerceItemTrans());
		commerceResultTrans.setInvalidBasket(new InvalidBasketTrans());
		commerceResultTrans.setInventoryItems(arrayOfInventoryItemTrans);

		ArrayOfOwnedCarTrans arrayOfOwnedCarTrans = getArrayOfOwnedCarTransExample();
		commerceResultTrans.setPurchasedCars(arrayOfOwnedCarTrans);
		commerceResultTrans.setStatus(CommerceResultStatus.SUCCESS);

		BasketTrans basketTrans = (BasketTrans) UnmarshalXML.unMarshal(basketXml, BasketTrans.class);
		String productId = basketTrans.getItems().getBasketItemTrans().get(0).getProductId();
		if ("SRV-GARAGESLOT".equals(productId)) {
			commerceResultTrans.setStatus(CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS);
		}
		return commerceResultTrans;
	}

	@GET
	@Secured
	@Path("/{personaId}/carslots")
	@Produces(MediaType.APPLICATION_XML)
	public CarSlotInfoTrans carslots(@PathParam(value = "personaId") Long personaId) {
		CarSlotInfoTrans carSlotInfoTrans = new CarSlotInfoTrans();
		carSlotInfoTrans.setCarsOwnedByPersona(getArrayOfOwnedCarTransExample());
		carSlotInfoTrans.setDefaultOwnedCarIndex(0);
		carSlotInfoTrans.setObtainableSlots(new ArrayOfProductTrans());
		carSlotInfoTrans.setOwnedCarSlotsCount(1);
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

	private CustomPaintTrans getCustomPaintTrans(int slot) {
		CustomPaintTrans customPaintTrans = new CustomPaintTrans();
		customPaintTrans.setGroup(47885063);
		customPaintTrans.setHue(496032328);
		customPaintTrans.setSat(0);
		customPaintTrans.setVar(0);
		customPaintTrans.setSlot(slot);
		return customPaintTrans;
	}

	private ArrayOfCustomPaintTrans getArrayOfCustomPaintTrans() {
		ArrayOfCustomPaintTrans arrayOfCustomPaintTrans = new ArrayOfCustomPaintTrans();
		arrayOfCustomPaintTrans.getCustomPaintTrans().add(getCustomPaintTrans(0));
		arrayOfCustomPaintTrans.getCustomPaintTrans().add(getCustomPaintTrans(3));
		arrayOfCustomPaintTrans.getCustomPaintTrans().add(getCustomPaintTrans(4));
		arrayOfCustomPaintTrans.getCustomPaintTrans().add(getCustomPaintTrans(5));
		arrayOfCustomPaintTrans.getCustomPaintTrans().add(getCustomPaintTrans(6));
		arrayOfCustomPaintTrans.getCustomPaintTrans().add(getCustomPaintTrans(7));
		return arrayOfCustomPaintTrans;
	}

	private OwnedCarTrans getOwnedCarTransExample() {
		CustomCarTrans customCarTrans = new CustomCarTrans();
		customCarTrans.setId(12345678);
		customCarTrans.setBaseCar(12345678);
		customCarTrans.setIsPreset(true);
		customCarTrans.setCarClassHash(872416321);
		customCarTrans.setName("240sx");
		customCarTrans.setPhysicsProfileHash(-1469109252);
		customCarTrans.setSkillModSlotCount(5);

		customCarTrans.setPaints(getArrayOfCustomPaintTrans());
		customCarTrans.setPerformanceParts(new ArrayOfPerformancePartTrans());
		customCarTrans.setRating(224);
		customCarTrans.setResalePrice(65000);
		customCarTrans.setRideHeightDrop(0);
		customCarTrans.setSkillModParts(new ArrayOfSkillModPartTrans());
		customCarTrans.setVinyls(new ArrayOfCustomVinylTrans());
		customCarTrans.setVisualParts(new ArrayOfVisualPartTrans());
		OwnedCarTrans ownedCarTrans = new OwnedCarTrans();
		ownedCarTrans.setCustomCar(customCarTrans);
		ownedCarTrans.setId(123456L);
		ownedCarTrans.setDurability((short) 100);
		ownedCarTrans.setExpirationDate(null);
		ownedCarTrans.setHeat(0F);
		ownedCarTrans.setOwnershipType("PresetCar");

		return ownedCarTrans;
	}

	private ArrayOfOwnedCarTrans getArrayOfOwnedCarTransExample() {
		ArrayOfOwnedCarTrans arrayOfOwnedCarTrans = new ArrayOfOwnedCarTrans();
		arrayOfOwnedCarTrans.getOwnedCarTrans().add(getOwnedCarTransExample());
		return arrayOfOwnedCarTrans;
	}

	@PUT
	@Secured
	@Path("/{personaId}/defaultcar/{carId}")
	@Produces(MediaType.APPLICATION_XML)
	public String defaultcar(@PathParam(value = "personaId") Long personaId, @PathParam(value = "carId") Long carId) {
		return "";
	}

	@POST
	@Secured
	@Path("/{personaId}/cars")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfOwnedCarTrans carsPost(@PathParam(value = "personaId") Long personaId) {
		return getArrayOfOwnedCarTransExample();
	}

	@GET
	@Secured
	@Path("/{personaId}/cars")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfOwnedCarTrans carsGet(@PathParam(value = "personaId") Long personaId) {
		return getArrayOfOwnedCarTransExample();
	}

	@GET
	@Secured
	@Path("/{personaId}/defaultcar")
	@Produces(MediaType.APPLICATION_XML)
	public OwnedCarTrans defaultcarGet(@PathParam(value = "personaId") Long personaId) {
		return getOwnedCarTransExample();
	}

}
