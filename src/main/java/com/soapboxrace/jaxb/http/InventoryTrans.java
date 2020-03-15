
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
 * <p>Java class for InventoryTrans complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="InventoryTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InventoryItems" type="{}ArrayOfInventoryItemTrans" minOccurs="0"/>
 *         &lt;element name="PerformancePartsCapacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="PerformancePartsUsedSlotCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="SkillModPartsCapacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="SkillModPartsUsedSlotCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="VisualPartsCapacity" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="VisualPartsUsedSlotCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InventoryTrans", propOrder = {
        "inventoryItems",
        "performancePartsCapacity",
        "performancePartsUsedSlotCount",
        "skillModPartsCapacity",
        "skillModPartsUsedSlotCount",
        "visualPartsCapacity",
        "visualPartsUsedSlotCount"
})
public class InventoryTrans {

    @XmlElement(name = "InventoryItems")
    protected ArrayOfInventoryItemTrans inventoryItems;
    @XmlElement(name = "PerformancePartsCapacity")
    protected long performancePartsCapacity;
    @XmlElement(name = "PerformancePartsUsedSlotCount")
    protected long performancePartsUsedSlotCount;
    @XmlElement(name = "SkillModPartsCapacity")
    protected long skillModPartsCapacity;
    @XmlElement(name = "SkillModPartsUsedSlotCount")
    protected long skillModPartsUsedSlotCount;
    @XmlElement(name = "VisualPartsCapacity")
    protected long visualPartsCapacity;
    @XmlElement(name = "VisualPartsUsedSlotCount")
    protected long visualPartsUsedSlotCount;

    /**
     * Gets the value of the inventoryItems property.
     *
     * @return possible object is
     * {@link ArrayOfInventoryItemTrans }
     */
    public ArrayOfInventoryItemTrans getInventoryItems() {
        return inventoryItems;
    }

    /**
     * Sets the value of the inventoryItems property.
     *
     * @param value allowed object is
     *              {@link ArrayOfInventoryItemTrans }
     */
    public void setInventoryItems(ArrayOfInventoryItemTrans value) {
        this.inventoryItems = value;
    }

    /**
     * Gets the value of the performancePartsCapacity property.
     */
    public long getPerformancePartsCapacity() {
        return performancePartsCapacity;
    }

    /**
     * Sets the value of the performancePartsCapacity property.
     */
    public void setPerformancePartsCapacity(long value) {
        this.performancePartsCapacity = value;
    }

    /**
     * Gets the value of the performancePartsUsedSlotCount property.
     */
    public long getPerformancePartsUsedSlotCount() {
        return performancePartsUsedSlotCount;
    }

    /**
     * Sets the value of the performancePartsUsedSlotCount property.
     */
    public void setPerformancePartsUsedSlotCount(long value) {
        this.performancePartsUsedSlotCount = value;
    }

    /**
     * Gets the value of the skillModPartsCapacity property.
     */
    public long getSkillModPartsCapacity() {
        return skillModPartsCapacity;
    }

    /**
     * Sets the value of the skillModPartsCapacity property.
     */
    public void setSkillModPartsCapacity(long value) {
        this.skillModPartsCapacity = value;
    }

    /**
     * Gets the value of the skillModPartsUsedSlotCount property.
     */
    public long getSkillModPartsUsedSlotCount() {
        return skillModPartsUsedSlotCount;
    }

    /**
     * Sets the value of the skillModPartsUsedSlotCount property.
     */
    public void setSkillModPartsUsedSlotCount(long value) {
        this.skillModPartsUsedSlotCount = value;
    }

    /**
     * Gets the value of the visualPartsCapacity property.
     */
    public long getVisualPartsCapacity() {
        return visualPartsCapacity;
    }

    /**
     * Sets the value of the visualPartsCapacity property.
     */
    public void setVisualPartsCapacity(long value) {
        this.visualPartsCapacity = value;
    }

    /**
     * Gets the value of the visualPartsUsedSlotCount property.
     */
    public long getVisualPartsUsedSlotCount() {
        return visualPartsUsedSlotCount;
    }

    /**
     * Sets the value of the visualPartsUsedSlotCount property.
     */
    public void setVisualPartsUsedSlotCount(long value) {
        this.visualPartsUsedSlotCount = value;
    }

}
