
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for HardwareInfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="HardwareInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="availableMem" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="cpuBrand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpuid0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cpuid1_0" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="cpuid1_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="cpuid1_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="cpuid1_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="deviceID" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="excpuid1_0" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="excpuid1_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="excpuid1_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="excpuid1_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="gpuDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gpuDriverBuild" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="gpuDriverSubversion" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="gpuDriverVersion" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="gpuMemory" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="gpuProduct" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="osBuildNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="osMajorVersion" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="osMinorVersion" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="physicalCores" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="platformID" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="processAffinityMask" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="servicePack" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="systemAffinityMask" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="totalMemory" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="vendorID" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HardwareInfo", propOrder = {
        "availableMem",
        "cpuBrand",
        "cpuid0",
        "cpuid10",
        "cpuid11",
        "cpuid12",
        "cpuid13",
        "deviceID",
        "excpuid10",
        "excpuid11",
        "excpuid12",
        "excpuid13",
        "gpuDescription",
        "gpuDriverBuild",
        "gpuDriverSubversion",
        "gpuDriverVersion",
        "gpuMemory",
        "gpuProduct",
        "osBuildNumber",
        "osMajorVersion",
        "osMinorVersion",
        "physicalCores",
        "platformID",
        "processAffinityMask",
        "servicePack",
        "systemAffinityMask",
        "totalMemory",
        "userID",
        "vendorID"
})
public class HardwareInfo {

    @XmlSchemaType(name = "unsignedInt")
    protected long availableMem;
    protected String cpuBrand;
    protected String cpuid0;
    @XmlElement(name = "cpuid1_0")
    @XmlSchemaType(name = "unsignedInt")
    protected long cpuid10;
    @XmlElement(name = "cpuid1_1")
    @XmlSchemaType(name = "unsignedInt")
    protected long cpuid11;
    @XmlElement(name = "cpuid1_2")
    @XmlSchemaType(name = "unsignedInt")
    protected long cpuid12;
    @XmlElement(name = "cpuid1_3")
    @XmlSchemaType(name = "unsignedInt")
    protected long cpuid13;
    @XmlSchemaType(name = "unsignedInt")
    protected long deviceID;
    @XmlElement(name = "excpuid1_0")
    @XmlSchemaType(name = "unsignedInt")
    protected long excpuid10;
    @XmlElement(name = "excpuid1_1")
    @XmlSchemaType(name = "unsignedInt")
    protected long excpuid11;
    @XmlElement(name = "excpuid1_2")
    @XmlSchemaType(name = "unsignedInt")
    protected long excpuid12;
    @XmlElement(name = "excpuid1_3")
    @XmlSchemaType(name = "unsignedInt")
    protected long excpuid13;
    protected String gpuDescription;
    @XmlSchemaType(name = "unsignedInt")
    protected long gpuDriverBuild;
    @XmlSchemaType(name = "unsignedInt")
    protected long gpuDriverSubversion;
    @XmlSchemaType(name = "unsignedInt")
    protected long gpuDriverVersion;
    @XmlSchemaType(name = "unsignedInt")
    protected long gpuMemory;
    @XmlSchemaType(name = "unsignedInt")
    protected long gpuProduct;
    @XmlSchemaType(name = "unsignedInt")
    protected long osBuildNumber;
    @XmlSchemaType(name = "unsignedInt")
    protected long osMajorVersion;
    @XmlSchemaType(name = "unsignedInt")
    protected long osMinorVersion;
    @XmlSchemaType(name = "unsignedInt")
    protected long physicalCores;
    @XmlSchemaType(name = "unsignedInt")
    protected long platformID;
    @XmlSchemaType(name = "unsignedInt")
    protected long processAffinityMask;
    protected String servicePack;
    @XmlSchemaType(name = "unsignedInt")
    protected long systemAffinityMask;
    @XmlSchemaType(name = "unsignedInt")
    protected long totalMemory;
    protected long userID;
    @XmlSchemaType(name = "unsignedInt")
    protected long vendorID;

    /**
     * Gets the value of the availableMem property.
     */
    public long getAvailableMem() {
        return availableMem;
    }

    /**
     * Sets the value of the availableMem property.
     */
    public void setAvailableMem(long value) {
        this.availableMem = value;
    }

    /**
     * Gets the value of the cpuBrand property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCpuBrand() {
        return cpuBrand;
    }

    /**
     * Sets the value of the cpuBrand property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCpuBrand(String value) {
        this.cpuBrand = value;
    }

    /**
     * Gets the value of the cpuid0 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCpuid0() {
        return cpuid0;
    }

    /**
     * Sets the value of the cpuid0 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCpuid0(String value) {
        this.cpuid0 = value;
    }

    /**
     * Gets the value of the cpuid10 property.
     */
    public long getCpuid10() {
        return cpuid10;
    }

    /**
     * Sets the value of the cpuid10 property.
     */
    public void setCpuid10(long value) {
        this.cpuid10 = value;
    }

    /**
     * Gets the value of the cpuid11 property.
     */
    public long getCpuid11() {
        return cpuid11;
    }

    /**
     * Sets the value of the cpuid11 property.
     */
    public void setCpuid11(long value) {
        this.cpuid11 = value;
    }

    /**
     * Gets the value of the cpuid12 property.
     */
    public long getCpuid12() {
        return cpuid12;
    }

    /**
     * Sets the value of the cpuid12 property.
     */
    public void setCpuid12(long value) {
        this.cpuid12 = value;
    }

    /**
     * Gets the value of the cpuid13 property.
     */
    public long getCpuid13() {
        return cpuid13;
    }

    /**
     * Sets the value of the cpuid13 property.
     */
    public void setCpuid13(long value) {
        this.cpuid13 = value;
    }

    /**
     * Gets the value of the deviceID property.
     */
    public long getDeviceID() {
        return deviceID;
    }

    /**
     * Sets the value of the deviceID property.
     */
    public void setDeviceID(long value) {
        this.deviceID = value;
    }

    /**
     * Gets the value of the excpuid10 property.
     */
    public long getExcpuid10() {
        return excpuid10;
    }

    /**
     * Sets the value of the excpuid10 property.
     */
    public void setExcpuid10(long value) {
        this.excpuid10 = value;
    }

    /**
     * Gets the value of the excpuid11 property.
     */
    public long getExcpuid11() {
        return excpuid11;
    }

    /**
     * Sets the value of the excpuid11 property.
     */
    public void setExcpuid11(long value) {
        this.excpuid11 = value;
    }

    /**
     * Gets the value of the excpuid12 property.
     */
    public long getExcpuid12() {
        return excpuid12;
    }

    /**
     * Sets the value of the excpuid12 property.
     */
    public void setExcpuid12(long value) {
        this.excpuid12 = value;
    }

    /**
     * Gets the value of the excpuid13 property.
     */
    public long getExcpuid13() {
        return excpuid13;
    }

    /**
     * Sets the value of the excpuid13 property.
     */
    public void setExcpuid13(long value) {
        this.excpuid13 = value;
    }

    /**
     * Gets the value of the gpuDescription property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getGpuDescription() {
        return gpuDescription;
    }

    /**
     * Sets the value of the gpuDescription property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGpuDescription(String value) {
        this.gpuDescription = value;
    }

    /**
     * Gets the value of the gpuDriverBuild property.
     */
    public long getGpuDriverBuild() {
        return gpuDriverBuild;
    }

    /**
     * Sets the value of the gpuDriverBuild property.
     */
    public void setGpuDriverBuild(long value) {
        this.gpuDriverBuild = value;
    }

    /**
     * Gets the value of the gpuDriverSubversion property.
     */
    public long getGpuDriverSubversion() {
        return gpuDriverSubversion;
    }

    /**
     * Sets the value of the gpuDriverSubversion property.
     */
    public void setGpuDriverSubversion(long value) {
        this.gpuDriverSubversion = value;
    }

    /**
     * Gets the value of the gpuDriverVersion property.
     */
    public long getGpuDriverVersion() {
        return gpuDriverVersion;
    }

    /**
     * Sets the value of the gpuDriverVersion property.
     */
    public void setGpuDriverVersion(long value) {
        this.gpuDriverVersion = value;
    }

    /**
     * Gets the value of the gpuMemory property.
     */
    public long getGpuMemory() {
        return gpuMemory;
    }

    /**
     * Sets the value of the gpuMemory property.
     */
    public void setGpuMemory(long value) {
        this.gpuMemory = value;
    }

    /**
     * Gets the value of the gpuProduct property.
     */
    public long getGpuProduct() {
        return gpuProduct;
    }

    /**
     * Sets the value of the gpuProduct property.
     */
    public void setGpuProduct(long value) {
        this.gpuProduct = value;
    }

    /**
     * Gets the value of the osBuildNumber property.
     */
    public long getOsBuildNumber() {
        return osBuildNumber;
    }

    /**
     * Sets the value of the osBuildNumber property.
     */
    public void setOsBuildNumber(long value) {
        this.osBuildNumber = value;
    }

    /**
     * Gets the value of the osMajorVersion property.
     */
    public long getOsMajorVersion() {
        return osMajorVersion;
    }

    /**
     * Sets the value of the osMajorVersion property.
     */
    public void setOsMajorVersion(long value) {
        this.osMajorVersion = value;
    }

    /**
     * Gets the value of the osMinorVersion property.
     */
    public long getOsMinorVersion() {
        return osMinorVersion;
    }

    /**
     * Sets the value of the osMinorVersion property.
     */
    public void setOsMinorVersion(long value) {
        this.osMinorVersion = value;
    }

    /**
     * Gets the value of the physicalCores property.
     */
    public long getPhysicalCores() {
        return physicalCores;
    }

    /**
     * Sets the value of the physicalCores property.
     */
    public void setPhysicalCores(long value) {
        this.physicalCores = value;
    }

    /**
     * Gets the value of the platformID property.
     */
    public long getPlatformID() {
        return platformID;
    }

    /**
     * Sets the value of the platformID property.
     */
    public void setPlatformID(long value) {
        this.platformID = value;
    }

    /**
     * Gets the value of the processAffinityMask property.
     */
    public long getProcessAffinityMask() {
        return processAffinityMask;
    }

    /**
     * Sets the value of the processAffinityMask property.
     */
    public void setProcessAffinityMask(long value) {
        this.processAffinityMask = value;
    }

    /**
     * Gets the value of the servicePack property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getServicePack() {
        return servicePack;
    }

    /**
     * Sets the value of the servicePack property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setServicePack(String value) {
        this.servicePack = value;
    }

    /**
     * Gets the value of the systemAffinityMask property.
     */
    public long getSystemAffinityMask() {
        return systemAffinityMask;
    }

    /**
     * Sets the value of the systemAffinityMask property.
     */
    public void setSystemAffinityMask(long value) {
        this.systemAffinityMask = value;
    }

    /**
     * Gets the value of the totalMemory property.
     */
    public long getTotalMemory() {
        return totalMemory;
    }

    /**
     * Sets the value of the totalMemory property.
     */
    public void setTotalMemory(long value) {
        this.totalMemory = value;
    }

    /**
     * Gets the value of the userID property.
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     */
    public void setUserID(long value) {
        this.userID = value;
    }

    /**
     * Gets the value of the vendorID property.
     */
    public long getVendorID() {
        return vendorID;
    }

    /**
     * Sets the value of the vendorID property.
     */
    public void setVendorID(long value) {
        this.vendorID = value;
    }

}
