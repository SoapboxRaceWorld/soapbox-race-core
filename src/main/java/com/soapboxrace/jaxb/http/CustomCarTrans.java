//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.04.20 às 09:47:17 PM AMT 
//


package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de CustomCarTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="CustomCarTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BaseCar" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CarClassHash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsPreset" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paints" type="{}ArrayOfCustomPaintTrans" minOccurs="0"/>
 *         &lt;element name="PerformanceParts" type="{}ArrayOfPerformancePartTrans" minOccurs="0"/>
 *         &lt;element name="PhysicsProfileHash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Rating" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ResalePrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="RideHeightDrop" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="SkillModParts" type="{}ArrayOfSkillModPartTrans" minOccurs="0"/>
 *         &lt;element name="SkillModSlotCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Vinyls" type="{}ArrayOfCustomVinylTrans" minOccurs="0"/>
 *         &lt;element name="VisualParts" type="{}ArrayOfVisualPartTrans" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomCarTrans", propOrder = {
    "baseCar",
    "carClassHash",
    "id",
    "isPreset",
    "level",
    "name",
    "paints",
    "performanceParts",
    "physicsProfileHash",
    "rating",
    "resalePrice",
    "rideHeightDrop",
    "skillModParts",
    "skillModSlotCount",
    "version",
    "vinyls",
    "visualParts"
})
public class CustomCarTrans {

    @XmlElement(name = "BaseCar")
    protected int baseCar;
    @XmlElement(name = "CarClassHash")
    protected int carClassHash;
    @XmlElement(name = "Id")
    protected int id;
    @XmlElement(name = "IsPreset")
    protected boolean isPreset;
    @XmlElement(name = "Level")
    protected int level;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Paints")
    protected ArrayOfCustomPaintTrans paints;
    @XmlElement(name = "PerformanceParts")
    protected ArrayOfPerformancePartTrans performanceParts;
    @XmlElement(name = "PhysicsProfileHash")
    protected int physicsProfileHash;
    @XmlElement(name = "Rating")
    protected int rating;
    @XmlElement(name = "ResalePrice")
    protected float resalePrice;
    @XmlElement(name = "RideHeightDrop")
    protected float rideHeightDrop;
    @XmlElement(name = "SkillModParts")
    protected ArrayOfSkillModPartTrans skillModParts;
    @XmlElement(name = "SkillModSlotCount")
    protected int skillModSlotCount;
    @XmlElement(name = "Version")
    protected int version;
    @XmlElement(name = "Vinyls")
    protected ArrayOfCustomVinylTrans vinyls;
    @XmlElement(name = "VisualParts")
    protected ArrayOfVisualPartTrans visualParts;

    /**
     * Obtém o valor da propriedade baseCar.
     * 
     */
    public int getBaseCar() {
        return baseCar;
    }

    /**
     * Define o valor da propriedade baseCar.
     * 
     */
    public void setBaseCar(int value) {
        this.baseCar = value;
    }

    /**
     * Obtém o valor da propriedade carClassHash.
     * 
     */
    public int getCarClassHash() {
        return carClassHash;
    }

    /**
     * Define o valor da propriedade carClassHash.
     * 
     */
    public void setCarClassHash(int value) {
        this.carClassHash = value;
    }

    /**
     * Obtém o valor da propriedade id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Obtém o valor da propriedade isPreset.
     * 
     */
    public boolean isIsPreset() {
        return isPreset;
    }

    /**
     * Define o valor da propriedade isPreset.
     * 
     */
    public void setIsPreset(boolean value) {
        this.isPreset = value;
    }

    /**
     * Obtém o valor da propriedade level.
     * 
     */
    public int getLevel() {
        return level;
    }

    /**
     * Define o valor da propriedade level.
     * 
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Obtém o valor da propriedade name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Define o valor da propriedade name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtém o valor da propriedade paints.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCustomPaintTrans }
     *     
     */
    public ArrayOfCustomPaintTrans getPaints() {
        return paints;
    }

    /**
     * Define o valor da propriedade paints.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCustomPaintTrans }
     *     
     */
    public void setPaints(ArrayOfCustomPaintTrans value) {
        this.paints = value;
    }

    /**
     * Obtém o valor da propriedade performanceParts.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPerformancePartTrans }
     *     
     */
    public ArrayOfPerformancePartTrans getPerformanceParts() {
        return performanceParts;
    }

    /**
     * Define o valor da propriedade performanceParts.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPerformancePartTrans }
     *     
     */
    public void setPerformanceParts(ArrayOfPerformancePartTrans value) {
        this.performanceParts = value;
    }

    /**
     * Obtém o valor da propriedade physicsProfileHash.
     * 
     */
    public int getPhysicsProfileHash() {
        return physicsProfileHash;
    }

    /**
     * Define o valor da propriedade physicsProfileHash.
     * 
     */
    public void setPhysicsProfileHash(int value) {
        this.physicsProfileHash = value;
    }

    /**
     * Obtém o valor da propriedade rating.
     * 
     */
    public int getRating() {
        return rating;
    }

    /**
     * Define o valor da propriedade rating.
     * 
     */
    public void setRating(int value) {
        this.rating = value;
    }

    /**
     * Obtém o valor da propriedade resalePrice.
     * 
     */
    public float getResalePrice() {
        return resalePrice;
    }

    /**
     * Define o valor da propriedade resalePrice.
     * 
     */
    public void setResalePrice(float value) {
        this.resalePrice = value;
    }

    /**
     * Obtém o valor da propriedade rideHeightDrop.
     * 
     */
    public float getRideHeightDrop() {
        return rideHeightDrop;
    }

    /**
     * Define o valor da propriedade rideHeightDrop.
     * 
     */
    public void setRideHeightDrop(float value) {
        this.rideHeightDrop = value;
    }

    /**
     * Obtém o valor da propriedade skillModParts.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSkillModPartTrans }
     *     
     */
    public ArrayOfSkillModPartTrans getSkillModParts() {
        return skillModParts;
    }

    /**
     * Define o valor da propriedade skillModParts.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSkillModPartTrans }
     *     
     */
    public void setSkillModParts(ArrayOfSkillModPartTrans value) {
        this.skillModParts = value;
    }

    /**
     * Obtém o valor da propriedade skillModSlotCount.
     * 
     */
    public int getSkillModSlotCount() {
        return skillModSlotCount;
    }

    /**
     * Define o valor da propriedade skillModSlotCount.
     * 
     */
    public void setSkillModSlotCount(int value) {
        this.skillModSlotCount = value;
    }

    /**
     * Obtém o valor da propriedade version.
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * Define o valor da propriedade version.
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

    /**
     * Obtém o valor da propriedade vinyls.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCustomVinylTrans }
     *     
     */
    public ArrayOfCustomVinylTrans getVinyls() {
        return vinyls;
    }

    /**
     * Define o valor da propriedade vinyls.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCustomVinylTrans }
     *     
     */
    public void setVinyls(ArrayOfCustomVinylTrans value) {
        this.vinyls = value;
    }

    /**
     * Obtém o valor da propriedade visualParts.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVisualPartTrans }
     *     
     */
    public ArrayOfVisualPartTrans getVisualParts() {
        return visualParts;
    }

    /**
     * Define o valor da propriedade visualParts.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVisualPartTrans }
     *     
     */
    public void setVisualParts(ArrayOfVisualPartTrans value) {
        this.visualParts = value;
    }

}
