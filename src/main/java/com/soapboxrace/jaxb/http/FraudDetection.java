
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
 * <p>Java class for FraudDetection complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FraudDetection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Checksum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IntValue1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IntValue2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IntValue3" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IntValue4" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsEncrypted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ModuleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModulePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModuleValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StringValue1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StringValue2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FraudDetection", propOrder = {
        "checksum",
        "intValue1",
        "intValue2",
        "intValue3",
        "intValue4",
        "isEncrypted",
        "moduleName",
        "modulePath",
        "moduleValue",
        "stringValue1",
        "stringValue2"
})
public class FraudDetection {

    @XmlElement(name = "Checksum")
    protected int checksum;
    @XmlElement(name = "IntValue1")
    protected int intValue1;
    @XmlElement(name = "IntValue2")
    protected int intValue2;
    @XmlElement(name = "IntValue3")
    protected int intValue3;
    @XmlElement(name = "IntValue4")
    protected int intValue4;
    @XmlElement(name = "IsEncrypted")
    protected boolean isEncrypted;
    @XmlElement(name = "ModuleName")
    protected String moduleName;
    @XmlElement(name = "ModulePath")
    protected String modulePath;
    @XmlElement(name = "ModuleValue")
    protected String moduleValue;
    @XmlElement(name = "StringValue1")
    protected String stringValue1;
    @XmlElement(name = "StringValue2")
    protected String stringValue2;

    /**
     * Gets the value of the checksum property.
     */
    public int getChecksum() {
        return checksum;
    }

    /**
     * Sets the value of the checksum property.
     */
    public void setChecksum(int value) {
        this.checksum = value;
    }

    /**
     * Gets the value of the intValue1 property.
     */
    public int getIntValue1() {
        return intValue1;
    }

    /**
     * Sets the value of the intValue1 property.
     */
    public void setIntValue1(int value) {
        this.intValue1 = value;
    }

    /**
     * Gets the value of the intValue2 property.
     */
    public int getIntValue2() {
        return intValue2;
    }

    /**
     * Sets the value of the intValue2 property.
     */
    public void setIntValue2(int value) {
        this.intValue2 = value;
    }

    /**
     * Gets the value of the intValue3 property.
     */
    public int getIntValue3() {
        return intValue3;
    }

    /**
     * Sets the value of the intValue3 property.
     */
    public void setIntValue3(int value) {
        this.intValue3 = value;
    }

    /**
     * Gets the value of the intValue4 property.
     */
    public int getIntValue4() {
        return intValue4;
    }

    /**
     * Sets the value of the intValue4 property.
     */
    public void setIntValue4(int value) {
        this.intValue4 = value;
    }

    /**
     * Gets the value of the isEncrypted property.
     */
    public boolean isIsEncrypted() {
        return isEncrypted;
    }

    /**
     * Sets the value of the isEncrypted property.
     */
    public void setIsEncrypted(boolean value) {
        this.isEncrypted = value;
    }

    /**
     * Gets the value of the moduleName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Sets the value of the moduleName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setModuleName(String value) {
        this.moduleName = value;
    }

    /**
     * Gets the value of the modulePath property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getModulePath() {
        return modulePath;
    }

    /**
     * Sets the value of the modulePath property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setModulePath(String value) {
        this.modulePath = value;
    }

    /**
     * Gets the value of the moduleValue property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getModuleValue() {
        return moduleValue;
    }

    /**
     * Sets the value of the moduleValue property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setModuleValue(String value) {
        this.moduleValue = value;
    }

    /**
     * Gets the value of the stringValue1 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getStringValue1() {
        return stringValue1;
    }

    /**
     * Sets the value of the stringValue1 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStringValue1(String value) {
        this.stringValue1 = value;
    }

    /**
     * Gets the value of the stringValue2 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getStringValue2() {
        return stringValue2;
    }

    /**
     * Sets the value of the stringValue2 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStringValue2(String value) {
        this.stringValue2 = value;
    }

}
