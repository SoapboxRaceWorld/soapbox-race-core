//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.04.20 às 09:47:17 PM AMT 
//


package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de User complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="User">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="address1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="address2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateCreated" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fullGameAccess" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idDigits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isComplete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="landlinePhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastAuthDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nickname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="realName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remoteUserId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="securityToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="starterPackEntitlementTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subscribeMsg" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tosVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User", propOrder = {
    "address1",
    "address2",
    "country",
    "dateCreated",
    "dob",
    "email",
    "emailStatus",
    "firstName",
    "fullGameAccess",
    "gender",
    "idDigits",
    "isComplete",
    "landlinePhone",
    "language",
    "lastAuthDate",
    "lastName",
    "mobile",
    "nickname",
    "postalCode",
    "realName",
    "reasonCode",
    "remoteUserId",
    "securityToken",
    "starterPackEntitlementTag",
    "status",
    "subscribeMsg",
    "tosVersion",
    "userId",
    "username"
})
public class User {

    protected String address1;
    protected String address2;
    protected String country;
    protected String dateCreated;
    protected String dob;
    protected String email;
    protected String emailStatus;
    protected String firstName;
    protected boolean fullGameAccess;
    protected String gender;
    protected String idDigits;
    protected boolean isComplete;
    protected String landlinePhone;
    protected String language;
    protected String lastAuthDate;
    protected String lastName;
    protected String mobile;
    protected String nickname;
    protected String postalCode;
    protected String realName;
    protected String reasonCode;
    protected long remoteUserId;
    protected String securityToken;
    protected String starterPackEntitlementTag;
    protected String status;
    protected boolean subscribeMsg;
    protected String tosVersion;
    protected long userId;
    protected String username;

    /**
     * Obtém o valor da propriedade address1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Define o valor da propriedade address1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress1(String value) {
        this.address1 = value;
    }

    /**
     * Obtém o valor da propriedade address2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Define o valor da propriedade address2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress2(String value) {
        this.address2 = value;
    }

    /**
     * Obtém o valor da propriedade country.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Define o valor da propriedade country.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Obtém o valor da propriedade dateCreated.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Define o valor da propriedade dateCreated.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateCreated(String value) {
        this.dateCreated = value;
    }

    /**
     * Obtém o valor da propriedade dob.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDob() {
        return dob;
    }

    /**
     * Define o valor da propriedade dob.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDob(String value) {
        this.dob = value;
    }

    /**
     * Obtém o valor da propriedade email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o valor da propriedade email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Obtém o valor da propriedade emailStatus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailStatus() {
        return emailStatus;
    }

    /**
     * Define o valor da propriedade emailStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailStatus(String value) {
        this.emailStatus = value;
    }

    /**
     * Obtém o valor da propriedade firstName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Define o valor da propriedade firstName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Obtém o valor da propriedade fullGameAccess.
     * 
     */
    public boolean isFullGameAccess() {
        return fullGameAccess;
    }

    /**
     * Define o valor da propriedade fullGameAccess.
     * 
     */
    public void setFullGameAccess(boolean value) {
        this.fullGameAccess = value;
    }

    /**
     * Obtém o valor da propriedade gender.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Define o valor da propriedade gender.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Obtém o valor da propriedade idDigits.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDigits() {
        return idDigits;
    }

    /**
     * Define o valor da propriedade idDigits.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDigits(String value) {
        this.idDigits = value;
    }

    /**
     * Obtém o valor da propriedade isComplete.
     * 
     */
    public boolean isIsComplete() {
        return isComplete;
    }

    /**
     * Define o valor da propriedade isComplete.
     * 
     */
    public void setIsComplete(boolean value) {
        this.isComplete = value;
    }

    /**
     * Obtém o valor da propriedade landlinePhone.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLandlinePhone() {
        return landlinePhone;
    }

    /**
     * Define o valor da propriedade landlinePhone.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLandlinePhone(String value) {
        this.landlinePhone = value;
    }

    /**
     * Obtém o valor da propriedade language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Define o valor da propriedade language.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Obtém o valor da propriedade lastAuthDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastAuthDate() {
        return lastAuthDate;
    }

    /**
     * Define o valor da propriedade lastAuthDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastAuthDate(String value) {
        this.lastAuthDate = value;
    }

    /**
     * Obtém o valor da propriedade lastName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Define o valor da propriedade lastName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Obtém o valor da propriedade mobile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Define o valor da propriedade mobile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    /**
     * Obtém o valor da propriedade nickname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Define o valor da propriedade nickname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickname(String value) {
        this.nickname = value;
    }

    /**
     * Obtém o valor da propriedade postalCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Define o valor da propriedade postalCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Obtém o valor da propriedade realName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Define o valor da propriedade realName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRealName(String value) {
        this.realName = value;
    }

    /**
     * Obtém o valor da propriedade reasonCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Define o valor da propriedade reasonCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCode(String value) {
        this.reasonCode = value;
    }

    /**
     * Obtém o valor da propriedade remoteUserId.
     * 
     */
    public long getRemoteUserId() {
        return remoteUserId;
    }

    /**
     * Define o valor da propriedade remoteUserId.
     * 
     */
    public void setRemoteUserId(long value) {
        this.remoteUserId = value;
    }

    /**
     * Obtém o valor da propriedade securityToken.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityToken() {
        return securityToken;
    }

    /**
     * Define o valor da propriedade securityToken.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityToken(String value) {
        this.securityToken = value;
    }

    /**
     * Obtém o valor da propriedade starterPackEntitlementTag.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStarterPackEntitlementTag() {
        return starterPackEntitlementTag;
    }

    /**
     * Define o valor da propriedade starterPackEntitlementTag.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStarterPackEntitlementTag(String value) {
        this.starterPackEntitlementTag = value;
    }

    /**
     * Obtém o valor da propriedade status.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define o valor da propriedade status.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Obtém o valor da propriedade subscribeMsg.
     * 
     */
    public boolean isSubscribeMsg() {
        return subscribeMsg;
    }

    /**
     * Define o valor da propriedade subscribeMsg.
     * 
     */
    public void setSubscribeMsg(boolean value) {
        this.subscribeMsg = value;
    }

    /**
     * Obtém o valor da propriedade tosVersion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTosVersion() {
        return tosVersion;
    }

    /**
     * Define o valor da propriedade tosVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTosVersion(String value) {
        this.tosVersion = value;
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

    /**
     * Obtém o valor da propriedade username.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define o valor da propriedade username.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

}
