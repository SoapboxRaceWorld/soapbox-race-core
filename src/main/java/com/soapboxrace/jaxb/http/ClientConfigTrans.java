
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClientConfigTrans complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClientConfigTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientConfigs" type="{}ArrayOfClientConfig" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientConfigTrans", propOrder = {
    "clientConfigs"
})
public class ClientConfigTrans {

    protected ArrayOfClientConfig clientConfigs;

    /**
     * Gets the value of the clientConfigs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfClientConfig }
     *     
     */
    public ArrayOfClientConfig getClientConfigs() {
        return clientConfigs;
    }

    /**
     * Sets the value of the clientConfigs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfClientConfig }
     *     
     */
    public void setClientConfigs(ArrayOfClientConfig value) {
        this.clientConfigs = value;
    }

}
