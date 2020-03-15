
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
 * <p>Java class for ExitPath.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExitPath">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ExitToFreeroam"/>
 *     &lt;enumeration value="ExitToLobby"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ExitPath")
@XmlEnum
public enum ExitPath {

    @XmlEnumValue("ExitToFreeroam")
    EXIT_TO_FREEROAM("ExitToFreeroam"),
    @XmlEnumValue("ExitToLobby")
    EXIT_TO_LOBBY("ExitToLobby");
    private final String value;

    ExitPath(String v) {
        value = v;
    }

    public static ExitPath fromValue(String v) {
        for (ExitPath c : ExitPath.values()) {
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
