
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de FriendPersona complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="FriendPersona">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="iconIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="personaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="presence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="socialNetwork" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "FriendPersona", propOrder = {
    "iconIndex",
    "level",
    "name",
    "originalName",
    "personaId",
    "presence",
    "socialNetwork",
    "userId"
})
public class FriendPersona {

    protected int iconIndex;
    protected int level;
    protected String name;
    protected String originalName;
    protected long personaId;
    protected int presence;
    protected int socialNetwork;
    protected long userId;

    /**
     * Obtém o valor da propriedade iconIndex.
     * 
     */
    public int getIconIndex() {
        return iconIndex;
    }

    /**
     * Define o valor da propriedade iconIndex.
     * 
     */
    public void setIconIndex(int value) {
        this.iconIndex = value;
    }

    /**
     * Obtém o valor da propriedade level.
     * 
     */
    public int getLevel() {
        return level;
    }

    /**
     * Define o valor da propriedade level.
     * 
     */
    public void setLevel(int value) {
        this.level = value;
    }

    /**
     * Obtém o valor da propriedade name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Define o valor da propriedade name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtém o valor da propriedade originalName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * Define o valor da propriedade originalName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalName(String value) {
        this.originalName = value;
    }

    /**
     * Obtém o valor da propriedade personaId.
     * 
     */
    public long getPersonaId() {
        return personaId;
    }

    /**
     * Define o valor da propriedade personaId.
     * 
     */
    public void setPersonaId(long value) {
        this.personaId = value;
    }

    /**
     * Obtém o valor da propriedade presence.
     * 
     */
    public int getPresence() {
        return presence;
    }

    /**
     * Define o valor da propriedade presence.
     * 
     */
    public void setPresence(int value) {
        this.presence = value;
    }

    /**
     * Obtém o valor da propriedade socialNetwork.
     * 
     */
    public int getSocialNetwork() {
        return socialNetwork;
    }

    /**
     * Define o valor da propriedade socialNetwork.
     * 
     */
    public void setSocialNetwork(int value) {
        this.socialNetwork = value;
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
