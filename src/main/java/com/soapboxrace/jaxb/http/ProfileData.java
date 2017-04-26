
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ProfileData complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ProfileData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Boost" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Cash" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="IconIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Level" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Motto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PercentToLevel" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="PersonaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Rating" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Rep" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="RepAtCurrentLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ccar" type="{}ArrayOfPersonaCCar" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfileData", propOrder = {
    "boost",
    "cash",
    "iconIndex",
    "level",
    "motto",
    "name",
    "percentToLevel",
    "personaId",
    "rating",
    "rep",
    "repAtCurrentLevel",
    "ccar"
})
public class ProfileData {

    @XmlElement(name = "Boost")
    protected double boost;
    @XmlElement(name = "Cash")
    protected double cash;
    @XmlElement(name = "IconIndex")
    protected int iconIndex;
    @XmlElement(name = "Level")
    protected int level;
    @XmlElement(name = "Motto")
    protected String motto;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "PercentToLevel")
    protected float percentToLevel;
    @XmlElement(name = "PersonaId")
    protected long personaId;
    @XmlElement(name = "Rating")
    protected double rating;
    @XmlElement(name = "Rep")
    protected double rep;
    @XmlElement(name = "RepAtCurrentLevel")
    protected int repAtCurrentLevel;
    protected ArrayOfPersonaCCar ccar;

    /**
     * Obtém o valor da propriedade boost.
     * 
     */
    public double getBoost() {
        return boost;
    }

    /**
     * Define o valor da propriedade boost.
     * 
     */
    public void setBoost(double value) {
        this.boost = value;
    }

    /**
     * Obtém o valor da propriedade cash.
     * 
     */
    public double getCash() {
        return cash;
    }

    /**
     * Define o valor da propriedade cash.
     * 
     */
    public void setCash(double value) {
        this.cash = value;
    }

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
     * Obtém o valor da propriedade motto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotto() {
        return motto;
    }

    /**
     * Define o valor da propriedade motto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotto(String value) {
        this.motto = value;
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
     * Obtém o valor da propriedade percentToLevel.
     * 
     */
    public float getPercentToLevel() {
        return percentToLevel;
    }

    /**
     * Define o valor da propriedade percentToLevel.
     * 
     */
    public void setPercentToLevel(float value) {
        this.percentToLevel = value;
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
     * Obtém o valor da propriedade rating.
     * 
     */
    public double getRating() {
        return rating;
    }

    /**
     * Define o valor da propriedade rating.
     * 
     */
    public void setRating(double value) {
        this.rating = value;
    }

    /**
     * Obtém o valor da propriedade rep.
     * 
     */
    public double getRep() {
        return rep;
    }

    /**
     * Define o valor da propriedade rep.
     * 
     */
    public void setRep(double value) {
        this.rep = value;
    }

    /**
     * Obtém o valor da propriedade repAtCurrentLevel.
     * 
     */
    public int getRepAtCurrentLevel() {
        return repAtCurrentLevel;
    }

    /**
     * Define o valor da propriedade repAtCurrentLevel.
     * 
     */
    public void setRepAtCurrentLevel(int value) {
        this.repAtCurrentLevel = value;
    }

    /**
     * Obtém o valor da propriedade ccar.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPersonaCCar }
     *     
     */
    public ArrayOfPersonaCCar getCcar() {
        return ccar;
    }

    /**
     * Define o valor da propriedade ccar.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPersonaCCar }
     *     
     */
    public void setCcar(ArrayOfPersonaCCar value) {
        this.ccar = value;
    }

}
