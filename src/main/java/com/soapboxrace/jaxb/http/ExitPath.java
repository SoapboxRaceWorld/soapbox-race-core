
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
 * 
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

    public String value() {
        return value;
    }

    public static ExitPath fromValue(String v) {
        for (ExitPath c: ExitPath.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
