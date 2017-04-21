//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.04.20 às 09:47:17 PM AMT 
//


package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de LoginAnnouncementContext.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
 * 
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

    public String value() {
        return value;
    }

    public static LoginAnnouncementContext fromValue(String v) {
        for (LoginAnnouncementContext c: LoginAnnouncementContext.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
