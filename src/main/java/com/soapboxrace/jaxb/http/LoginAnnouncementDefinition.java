
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de LoginAnnouncementDefinition complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="LoginAnnouncementDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Context" type="{}LoginAnnouncementContext"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ImageChecksum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ImageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Target" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{}LoginAnnouncementType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginAnnouncementDefinition", propOrder = {
    "context",
    "id",
    "imageChecksum",
    "imageUrl",
    "target",
    "type"
})
public class LoginAnnouncementDefinition {

    @XmlElement(name = "Context", required = true)
    @XmlSchemaType(name = "string")
    protected LoginAnnouncementContext context;
    @XmlElement(name = "Id")
    protected int id;
    @XmlElement(name = "ImageChecksum")
    protected int imageChecksum;
    @XmlElement(name = "ImageUrl")
    protected String imageUrl;
    @XmlElement(name = "Target")
    protected String target;
    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected LoginAnnouncementType type;

    /**
     * Obtém o valor da propriedade context.
     * 
     * @return
     *     possible object is
     *     {@link LoginAnnouncementContext }
     *     
     */
    public LoginAnnouncementContext getContext() {
        return context;
    }

    /**
     * Define o valor da propriedade context.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginAnnouncementContext }
     *     
     */
    public void setContext(LoginAnnouncementContext value) {
        this.context = value;
    }

    /**
     * Obtém o valor da propriedade id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Obtém o valor da propriedade imageChecksum.
     * 
     */
    public int getImageChecksum() {
        return imageChecksum;
    }

    /**
     * Define o valor da propriedade imageChecksum.
     * 
     */
    public void setImageChecksum(int value) {
        this.imageChecksum = value;
    }

    /**
     * Obtém o valor da propriedade imageUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Define o valor da propriedade imageUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageUrl(String value) {
        this.imageUrl = value;
    }

    /**
     * Obtém o valor da propriedade target.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        return target;
    }

    /**
     * Define o valor da propriedade target.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Obtém o valor da propriedade type.
     * 
     * @return
     *     possible object is
     *     {@link LoginAnnouncementType }
     *     
     */
    public LoginAnnouncementType getType() {
        return type;
    }

    /**
     * Define o valor da propriedade type.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginAnnouncementType }
     *     
     */
    public void setType(LoginAnnouncementType value) {
        this.type = value;
    }

}
