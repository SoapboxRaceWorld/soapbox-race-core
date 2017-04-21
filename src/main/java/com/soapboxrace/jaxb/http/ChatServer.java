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
 * <p>Classe Java de chatServer complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="chatServer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Rooms" type="{}ArrayOfChatRoom" minOccurs="0"/>
 *         &lt;element name="ip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="prefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chatServer", propOrder = {
    "rooms",
    "ip",
    "port",
    "prefix"
})
public class ChatServer {

    @XmlElement(name = "Rooms")
    protected ArrayOfChatRoom rooms;
    protected String ip;
    protected int port;
    protected String prefix;

    /**
     * Obtém o valor da propriedade rooms.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChatRoom }
     *     
     */
    public ArrayOfChatRoom getRooms() {
        return rooms;
    }

    /**
     * Define o valor da propriedade rooms.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChatRoom }
     *     
     */
    public void setRooms(ArrayOfChatRoom value) {
        this.rooms = value;
    }

    /**
     * Obtém o valor da propriedade ip.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp() {
        return ip;
    }

    /**
     * Define o valor da propriedade ip.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp(String value) {
        this.ip = value;
    }

    /**
     * Obtém o valor da propriedade port.
     * 
     */
    public int getPort() {
        return port;
    }

    /**
     * Define o valor da propriedade port.
     * 
     */
    public void setPort(int value) {
        this.port = value;
    }

    /**
     * Obtém o valor da propriedade prefix.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Define o valor da propriedade prefix.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefix(String value) {
        this.prefix = value;
    }

}
