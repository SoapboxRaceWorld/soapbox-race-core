
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de BadgePacket complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="BadgePacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AchievementRankId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BadgeDefinitionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsRare" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Rarity" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="SlotId" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BadgePacket", propOrder = {
    "achievementRankId",
    "badgeDefinitionId",
    "isRare",
    "rarity",
    "slotId"
})
public class BadgePacket {

    @XmlElement(name = "AchievementRankId")
    protected int achievementRankId;
    @XmlElement(name = "BadgeDefinitionId")
    protected int badgeDefinitionId;
    @XmlElement(name = "IsRare")
    protected boolean isRare;
    @XmlElement(name = "Rarity")
    protected float rarity;
    @XmlElement(name = "SlotId")
    protected short slotId;

    /**
     * Obtém o valor da propriedade achievementRankId.
     * 
     */
    public int getAchievementRankId() {
        return achievementRankId;
    }

    /**
     * Define o valor da propriedade achievementRankId.
     * 
     */
    public void setAchievementRankId(int value) {
        this.achievementRankId = value;
    }

    /**
     * Obtém o valor da propriedade badgeDefinitionId.
     * 
     */
    public int getBadgeDefinitionId() {
        return badgeDefinitionId;
    }

    /**
     * Define o valor da propriedade badgeDefinitionId.
     * 
     */
    public void setBadgeDefinitionId(int value) {
        this.badgeDefinitionId = value;
    }

    /**
     * Obtém o valor da propriedade isRare.
     * 
     */
    public boolean isIsRare() {
        return isRare;
    }

    /**
     * Define o valor da propriedade isRare.
     * 
     */
    public void setIsRare(boolean value) {
        this.isRare = value;
    }

    /**
     * Obtém o valor da propriedade rarity.
     * 
     */
    public float getRarity() {
        return rarity;
    }

    /**
     * Define o valor da propriedade rarity.
     * 
     */
    public void setRarity(float value) {
        this.rarity = value;
    }

    /**
     * Obtém o valor da propriedade slotId.
     * 
     */
    public short getSlotId() {
        return slotId;
    }

    /**
     * Define o valor da propriedade slotId.
     * 
     */
    public void setSlotId(short value) {
        this.slotId = value;
    }

}
