//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.04.19 às 11:07:08 PM AMT 
//


package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de CarSlotInfoTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="CarSlotInfoTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CarsOwnedByPersona" type="{}ArrayOfOwnedCarTrans" minOccurs="0"/>
 *         &lt;element name="DefaultOwnedCarIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ObtainableSlots" type="{}ArrayOfProductTrans" minOccurs="0"/>
 *         &lt;element name="OwnedCarSlotsCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CarSlotInfoTrans", propOrder = {
    "carsOwnedByPersona",
    "defaultOwnedCarIndex",
    "obtainableSlots",
    "ownedCarSlotsCount"
})
public class CarSlotInfoTrans {

    @XmlElement(name = "CarsOwnedByPersona")
    protected ArrayOfOwnedCarTrans carsOwnedByPersona;
    @XmlElement(name = "DefaultOwnedCarIndex")
    protected int defaultOwnedCarIndex;
    @XmlElement(name = "ObtainableSlots")
    protected ArrayOfProductTrans obtainableSlots;
    @XmlElement(name = "OwnedCarSlotsCount")
    protected int ownedCarSlotsCount;

    /**
     * Obtém o valor da propriedade carsOwnedByPersona.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOwnedCarTrans }
     *     
     */
    public ArrayOfOwnedCarTrans getCarsOwnedByPersona() {
        return carsOwnedByPersona;
    }

    /**
     * Define o valor da propriedade carsOwnedByPersona.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOwnedCarTrans }
     *     
     */
    public void setCarsOwnedByPersona(ArrayOfOwnedCarTrans value) {
        this.carsOwnedByPersona = value;
    }

    /**
     * Obtém o valor da propriedade defaultOwnedCarIndex.
     * 
     */
    public int getDefaultOwnedCarIndex() {
        return defaultOwnedCarIndex;
    }

    /**
     * Define o valor da propriedade defaultOwnedCarIndex.
     * 
     */
    public void setDefaultOwnedCarIndex(int value) {
        this.defaultOwnedCarIndex = value;
    }

    /**
     * Obtém o valor da propriedade obtainableSlots.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProductTrans }
     *     
     */
    public ArrayOfProductTrans getObtainableSlots() {
        return obtainableSlots;
    }

    /**
     * Define o valor da propriedade obtainableSlots.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProductTrans }
     *     
     */
    public void setObtainableSlots(ArrayOfProductTrans value) {
        this.obtainableSlots = value;
    }

    /**
     * Obtém o valor da propriedade ownedCarSlotsCount.
     * 
     */
    public int getOwnedCarSlotsCount() {
        return ownedCarSlotsCount;
    }

    /**
     * Define o valor da propriedade ownedCarSlotsCount.
     * 
     */
    public void setOwnedCarSlotsCount(int value) {
        this.ownedCarSlotsCount = value;
    }

}
