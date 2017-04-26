
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de HeartBeat complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="HeartBeat">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MetagameFlags" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="enabledBitField" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeartBeat", propOrder = {
    "metagameFlags",
    "enabledBitField"
})
public class HeartBeat {

    @XmlElement(name = "MetagameFlags")
    protected int metagameFlags;
    protected int enabledBitField;

    /**
     * Obtém o valor da propriedade metagameFlags.
     * 
     */
    public int getMetagameFlags() {
        return metagameFlags;
    }

    /**
     * Define o valor da propriedade metagameFlags.
     * 
     */
    public void setMetagameFlags(int value) {
        this.metagameFlags = value;
    }

    /**
     * Obtém o valor da propriedade enabledBitField.
     * 
     */
    public int getEnabledBitField() {
        return enabledBitField;
    }

    /**
     * Define o valor da propriedade enabledBitField.
     * 
     */
    public void setEnabledBitField(int value) {
        this.enabledBitField = value;
    }

}
