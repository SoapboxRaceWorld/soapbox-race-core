
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoginAnnouncementContext.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LoginAnnouncementContext">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NotApplicable"/>
 *     &lt;enumeration value="CarPurchase"/>
 *     &lt;enumeration value="CardsPack"/>
 *     &lt;enumeration value="PaintShop"/>
 *     &lt;enumeration value="PerformanceShop"/>
 *     &lt;enumeration value="AftermarketShop"/>
 *     &lt;enumeration value="VinylShop"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "LoginAnnouncementContext")
@XmlEnum
public enum LoginAnnouncementContext {

    @XmlEnumValue("NotApplicable")
    NOT_APPLICABLE("NotApplicable"),
    @XmlEnumValue("CarPurchase")
    CAR_PURCHASE("CarPurchase"),
    @XmlEnumValue("CardsPack")
    CARDS_PACK("CardsPack"),
    @XmlEnumValue("PaintShop")
    PAINT_SHOP("PaintShop"),
    @XmlEnumValue("PerformanceShop")
    PERFORMANCE_SHOP("PerformanceShop"),
    @XmlEnumValue("AftermarketShop")
    AFTERMARKET_SHOP("AftermarketShop"),
    @XmlEnumValue("VinylShop")
    VINYL_SHOP("VinylShop");
    private final String value;

    LoginAnnouncementContext(String v) {
        value = v;
    }

    public static LoginAnnouncementContext fromValue(String v) {
        for (LoginAnnouncementContext c : LoginAnnouncementContext.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public String value() {
        return value;
    }

}
