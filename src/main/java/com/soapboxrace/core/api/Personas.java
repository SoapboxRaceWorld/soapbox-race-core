/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.api;

import com.soapboxrace.core.api.util.Secured;
import com.soapboxrace.core.bo.*;
import com.soapboxrace.core.bo.util.OwnedCarConverter;
import com.soapboxrace.core.jpa.CarSlotEntity;
import com.soapboxrace.core.jpa.OwnedCarEntity;
import com.soapboxrace.core.jpa.PersonaEntity;
import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.*;
import com.soapboxrace.jaxb.util.MarshalXML;
import com.soapboxrace.jaxb.util.UnmarshalXML;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

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
    public CommerceSessionResultTrans commerce(InputStream commerceXml,
                                               @HeaderParam("securityToken") String securityToken,
                                               @PathParam(value = "personaId") Long personaId) {
        sessionBO.verifyPersonaOwnership(securityToken, personaId);
        String xml = new BufferedReader(new InputStreamReader(commerceXml))
                .lines().collect(Collectors.joining(""));
        CommerceSessionTrans commerceSessionTrans = UnmarshalXML.unMarshal(xml, CommerceSessionTrans.class);

        return commerceBO.doCommerce(commerceSessionTrans, personaId);
    }

    @POST
    @Secured
    @Path("/{personaId}/baskets")
    @Produces(MediaType.APPLICATION_XML)
    public CommerceResultTrans baskets(@HeaderParam("securityToken") String securityToken, InputStream basketXml,
                                       @PathParam(value = "personaId") Long personaId) {
        sessionBO.verifyPersonaOwnership(securityToken, personaId);

        PersonaEntity personaEntity = personaBO.getPersonaById(personaId);

        CommerceResultTrans commerceResultTrans = new CommerceResultTrans();

        ArrayOfInventoryItemTrans arrayOfInventoryItemTrans = new ArrayOfInventoryItemTrans();
        arrayOfInventoryItemTrans.getInventoryItemTrans().add(new InventoryItemTrans());

        commerceResultTrans.setCommerceItems(new ArrayOfCommerceItemTrans());
        commerceResultTrans.setInvalidBasket(new InvalidBasketTrans());
        commerceResultTrans.setInventoryItems(arrayOfInventoryItemTrans);

        ArrayOfOwnedCarTrans arrayOfOwnedCarTrans = new ArrayOfOwnedCarTrans();

        BasketTrans basketTrans = UnmarshalXML.unMarshal(basketXml, BasketTrans.class);
        String productId = basketTrans.getItems().getBasketItemTrans().get(0).getProductId();
        if ("-1".equals(productId) || "SRV-GARAGESLOT".equals(productId)) {
            commerceResultTrans.setStatus(CommerceResultStatus.FAIL_INSUFFICIENT_FUNDS);
        } else if (productId.contains("SRV-POWERUP")) {
            commerceResultTrans.setStatus(basketBO.buyPowerups(productId, personaEntity));
        } else if ("SRV-REPAIR".equals(productId)) {
            commerceResultTrans.setStatus(basketBO.repairCar(productId, personaEntity));
        } else if ("SRV-THREVIVE".equals(productId)) {
            commerceResultTrans.setStatus(basketBO.reviveTreasureHunt(productId, personaEntity));
        } else {
            ProductEntity productEntity = basketBO.findProduct(productId);

            if (productEntity != null) {
                switch (productEntity.getProductType()) {
                    case "PRESETCAR":
                        commerceResultTrans.setStatus(basketBO.buyCar(productId, personaEntity, securityToken,
                                commerceResultTrans));
                        break;
                    case "AMPLIFIER":
                        commerceResultTrans.setStatus(basketBO.buyAmplifier(personaEntity, productId
                        ));
                        break;
                }
            }
        }

        WalletTrans cashWallet = new WalletTrans();
        cashWallet.setBalance(personaEntity.getCash());
        cashWallet.setCurrency("CASH");

        WalletTrans boostWallet = new WalletTrans();
        boostWallet.setBalance(personaEntity.getBoost());
        boostWallet.setCurrency("BOOST"); // 12/30/18: why doesn't _NS work? Truly a mystery...

        ArrayOfWalletTrans arrayOfWalletTrans = new ArrayOfWalletTrans();
        arrayOfWalletTrans.getWalletTrans().add(cashWallet);
        arrayOfWalletTrans.getWalletTrans().add(boostWallet);

        commerceResultTrans.setWallets(arrayOfWalletTrans);

        return commerceResultTrans;
    }

    @GET
    @Secured
    @Path("/{personaId}/carslots")
    @Produces(MediaType.APPLICATION_XML)
    public CarSlotInfoTrans carslots(@PathParam(value = "personaId") Long personaId,
                                     @HeaderParam("securityToken") String securityToken) {
        sessionBO.verifyPersonaOwnership(securityToken, personaId);

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
        return inventoryBO.getClientInventory(inventoryBO.getInventory(personaId));
    }

    @GET
    @Secured
    @Path("/inventory/sell/{entitlementTag}")
    @Produces(MediaType.APPLICATION_XML)
    public String sellInventoryItem(@HeaderParam("securityToken") String securityToken,
                                    @PathParam("entitlementTag") String entitlementTag) {
        long personaId = sessionBO.getActivePersonaId(securityToken);
        inventoryBO.removeItem(inventoryBO.getInventory(personaId), entitlementTag);
        return "";
    }

    @POST
    @Secured
    @Path("/{personaId}/cars")
    @Produces(MediaType.APPLICATION_XML)
    public String carsPost(@PathParam(value = "personaId") Long personaId,
                           @QueryParam("serialNumber") Long serialNumber,
                           @HeaderParam("securityToken") String securityToken) {
        sessionBO.verifyPersonaOwnership(securityToken, personaId);
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
    public OwnedCarTrans carsGet(@PathParam(value = "personaId") Long personaId,
                                 @PathParam(value = "carId") Long carId) {
        OwnedCarEntity ownedCarEntity = personaBO.getCarByOwnedCarId(carId);
        return OwnedCarConverter.entity2Trans(ownedCarEntity);
    }

    @PUT
    @Secured
    @Path("/{personaId}/cars")
    @Produces(MediaType.APPLICATION_XML)
    public String carsPut(@PathParam(value = "personaId") Long personaId,
                          @HeaderParam("securityToken") String securityToken, InputStream ownedCarXml) {
        // update car (skill and performance shop)
        sessionBO.verifyPersonaOwnership(securityToken, personaId);
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
        sessionBO.verifyPersonaOwnership(securityToken, personaId);
        personaBO.changeDefaultCar(personaId, carId);
        return "";
    }

}
