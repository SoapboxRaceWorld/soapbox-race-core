
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
 * <p>Java class for enumRewardType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="enumRewardType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Busted"/>
 *     &lt;enumeration value="Evaded"/>
 *     &lt;enumeration value="RepAmplifier"/>
 *     &lt;enumeration value="TokenAmplifier"/>
 *     &lt;enumeration value="SkillMostWanted"/>
 *     &lt;enumeration value="SkillSocialite"/>
 *     &lt;enumeration value="SkillTycoon"/>
 *     &lt;enumeration value="SkillTerminator"/>
 *     &lt;enumeration value="HeatLevel"/>
 *     &lt;enumeration value="PursuitLength"/>
 *     &lt;enumeration value="Bounty"/>
 *     &lt;enumeration value="CopCarsDeployed"/>
 *     &lt;enumeration value="CopCarsRammed"/>
 *     &lt;enumeration value="CopCarsDisabled"/>
 *     &lt;enumeration value="RhinosDisabled"/>
 *     &lt;enumeration value="CostToState"/>
 *     &lt;enumeration value="RoadblocksDodged"/>
 *     &lt;enumeration value="SpikeStripsDodged"/>
 *     &lt;enumeration value="Infractions"/>
 *     &lt;enumeration value="LevelCap"/>
 *     &lt;enumeration value="EntitlementLevelCap"/>
 *     &lt;enumeration value="TopenCap"/>
 *     &lt;enumeration value="SafehouseReached"/>
 *     &lt;enumeration value="Finished"/>
 *     &lt;enumeration value="TimeBonus"/>
 *     &lt;enumeration value="Player1"/>
 *     &lt;enumeration value="Player2"/>
 *     &lt;enumeration value="Player3"/>
 *     &lt;enumeration value="Player4"/>
 *     &lt;enumeration value="StrikeFree"/>
 *     &lt;enumeration value="TeamStrikeBonus"/>
 *     &lt;enumeration value="PowerupBonus"/>
 *     &lt;enumeration value="SkillMod"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "enumRewardType")
@XmlEnum
public enum EnumRewardType {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Busted")
    BUSTED("Busted"),
    @XmlEnumValue("Evaded")
    EVADED("Evaded"),
    @XmlEnumValue("RepAmplifier")
    REP_AMPLIFIER("RepAmplifier"),
    @XmlEnumValue("TokenAmplifier")
    TOKEN_AMPLIFIER("TokenAmplifier"),
    @XmlEnumValue("SkillMostWanted")
    SKILL_MOST_WANTED("SkillMostWanted"),
    @XmlEnumValue("SkillSocialite")
    SKILL_SOCIALITE("SkillSocialite"),
    @XmlEnumValue("SkillTycoon")
    SKILL_TYCOON("SkillTycoon"),
    @XmlEnumValue("SkillTerminator")
    SKILL_TERMINATOR("SkillTerminator"),
    @XmlEnumValue("HeatLevel")
    HEAT_LEVEL("HeatLevel"),
    @XmlEnumValue("PursuitLength")
    PURSUIT_LENGTH("PursuitLength"),
    @XmlEnumValue("Bounty")
    BOUNTY("Bounty"),
    @XmlEnumValue("CopCarsDeployed")
    COP_CARS_DEPLOYED("CopCarsDeployed"),
    @XmlEnumValue("CopCarsRammed")
    COP_CARS_RAMMED("CopCarsRammed"),
    @XmlEnumValue("CopCarsDisabled")
    COP_CARS_DISABLED("CopCarsDisabled"),
    @XmlEnumValue("RhinosDisabled")
    RHINOS_DISABLED("RhinosDisabled"),
    @XmlEnumValue("CostToState")
    COST_TO_STATE("CostToState"),
    @XmlEnumValue("RoadblocksDodged")
    ROADBLOCKS_DODGED("RoadblocksDodged"),
    @XmlEnumValue("SpikeStripsDodged")
    SPIKE_STRIPS_DODGED("SpikeStripsDodged"),
    @XmlEnumValue("Infractions")
    INFRACTIONS("Infractions"),
    @XmlEnumValue("LevelCap")
    LEVEL_CAP("LevelCap"),
    @XmlEnumValue("EntitlementLevelCap")
    ENTITLEMENT_LEVEL_CAP("EntitlementLevelCap"),
    @XmlEnumValue("TopenCap")
    TOPEN_CAP("TopenCap"),
    @XmlEnumValue("SafehouseReached")
    SAFEHOUSE_REACHED("SafehouseReached"),
    @XmlEnumValue("Finished")
    FINISHED("Finished"),
    @XmlEnumValue("TimeBonus")
    TIME_BONUS("TimeBonus"),
    @XmlEnumValue("Player1")
    PLAYER_1("Player1"),
    @XmlEnumValue("Player2")
    PLAYER_2("Player2"),
    @XmlEnumValue("Player3")
    PLAYER_3("Player3"),
    @XmlEnumValue("Player4")
    PLAYER_4("Player4"),
    @XmlEnumValue("StrikeFree")
    STRIKE_FREE("StrikeFree"),
    @XmlEnumValue("TeamStrikeBonus")
    TEAM_STRIKE_BONUS("TeamStrikeBonus"),
    @XmlEnumValue("PowerupBonus")
    POWERUP_BONUS("PowerupBonus"),
    @XmlEnumValue("SkillMod")
    SKILL_MOD("SkillMod");
    private final String value;

    EnumRewardType(String v) {
        value = v;
    }

    public static EnumRewardType fromValue(String v) {
        for (EnumRewardType c : EnumRewardType.values()) {
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
