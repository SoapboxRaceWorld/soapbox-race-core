package com.soapboxrace.core.servlet.personas;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBElement;

import com.soapboxrace.core.servlet.GenericServlet;
import com.soapboxrace.jaxb.http.ArrayOfCommerceItemTrans;
import com.soapboxrace.jaxb.http.ArrayOfCustomPaintTrans;
import com.soapboxrace.jaxb.http.ArrayOfCustomVinylTrans;
import com.soapboxrace.jaxb.http.ArrayOfInventoryItemTrans;
import com.soapboxrace.jaxb.http.ArrayOfOwnedCarTrans;
import com.soapboxrace.jaxb.http.ArrayOfPerformancePartTrans;
import com.soapboxrace.jaxb.http.ArrayOfSkillModPartTrans;
import com.soapboxrace.jaxb.http.ArrayOfVisualPartTrans;
import com.soapboxrace.jaxb.http.ArrayOfWalletTrans;
import com.soapboxrace.jaxb.http.CarSlotInfoTrans;
import com.soapboxrace.jaxb.http.CommerceResultStatus;
import com.soapboxrace.jaxb.http.CommerceResultTrans;
import com.soapboxrace.jaxb.http.CustomCarTrans;
import com.soapboxrace.jaxb.http.InvalidBasketTrans;
import com.soapboxrace.jaxb.http.InventoryItemTrans;
import com.soapboxrace.jaxb.http.ObjectFactory;
import com.soapboxrace.jaxb.http.OwnedCarTrans;
import com.soapboxrace.jaxb.http.WalletTrans;
import com.soapboxrace.jaxb.util.MarshalXML;

@WebServlet(urlPatterns = { "/Engine.svc/personas/*" })
public class Personas extends GenericServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7643461666384516248L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		String[] splitUri = splitUri(request);
		Long personaId = Long.valueOf(splitUri[4]);
		String method = splitUri[5];
		System.out.println("personaId: " + personaId);
		System.out.println("method: [" + method + "]");
		if ("baskets".equals(method)) {
			baskets(request, response);
		}
		if ("carslots".equals(method)) {
			carslots(request, response);
		}
	}

	private void baskets(ServletRequest request, ServletResponse response) throws ServletException, IOException {
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

		JAXBElement<CommerceResultTrans> createCommerceResultTrans = new ObjectFactory().createCommerceResultTrans(commerceResultTrans);
		String marshal = MarshalXML.marshal(createCommerceResultTrans);
		response.getOutputStream().write(marshal.getBytes());
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
		ownedCarTrans.setId(0L);
		ownedCarTrans.setDurability((short) 100);
		ownedCarTrans.setExpirationDate(null);
		ownedCarTrans.setHeat(0F);
		ownedCarTrans.setOwnershipType("PresetCar");

		ArrayOfOwnedCarTrans arrayOfOwnedCarTrans = new ArrayOfOwnedCarTrans();
		arrayOfOwnedCarTrans.getOwnedCarTrans().add(ownedCarTrans);
		return arrayOfOwnedCarTrans;
	}

	private void carslots(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		CarSlotInfoTrans carSlotInfoTrans = new CarSlotInfoTrans();
		carSlotInfoTrans.setCarsOwnedByPersona(getArrayOfOwnedCarTransExample());
		JAXBElement<CarSlotInfoTrans> createCarSlotInfoTrans = new ObjectFactory().createCarSlotInfoTrans(carSlotInfoTrans);
		String marshal = MarshalXML.marshal(createCarSlotInfoTrans);
		response.getOutputStream().write(marshal.getBytes());
	}

}
