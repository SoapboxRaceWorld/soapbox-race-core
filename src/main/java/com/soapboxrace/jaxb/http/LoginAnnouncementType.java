
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
 * <p>Java class for LoginAnnouncementType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LoginAnnouncementType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ExternalLink"/>
 *     &lt;enumeration value="SafehouseProduct"/>
 *     &lt;enumeration value="ImageOnly"/>
 *     &lt;enumeration value="SafehouseProductNoButton"/>
 *     &lt;enumeration value="ExternalLinkNoButton"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "LoginAnnouncementType")
@XmlEnum
public enum LoginAnnouncementType {

    @XmlEnumValue("ExternalLink")
    EXTERNAL_LINK("ExternalLink"),
    @XmlEnumValue("SafehouseProduct")
    SAFEHOUSE_PRODUCT("SafehouseProduct"),
    @XmlEnumValue("ImageOnly")
    IMAGE_ONLY("ImageOnly"),
    @XmlEnumValue("SafehouseProductNoButton")
    SAFEHOUSE_PRODUCT_NO_BUTTON("SafehouseProductNoButton"),
    @XmlEnumValue("ExternalLinkNoButton")
    EXTERNAL_LINK_NO_BUTTON("ExternalLinkNoButton");
    private final String value;

    LoginAnnouncementType(String v) {
        value = v;
    }

    public static LoginAnnouncementType fromValue(String v) {
        for (LoginAnnouncementType c : LoginAnnouncementType.values()) {
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
