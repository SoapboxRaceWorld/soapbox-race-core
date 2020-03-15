
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
 * <p>Java class for enumRewardCategory.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="enumRewardCategory">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Base"/>
 *     &lt;enumeration value="Rank"/>
 *     &lt;enumeration value="Bonus"/>
 *     &lt;enumeration value="TeamBonus"/>
 *     &lt;enumeration value="Amplifier"/>
 *     &lt;enumeration value="Skill"/>
 *     &lt;enumeration value="Pursuit"/>
 *     &lt;enumeration value="Objective"/>
 *     &lt;enumeration value="SkillMod"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "enumRewardCategory")
@XmlEnum
public enum EnumRewardCategory {

    @XmlEnumValue("Base")
    BASE("Base"),
    @XmlEnumValue("Rank")
    RANK("Rank"),
    @XmlEnumValue("Bonus")
    BONUS("Bonus"),
    @XmlEnumValue("TeamBonus")
    TEAM_BONUS("TeamBonus"),
    @XmlEnumValue("Amplifier")
    AMPLIFIER("Amplifier"),
    @XmlEnumValue("Skill")
    SKILL("Skill"),
    @XmlEnumValue("Pursuit")
    PURSUIT("Pursuit"),
    @XmlEnumValue("Objective")
    OBJECTIVE("Objective"),
    @XmlEnumValue("SkillMod")
    SKILL_MOD("SkillMod");
    private final String value;

    EnumRewardCategory(String v) {
        value = v;
    }

    public static EnumRewardCategory fromValue(String v) {
        for (EnumRewardCategory c : EnumRewardCategory.values()) {
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
