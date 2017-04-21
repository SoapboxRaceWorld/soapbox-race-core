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
 * <p>Classe Java de LoginAnnouncementsDefinition complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="LoginAnnouncementsDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Announcements" type="{}ArrayOfLoginAnnouncementDefinition" minOccurs="0"/>
 *         &lt;element name="ImagesPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginAnnouncementsDefinition", propOrder = {
    "announcements",
    "imagesPath"
})
public class LoginAnnouncementsDefinition {

    @XmlElement(name = "Announcements")
    protected ArrayOfLoginAnnouncementDefinition announcements;
    @XmlElement(name = "ImagesPath")
    protected String imagesPath;

    /**
     * Obtém o valor da propriedade announcements.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfLoginAnnouncementDefinition }
     *     
     */
    public ArrayOfLoginAnnouncementDefinition getAnnouncements() {
        return announcements;
    }

    /**
     * Define o valor da propriedade announcements.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfLoginAnnouncementDefinition }
     *     
     */
    public void setAnnouncements(ArrayOfLoginAnnouncementDefinition value) {
        this.announcements = value;
    }

    /**
     * Obtém o valor da propriedade imagesPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagesPath() {
        return imagesPath;
    }

    /**
     * Define o valor da propriedade imagesPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagesPath(String value) {
        this.imagesPath = value;
    }

}
