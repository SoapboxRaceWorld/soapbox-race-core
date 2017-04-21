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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de SystemInfo complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
 * 
 * 
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
     * Obtém o valor da propriedade branch.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Define o valor da propriedade branch.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranch(String value) {
        this.branch = value;
    }

    /**
     * Obtém o valor da propriedade changeList.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangeList() {
        return changeList;
    }

    /**
     * Define o valor da propriedade changeList.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangeList(String value) {
        this.changeList = value;
    }

    /**
     * Obtém o valor da propriedade clientVersion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientVersion() {
        return clientVersion;
    }

    /**
     * Define o valor da propriedade clientVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientVersion(String value) {
        this.clientVersion = value;
    }

    /**
     * Obtém o valor da propriedade clientVersionCheck.
     * 
     */
    public boolean isClientVersionCheck() {
        return clientVersionCheck;
    }

    /**
     * Define o valor da propriedade clientVersionCheck.
     * 
     */
    public void setClientVersionCheck(boolean value) {
        this.clientVersionCheck = value;
    }

    /**
     * Obtém o valor da propriedade deployed.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeployed() {
        return deployed;
    }

    /**
     * Define o valor da propriedade deployed.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeployed(String value) {
        this.deployed = value;
    }

    /**
     * Obtém o valor da propriedade entitlementsToDownload.
     * 
     */
    public boolean isEntitlementsToDownload() {
        return entitlementsToDownload;
    }

    /**
     * Define o valor da propriedade entitlementsToDownload.
     * 
     */
    public void setEntitlementsToDownload(boolean value) {
        this.entitlementsToDownload = value;
    }

    /**
     * Obtém o valor da propriedade forcePermanentSession.
     * 
     */
    public boolean isForcePermanentSession() {
        return forcePermanentSession;
    }

    /**
     * Define o valor da propriedade forcePermanentSession.
     * 
     */
    public void setForcePermanentSession(boolean value) {
        this.forcePermanentSession = value;
    }

    /**
     * Obtém o valor da propriedade jidPrepender.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJidPrepender() {
        return jidPrepender;
    }

    /**
     * Define o valor da propriedade jidPrepender.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJidPrepender(String value) {
        this.jidPrepender = value;
    }

    /**
     * Obtém o valor da propriedade launcherServiceUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLauncherServiceUrl() {
        return launcherServiceUrl;
    }

    /**
     * Define o valor da propriedade launcherServiceUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLauncherServiceUrl(String value) {
        this.launcherServiceUrl = value;
    }

    /**
     * Obtém o valor da propriedade nucleusNamespace.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNucleusNamespace() {
        return nucleusNamespace;
    }

    /**
     * Define o valor da propriedade nucleusNamespace.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNucleusNamespace(String value) {
        this.nucleusNamespace = value;
    }

    /**
     * Obtém o valor da propriedade nucleusNamespaceWeb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNucleusNamespaceWeb() {
        return nucleusNamespaceWeb;
    }

    /**
     * Define o valor da propriedade nucleusNamespaceWeb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNucleusNamespaceWeb(String value) {
        this.nucleusNamespaceWeb = value;
    }

    /**
     * Obtém o valor da propriedade personaCacheTimeout.
     * 
     */
    public int getPersonaCacheTimeout() {
        return personaCacheTimeout;
    }

    /**
     * Define o valor da propriedade personaCacheTimeout.
     * 
     */
    public void setPersonaCacheTimeout(int value) {
        this.personaCacheTimeout = value;
    }

    /**
     * Obtém o valor da propriedade portalDomain.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortalDomain() {
        return portalDomain;
    }

    /**
     * Define o valor da propriedade portalDomain.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortalDomain(String value) {
        this.portalDomain = value;
    }

    /**
     * Obtém o valor da propriedade portalSecureDomain.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortalSecureDomain() {
        return portalSecureDomain;
    }

    /**
     * Define o valor da propriedade portalSecureDomain.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortalSecureDomain(String value) {
        this.portalSecureDomain = value;
    }

    /**
     * Obtém o valor da propriedade portalStoreFailurePage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortalStoreFailurePage() {
        return portalStoreFailurePage;
    }

    /**
     * Define o valor da propriedade portalStoreFailurePage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortalStoreFailurePage(String value) {
        this.portalStoreFailurePage = value;
    }

    /**
     * Obtém o valor da propriedade portalTimeOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortalTimeOut() {
        return portalTimeOut;
    }

    /**
     * Define o valor da propriedade portalTimeOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortalTimeOut(String value) {
        this.portalTimeOut = value;
    }

    /**
     * Obtém o valor da propriedade shardName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShardName() {
        return shardName;
    }

    /**
     * Define o valor da propriedade shardName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShardName(String value) {
        this.shardName = value;
    }

    /**
     * Obtém o valor da propriedade time.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * Define o valor da propriedade time.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    /**
     * Obtém o valor da propriedade version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Define o valor da propriedade version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
