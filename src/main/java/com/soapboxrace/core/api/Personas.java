package com.soapboxrace.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
import com.soapboxrace.jaxb.http.CarSlotInfoTrans;
import com.soapboxrace.jaxb.http.CommerceResultStatus;
import com.soapboxrace.jaxb.http.CommerceResultTrans;
import com.soapboxrace.jaxb.http.CustomCarTrans;
import com.soapboxrace.jaxb.http.InvalidBasketTrans;
import com.soapboxrace.jaxb.http.InventoryItemTrans;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.http.WalletTrans;

@Path("/personas")
public class Personas {

	@POST
	@Path("/{personaId}/baskets")
	@Produces(MediaType.APPLICATION_XML)
	public CommerceResultTrans baskets(@PathParam(value = "personaId") Long personaId) {
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
		return commerceResultTrans;
	}

	@GET
	@Path("/{personaId}/carslots")
	@Produces(MediaType.APPLICATION_XML)
	public CarSlotInfoTrans carslots(@PathParam(value = "personaId") Long personaId) {
		CarSlotInfoTrans carSlotInfoTrans = new CarSlotInfoTrans();
		carSlotInfoTrans.setCarsOwnedByPersona(getArrayOfOwnedCarTransExample());
		carSlotInfoTrans.setDefaultOwnedCarIndex(0);
		carSlotInfoTrans.setObtainableSlots(new ArrayOfProductTrans());
		carSlotInfoTrans.setOwnedCarSlotsCount(1);
		return carSlotInfoTrans;
	}

	@GET
	@Path("/inventory/objects")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayOfInventoryItemTrans inventoryObjects() {
		return new ArrayOfInventoryItemTrans();
	}

	private ArrayOfOwnedCarTrans getArrayOfOwnedCarTransExample() {
		CustomCarTrans customCarTrans = new CustomCarTrans();
		customCarTrans.setId(12345678);
		customCarTrans.setBaseCar(12345678);
		customCarTrans.setIsPreset(true);
		customCarTrans.setCarClassHash(872416321);
		customCarTrans.setName("240sx");
		customCarTrans.setPhysicsProfileHash(-1469109252);
		customCarTrans.setSkillModSlotCount(5);
		customCarTrans.setPaints(new ArrayOfCustomPaintTrans());
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

		ArrayOfOwnedCarTrans arrayOfOwnedCarTrans = new ArrayOfOwnedCarTrans();
		arrayOfOwnedCarTrans.getOwnedCarTrans().add(ownedCarTrans);
		return arrayOfOwnedCarTrans;
	}
}
