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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de OwnedCarTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="OwnedCarTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomCar" type="{}CustomCarTrans" minOccurs="0"/>
 *         &lt;element name="Durability" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ExpirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Heat" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="OwnershipType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OwnedCarTrans", propOrder = {
    "customCar",
    "durability",
    "expirationDate",
    "heat",
    "id",
    "ownershipType"
})
public class OwnedCarTrans {

    @XmlElement(name = "CustomCar")
    protected CustomCarTrans customCar;
    @XmlElement(name = "Durability")
    protected int durability;
    @XmlElement(name = "ExpirationDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    @XmlElement(name = "Heat")
    protected float heat;
    @XmlElement(name = "Id")
    protected long id;
    @XmlElement(name = "OwnershipType")
    protected String ownershipType;

    /**
     * Obtém o valor da propriedade customCar.
     * 
     * @return
     *     possible object is
     *     {@link CustomCarTrans }
     *     
     */
    public CustomCarTrans getCustomCar() {
        return customCar;
    }

    /**
     * Define o valor da propriedade customCar.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomCarTrans }
     *     
     */
    public void setCustomCar(CustomCarTrans value) {
        this.customCar = value;
    }

    /**
     * Obtém o valor da propriedade durability.
     * 
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Define o valor da propriedade durability.
     * 
     */
    public void setDurability(int value) {
        this.durability = value;
    }

    /**
     * Obtém o valor da propriedade expirationDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Define o valor da propriedade expirationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Obtém o valor da propriedade heat.
     * 
     */
    public float getHeat() {
        return heat;
    }

    /**
     * Define o valor da propriedade heat.
     * 
     */
    public void setHeat(float value) {
        this.heat = value;
    }

    /**
     * Obtém o valor da propriedade id.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Obtém o valor da propriedade ownershipType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnershipType() {
        return ownershipType;
    }

    /**
     * Define o valor da propriedade ownershipType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnershipType(String value) {
        this.ownershipType = value;
    }

}
