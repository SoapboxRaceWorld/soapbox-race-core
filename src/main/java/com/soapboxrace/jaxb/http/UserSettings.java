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
 * <p>Classe Java de User_Settings complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="User_Settings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CarCacheAgeLimit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsRaceNowEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MaxCarCacheSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MinRaceNowLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VoipAvailable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="activatedHolidaySceneryGroups" type="{}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="activeHolidayIds" type="{}ArrayOfLong" minOccurs="0"/>
 *         &lt;element name="disactivatedHolidaySceneryGroups" type="{}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="firstTimeLogin" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="maxLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="starterPackApplied" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User_Settings", propOrder = {
    "carCacheAgeLimit",
    "isRaceNowEnabled",
    "maxCarCacheSize",
    "minRaceNowLevel",
    "voipAvailable",
    "activatedHolidaySceneryGroups",
    "activeHolidayIds",
    "disactivatedHolidaySceneryGroups",
    "firstTimeLogin",
    "maxLevel",
    "starterPackApplied",
    "userId"
})
public class UserSettings {

    @XmlElement(name = "CarCacheAgeLimit")
    protected int carCacheAgeLimit;
    @XmlElement(name = "IsRaceNowEnabled")
    protected boolean isRaceNowEnabled;
    @XmlElement(name = "MaxCarCacheSize")
    protected int maxCarCacheSize;
    @XmlElement(name = "MinRaceNowLevel")
    protected int minRaceNowLevel;
    @XmlElement(name = "VoipAvailable")
    protected boolean voipAvailable;
    protected ArrayOfString activatedHolidaySceneryGroups;
    protected ArrayOfLong activeHolidayIds;
    protected ArrayOfString disactivatedHolidaySceneryGroups;
    protected boolean firstTimeLogin;
    protected int maxLevel;
    protected boolean starterPackApplied;
    protected long userId;

    /**
     * Obtém o valor da propriedade carCacheAgeLimit.
     * 
     */
    public int getCarCacheAgeLimit() {
        return carCacheAgeLimit;
    }

    /**
     * Define o valor da propriedade carCacheAgeLimit.
     * 
     */
    public void setCarCacheAgeLimit(int value) {
        this.carCacheAgeLimit = value;
    }

    /**
     * Obtém o valor da propriedade isRaceNowEnabled.
     * 
     */
    public boolean isIsRaceNowEnabled() {
        return isRaceNowEnabled;
    }

    /**
     * Define o valor da propriedade isRaceNowEnabled.
     * 
     */
    public void setIsRaceNowEnabled(boolean value) {
        this.isRaceNowEnabled = value;
    }

    /**
     * Obtém o valor da propriedade maxCarCacheSize.
     * 
     */
    public int getMaxCarCacheSize() {
        return maxCarCacheSize;
    }

    /**
     * Define o valor da propriedade maxCarCacheSize.
     * 
     */
    public void setMaxCarCacheSize(int value) {
        this.maxCarCacheSize = value;
    }

    /**
     * Obtém o valor da propriedade minRaceNowLevel.
     * 
     */
    public int getMinRaceNowLevel() {
        return minRaceNowLevel;
    }

    /**
     * Define o valor da propriedade minRaceNowLevel.
     * 
     */
    public void setMinRaceNowLevel(int value) {
        this.minRaceNowLevel = value;
    }

    /**
     * Obtém o valor da propriedade voipAvailable.
     * 
     */
    public boolean isVoipAvailable() {
        return voipAvailable;
    }

    /**
     * Define o valor da propriedade voipAvailable.
     * 
     */
    public void setVoipAvailable(boolean value) {
        this.voipAvailable = value;
    }

    /**
     * Obtém o valor da propriedade activatedHolidaySceneryGroups.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getActivatedHolidaySceneryGroups() {
        return activatedHolidaySceneryGroups;
    }

    /**
     * Define o valor da propriedade activatedHolidaySceneryGroups.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setActivatedHolidaySceneryGroups(ArrayOfString value) {
        this.activatedHolidaySceneryGroups = value;
    }

    /**
     * Obtém o valor da propriedade activeHolidayIds.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfLong }
     *     
     */
    public ArrayOfLong getActiveHolidayIds() {
        return activeHolidayIds;
    }

    /**
     * Define o valor da propriedade activeHolidayIds.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfLong }
     *     
     */
    public void setActiveHolidayIds(ArrayOfLong value) {
        this.activeHolidayIds = value;
    }

    /**
     * Obtém o valor da propriedade disactivatedHolidaySceneryGroups.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getDisactivatedHolidaySceneryGroups() {
        return disactivatedHolidaySceneryGroups;
    }

    /**
     * Define o valor da propriedade disactivatedHolidaySceneryGroups.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setDisactivatedHolidaySceneryGroups(ArrayOfString value) {
        this.disactivatedHolidaySceneryGroups = value;
    }

    /**
     * Obtém o valor da propriedade firstTimeLogin.
     * 
     */
    public boolean isFirstTimeLogin() {
        return firstTimeLogin;
    }

    /**
     * Define o valor da propriedade firstTimeLogin.
     * 
     */
    public void setFirstTimeLogin(boolean value) {
        this.firstTimeLogin = value;
    }

    /**
     * Obtém o valor da propriedade maxLevel.
     * 
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Define o valor da propriedade maxLevel.
     * 
     */
    public void setMaxLevel(int value) {
        this.maxLevel = value;
    }

    /**
     * Obtém o valor da propriedade starterPackApplied.
     * 
     */
    public boolean isStarterPackApplied() {
        return starterPackApplied;
    }

    /**
     * Define o valor da propriedade starterPackApplied.
     * 
     */
    public void setStarterPackApplied(boolean value) {
        this.starterPackApplied = value;
    }

    /**
     * Obtém o valor da propriedade userId.
     * 
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Define o valor da propriedade userId.
     * 
     */
    public void setUserId(long value) {
        this.userId = value;
    }

}
