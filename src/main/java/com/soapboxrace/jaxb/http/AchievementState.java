
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
 * <p>Java class for AchievementState.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AchievementState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Locked"/>
 *     &lt;enumeration value="InProgress"/>
 *     &lt;enumeration value="RewardWaiting"/>
 *     &lt;enumeration value="Completed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "AchievementState")
@XmlEnum
public enum AchievementState {

    @XmlEnumValue("Locked")
    LOCKED("Locked"),
    @XmlEnumValue("InProgress")
    IN_PROGRESS("InProgress"),
    @XmlEnumValue("RewardWaiting")
    REWARD_WAITING("RewardWaiting"),
    @XmlEnumValue("Completed")
    COMPLETED("Completed");
    private final String value;

    AchievementState(String v) {
        value = v;
    }

    public static AchievementState fromValue(String v) {
        for (AchievementState c : AchievementState.values()) {
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
