
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
 * <p>Java class for Accolades complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Accolades">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FinalRewards" type="{}Reward" minOccurs="0"/>
 *         &lt;element name="HasLeveledUp" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LuckyDrawInfo" type="{}LuckyDrawInfo" minOccurs="0"/>
 *         &lt;element name="OriginalRewards" type="{}Reward" minOccurs="0"/>
 *         &lt;element name="RewardInfo" type="{}ArrayOfRewardPart" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Accolades", propOrder = {
        "finalRewards",
        "hasLeveledUp",
        "luckyDrawInfo",
        "originalRewards",
        "rewardInfo"
})
public class Accolades {

    @XmlElement(name = "FinalRewards")
    protected Reward finalRewards;
    @XmlElement(name = "HasLeveledUp")
    protected boolean hasLeveledUp;
    @XmlElement(name = "LuckyDrawInfo")
    protected LuckyDrawInfo luckyDrawInfo;
    @XmlElement(name = "OriginalRewards")
    protected Reward originalRewards;
    @XmlElement(name = "RewardInfo")
    protected ArrayOfRewardPart rewardInfo;

    /**
     * Gets the value of the finalRewards property.
     *
     * @return possible object is
     * {@link Reward }
     */
    public Reward getFinalRewards() {
        return finalRewards;
    }

    /**
     * Sets the value of the finalRewards property.
     *
     * @param value allowed object is
     *              {@link Reward }
     */
    public void setFinalRewards(Reward value) {
        this.finalRewards = value;
    }

    /**
     * Gets the value of the hasLeveledUp property.
     */
    public boolean isHasLeveledUp() {
        return hasLeveledUp;
    }

    /**
     * Sets the value of the hasLeveledUp property.
     */
    public void setHasLeveledUp(boolean value) {
        this.hasLeveledUp = value;
    }

    /**
     * Gets the value of the luckyDrawInfo property.
     *
     * @return possible object is
     * {@link LuckyDrawInfo }
     */
    public LuckyDrawInfo getLuckyDrawInfo() {
        return luckyDrawInfo;
    }

    /**
     * Sets the value of the luckyDrawInfo property.
     *
     * @param value allowed object is
     *              {@link LuckyDrawInfo }
     */
    public void setLuckyDrawInfo(LuckyDrawInfo value) {
        this.luckyDrawInfo = value;
    }

    /**
     * Gets the value of the originalRewards property.
     *
     * @return possible object is
     * {@link Reward }
     */
    public Reward getOriginalRewards() {
        return originalRewards;
    }

    /**
     * Sets the value of the originalRewards property.
     *
     * @param value allowed object is
     *              {@link Reward }
     */
    public void setOriginalRewards(Reward value) {
        this.originalRewards = value;
    }

    /**
     * Gets the value of the rewardInfo property.
     *
     * @return possible object is
     * {@link ArrayOfRewardPart }
     */
    public ArrayOfRewardPart getRewardInfo() {
        return rewardInfo;
    }

    /**
     * Sets the value of the rewardInfo property.
     *
     * @param value allowed object is
     *              {@link ArrayOfRewardPart }
     */
    public void setRewardInfo(ArrayOfRewardPart value) {
        this.rewardInfo = value;
    }

}
