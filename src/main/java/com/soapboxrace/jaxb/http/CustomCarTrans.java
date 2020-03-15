
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomCarTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the baseCar property.
     */
    public int getBaseCar() {
        return baseCar;
    }

    /**
     * Sets the value of the baseCar property.
     */
    public void setBaseCar(int value) {
        this.baseCar = value;
    }

    /**
     * Gets the value of the carClassHash property.
     */
    public int getCarClassHash() {
        return carClassHash;
    }

    /**
     * Sets the value of the carClassHash property.
     */
    public void setCarClassHash(int value) {
        this.carClassHash = value;
    }

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the isPreset property.
     */
    public boolean isIsPreset() {
        return isPreset;
    }

    /**
     * Sets the value of the isPreset property.
     */
    public void setIsPreset(boolean value) {
        this.isPreset = value;
    }

    /**
     * Gets the value of the level property.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the paints property.
     *
     * @return possible object is
     * {@link ArrayOfCustomPaintTrans }
     */
    public ArrayOfCustomPaintTrans getPaints() {
        return paints;
    }

    /**
     * Sets the value of the paints property.
     *
     * @param value allowed object is
     *              {@link ArrayOfCustomPaintTrans }
     */
    public void setPaints(ArrayOfCustomPaintTrans value) {
        this.paints = value;
    }

    /**
     * Gets the value of the performanceParts property.
     *
     * @return possible object is
     * {@link ArrayOfPerformancePartTrans }
     */
    public ArrayOfPerformancePartTrans getPerformanceParts() {
        return performanceParts;
    }

    /**
     * Sets the value of the performanceParts property.
     *
     * @param value allowed object is
     *              {@link ArrayOfPerformancePartTrans }
     */
    public void setPerformanceParts(ArrayOfPerformancePartTrans value) {
        this.performanceParts = value;
    }

    /**
     * Gets the value of the physicsProfileHash property.
     */
    public int getPhysicsProfileHash() {
        return physicsProfileHash;
    }

    /**
     * Sets the value of the physicsProfileHash property.
     */
    public void setPhysicsProfileHash(int value) {
        this.physicsProfileHash = value;
    }

    /**
     * Gets the value of the rating property.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     */
    public void setRating(int value) {
        this.rating = value;
    }

    /**
     * Gets the value of the resalePrice property.
     */
    public float getResalePrice() {
        return resalePrice;
    }

    /**
     * Sets the value of the resalePrice property.
     */
    public void setResalePrice(float value) {
        this.resalePrice = value;
    }

    /**
     * Gets the value of the rideHeightDrop property.
     */
    public float getRideHeightDrop() {
        return rideHeightDrop;
    }

    /**
     * Sets the value of the rideHeightDrop property.
     */
    public void setRideHeightDrop(float value) {
        this.rideHeightDrop = value;
    }

    /**
     * Gets the value of the skillModParts property.
     *
     * @return possible object is
     * {@link ArrayOfSkillModPartTrans }
     */
    public ArrayOfSkillModPartTrans getSkillModParts() {
        return skillModParts;
    }

    /**
     * Sets the value of the skillModParts property.
     *
     * @param value allowed object is
     *              {@link ArrayOfSkillModPartTrans }
     */
    public void setSkillModParts(ArrayOfSkillModPartTrans value) {
        this.skillModParts = value;
    }

    /**
     * Gets the value of the skillModSlotCount property.
     */
    public int getSkillModSlotCount() {
        return skillModSlotCount;
    }

    /**
     * Sets the value of the skillModSlotCount property.
     */
    public void setSkillModSlotCount(int value) {
        this.skillModSlotCount = value;
    }

    /**
     * Gets the value of the version property.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     */
    public void setVersion(int value) {
        this.version = value;
    }

    /**
     * Gets the value of the vinyls property.
     *
     * @return possible object is
     * {@link ArrayOfCustomVinylTrans }
     */
    public ArrayOfCustomVinylTrans getVinyls() {
        return vinyls;
    }

    /**
     * Sets the value of the vinyls property.
     *
     * @param value allowed object is
     *              {@link ArrayOfCustomVinylTrans }
     */
    public void setVinyls(ArrayOfCustomVinylTrans value) {
        this.vinyls = value;
    }

    /**
     * Gets the value of the visualParts property.
     *
     * @return possible object is
     * {@link ArrayOfVisualPartTrans }
     */
    public ArrayOfVisualPartTrans getVisualParts() {
        return visualParts;
    }

    /**
     * Sets the value of the visualParts property.
     *
     * @param value allowed object is
     *              {@link ArrayOfVisualPartTrans }
     */
    public void setVisualParts(ArrayOfVisualPartTrans value) {
        this.visualParts = value;
    }

}
