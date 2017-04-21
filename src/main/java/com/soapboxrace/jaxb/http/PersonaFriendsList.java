//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.04.20 às 09:47:17 PM AMT 
//


package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de PersonaFriendsList complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="PersonaFriendsList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="friendPersona" type="{}ArrayOfFriendPersona" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaFriendsList", propOrder = {
    "friendPersona"
})
public class PersonaFriendsList {

    protected ArrayOfFriendPersona friendPersona;

    /**
     * Obtém o valor da propriedade friendPersona.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFriendPersona }
     *     
     */
    public ArrayOfFriendPersona getFriendPersona() {
        return friendPersona;
    }

    /**
     * Define o valor da propriedade friendPersona.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFriendPersona }
     *     
     */
    public void setFriendPersona(ArrayOfFriendPersona value) {
        this.friendPersona = value;
    }

}
