
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for User complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the address1 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the value of the address1 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAddress1(String value) {
        this.address1 = value;
    }

    /**
     * Gets the value of the address2 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the value of the address2 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAddress2(String value) {
        this.address2 = value;
    }

    /**
     * Gets the value of the country property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the dateCreated property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDateCreated(String value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the dob property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDob() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDob(String value) {
        this.dob = value;
    }

    /**
     * Gets the value of the email property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the emailStatus property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEmailStatus() {
        return emailStatus;
    }

    /**
     * Sets the value of the emailStatus property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEmailStatus(String value) {
        this.emailStatus = value;
    }

    /**
     * Gets the value of the firstName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the fullGameAccess property.
     */
    public boolean isFullGameAccess() {
        return fullGameAccess;
    }

    /**
     * Sets the value of the fullGameAccess property.
     */
    public void setFullGameAccess(boolean value) {
        this.fullGameAccess = value;
    }

    /**
     * Gets the value of the gender property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the idDigits property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIdDigits() {
        return idDigits;
    }

    /**
     * Sets the value of the idDigits property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIdDigits(String value) {
        this.idDigits = value;
    }

    /**
     * Gets the value of the isComplete property.
     */
    public boolean isIsComplete() {
        return isComplete;
    }

    /**
     * Sets the value of the isComplete property.
     */
    public void setIsComplete(boolean value) {
        this.isComplete = value;
    }

    /**
     * Gets the value of the landlinePhone property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLandlinePhone() {
        return landlinePhone;
    }

    /**
     * Sets the value of the landlinePhone property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLandlinePhone(String value) {
        this.landlinePhone = value;
    }

    /**
     * Gets the value of the language property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the lastAuthDate property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLastAuthDate() {
        return lastAuthDate;
    }

    /**
     * Sets the value of the lastAuthDate property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLastAuthDate(String value) {
        this.lastAuthDate = value;
    }

    /**
     * Gets the value of the lastName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the mobile property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the value of the mobile property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    /**
     * Gets the value of the nickname property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the value of the nickname property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNickname(String value) {
        this.nickname = value;
    }

    /**
     * Gets the value of the postalCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the realName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Sets the value of the realName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRealName(String value) {
        this.realName = value;
    }

    /**
     * Gets the value of the reasonCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReasonCode(String value) {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the remoteUserId property.
     */
    public long getRemoteUserId() {
        return remoteUserId;
    }

    /**
     * Sets the value of the remoteUserId property.
     */
    public void setRemoteUserId(long value) {
        this.remoteUserId = value;
    }

    /**
     * Gets the value of the securityToken property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSecurityToken() {
        return securityToken;
    }

    /**
     * Sets the value of the securityToken property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSecurityToken(String value) {
        this.securityToken = value;
    }

    /**
     * Gets the value of the starterPackEntitlementTag property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getStarterPackEntitlementTag() {
        return starterPackEntitlementTag;
    }

    /**
     * Sets the value of the starterPackEntitlementTag property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStarterPackEntitlementTag(String value) {
        this.starterPackEntitlementTag = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the subscribeMsg property.
     */
    public boolean isSubscribeMsg() {
        return subscribeMsg;
    }

    /**
     * Sets the value of the subscribeMsg property.
     */
    public void setSubscribeMsg(boolean value) {
        this.subscribeMsg = value;
    }

    /**
     * Gets the value of the tosVersion property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTosVersion() {
        return tosVersion;
    }

    /**
     * Sets the value of the tosVersion property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTosVersion(String value) {
        this.tosVersion = value;
    }

    /**
     * Gets the value of the userId property.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     */
    public void setUserId(long value) {
        this.userId = value;
    }

    /**
     * Gets the value of the username property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUsername(String value) {
        this.username = value;
    }

}
