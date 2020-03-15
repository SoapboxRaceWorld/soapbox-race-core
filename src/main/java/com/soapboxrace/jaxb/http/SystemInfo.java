
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SystemInfo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SystemInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Branch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChangeList" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClientVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClientVersionCheck" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Deployed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EntitlementsToDownload" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ForcePermanentSession" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="JidPrepender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LauncherServiceUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NucleusNamespace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NucleusNamespaceWeb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PersonaCacheTimeout" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PortalDomain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PortalSecureDomain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PortalStoreFailurePage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PortalTimeOut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ShardName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Time" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemInfo", propOrder = {
        "branch",
        "changeList",
        "clientVersion",
        "clientVersionCheck",
        "deployed",
        "entitlementsToDownload",
        "forcePermanentSession",
        "jidPrepender",
        "launcherServiceUrl",
        "nucleusNamespace",
        "nucleusNamespaceWeb",
        "personaCacheTimeout",
        "portalDomain",
        "portalSecureDomain",
        "portalStoreFailurePage",
        "portalTimeOut",
        "shardName",
        "time",
        "version"
})
public class SystemInfo {

    @XmlElement(name = "Branch")
    protected String branch;
    @XmlElement(name = "ChangeList")
    protected String changeList;
    @XmlElement(name = "ClientVersion")
    protected String clientVersion;
    @XmlElement(name = "ClientVersionCheck")
    protected boolean clientVersionCheck;
    @XmlElement(name = "Deployed")
    protected String deployed;
    @XmlElement(name = "EntitlementsToDownload")
    protected boolean entitlementsToDownload;
    @XmlElement(name = "ForcePermanentSession")
    protected boolean forcePermanentSession;
    @XmlElement(name = "JidPrepender")
    protected String jidPrepender;
    @XmlElement(name = "LauncherServiceUrl")
    protected String launcherServiceUrl;
    @XmlElement(name = "NucleusNamespace")
    protected String nucleusNamespace;
    @XmlElement(name = "NucleusNamespaceWeb")
    protected String nucleusNamespaceWeb;
    @XmlElement(name = "PersonaCacheTimeout")
    protected int personaCacheTimeout;
    @XmlElement(name = "PortalDomain")
    protected String portalDomain;
    @XmlElement(name = "PortalSecureDomain")
    protected String portalSecureDomain;
    @XmlElement(name = "PortalStoreFailurePage")
    protected String portalStoreFailurePage;
    @XmlElement(name = "PortalTimeOut")
    protected String portalTimeOut;
    @XmlElement(name = "ShardName")
    protected String shardName;
    @XmlElement(name = "Time", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar time;
    @XmlElement(name = "Version")
    protected String version;

    /**
     * Gets the value of the branch property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Sets the value of the branch property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBranch(String value) {
        this.branch = value;
    }

    /**
     * Gets the value of the changeList property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getChangeList() {
        return changeList;
    }

    /**
     * Sets the value of the changeList property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setChangeList(String value) {
        this.changeList = value;
    }

    /**
     * Gets the value of the clientVersion property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getClientVersion() {
        return clientVersion;
    }

    /**
     * Sets the value of the clientVersion property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setClientVersion(String value) {
        this.clientVersion = value;
    }

    /**
     * Gets the value of the clientVersionCheck property.
     */
    public boolean isClientVersionCheck() {
        return clientVersionCheck;
    }

    /**
     * Sets the value of the clientVersionCheck property.
     */
    public void setClientVersionCheck(boolean value) {
        this.clientVersionCheck = value;
    }

    /**
     * Gets the value of the deployed property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDeployed() {
        return deployed;
    }

    /**
     * Sets the value of the deployed property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDeployed(String value) {
        this.deployed = value;
    }

    /**
     * Gets the value of the entitlementsToDownload property.
     */
    public boolean isEntitlementsToDownload() {
        return entitlementsToDownload;
    }

    /**
     * Sets the value of the entitlementsToDownload property.
     */
    public void setEntitlementsToDownload(boolean value) {
        this.entitlementsToDownload = value;
    }

    /**
     * Gets the value of the forcePermanentSession property.
     */
    public boolean isForcePermanentSession() {
        return forcePermanentSession;
    }

    /**
     * Sets the value of the forcePermanentSession property.
     */
    public void setForcePermanentSession(boolean value) {
        this.forcePermanentSession = value;
    }

    /**
     * Gets the value of the jidPrepender property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getJidPrepender() {
        return jidPrepender;
    }

    /**
     * Sets the value of the jidPrepender property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setJidPrepender(String value) {
        this.jidPrepender = value;
    }

    /**
     * Gets the value of the launcherServiceUrl property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLauncherServiceUrl() {
        return launcherServiceUrl;
    }

    /**
     * Sets the value of the launcherServiceUrl property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLauncherServiceUrl(String value) {
        this.launcherServiceUrl = value;
    }

    /**
     * Gets the value of the nucleusNamespace property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNucleusNamespace() {
        return nucleusNamespace;
    }

    /**
     * Sets the value of the nucleusNamespace property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNucleusNamespace(String value) {
        this.nucleusNamespace = value;
    }

    /**
     * Gets the value of the nucleusNamespaceWeb property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNucleusNamespaceWeb() {
        return nucleusNamespaceWeb;
    }

    /**
     * Sets the value of the nucleusNamespaceWeb property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNucleusNamespaceWeb(String value) {
        this.nucleusNamespaceWeb = value;
    }

    /**
     * Gets the value of the personaCacheTimeout property.
     */
    public int getPersonaCacheTimeout() {
        return personaCacheTimeout;
    }

    /**
     * Sets the value of the personaCacheTimeout property.
     */
    public void setPersonaCacheTimeout(int value) {
        this.personaCacheTimeout = value;
    }

    /**
     * Gets the value of the portalDomain property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPortalDomain() {
        return portalDomain;
    }

    /**
     * Sets the value of the portalDomain property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPortalDomain(String value) {
        this.portalDomain = value;
    }

    /**
     * Gets the value of the portalSecureDomain property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPortalSecureDomain() {
        return portalSecureDomain;
    }

    /**
     * Sets the value of the portalSecureDomain property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPortalSecureDomain(String value) {
        this.portalSecureDomain = value;
    }

    /**
     * Gets the value of the portalStoreFailurePage property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPortalStoreFailurePage() {
        return portalStoreFailurePage;
    }

    /**
     * Sets the value of the portalStoreFailurePage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPortalStoreFailurePage(String value) {
        this.portalStoreFailurePage = value;
    }

    /**
     * Gets the value of the portalTimeOut property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPortalTimeOut() {
        return portalTimeOut;
    }

    /**
     * Sets the value of the portalTimeOut property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPortalTimeOut(String value) {
        this.portalTimeOut = value;
    }

    /**
     * Gets the value of the shardName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getShardName() {
        return shardName;
    }

    /**
     * Sets the value of the shardName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShardName(String value) {
        this.shardName = value;
    }

    /**
     * Gets the value of the time property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
