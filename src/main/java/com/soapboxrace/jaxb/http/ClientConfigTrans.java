
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ClientConfigTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
     * Obtém o valor da propriedade clientConfigs.
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
     * Define o valor da propriedade clientConfigs.
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
