
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LobbyEntrantState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LobbyEntrantState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="InFreeRoam"/>
 *     &lt;enumeration value="InLobby"/>
 *     &lt;enumeration value="InEvent"/>
 *     &lt;enumeration value="InPostEvent"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LobbyEntrantState")
@XmlEnum
public enum LobbyEntrantState {

    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("InFreeRoam")
    IN_FREE_ROAM("InFreeRoam"),
    @XmlEnumValue("InLobby")
    IN_LOBBY("InLobby"),
    @XmlEnumValue("InEvent")
    IN_EVENT("InEvent"),
    @XmlEnumValue("InPostEvent")
    IN_POST_EVENT("InPostEvent");
    private final String value;

    LobbyEntrantState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LobbyEntrantState fromValue(String v) {
        for (LobbyEntrantState c: LobbyEntrantState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
