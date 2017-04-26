
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de UserInfo complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="UserInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="defaultPersonaIdx" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="personas" type="{}ArrayOfProfileData" minOccurs="0"/>
 *         &lt;element name="user" type="{}User" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserInfo", propOrder = {
    "defaultPersonaIdx",
    "personas",
    "user"
})
public class UserInfo {

    protected int defaultPersonaIdx;
    protected ArrayOfProfileData personas;
    protected User user;

    /**
     * Obtém o valor da propriedade defaultPersonaIdx.
     * 
     */
    public int getDefaultPersonaIdx() {
        return defaultPersonaIdx;
    }

    /**
     * Define o valor da propriedade defaultPersonaIdx.
     * 
     */
    public void setDefaultPersonaIdx(int value) {
        this.defaultPersonaIdx = value;
    }

    /**
     * Obtém o valor da propriedade personas.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProfileData }
     *     
     */
    public ArrayOfProfileData getPersonas() {
        return personas;
    }

    /**
     * Define o valor da propriedade personas.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProfileData }
     *     
     */
    public void setPersonas(ArrayOfProfileData value) {
        this.personas = value;
    }

    /**
     * Obtém o valor da propriedade user.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser() {
        return user;
    }

    /**
     * Define o valor da propriedade user.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser(User value) {
        this.user = value;
    }

}
