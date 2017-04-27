
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de EventsPacket complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="EventsPacket">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Events" type="{}ArrayOfEventDefinition" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventsPacket", propOrder = {
    "events"
})
public class EventsPacket {

    @XmlElement(name = "Events")
    protected ArrayOfEventDefinition events;

    /**
     * Obtém o valor da propriedade events.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEventDefinition }
     *     
     */
    public ArrayOfEventDefinition getEvents() {
        return events;
    }

    /**
     * Define o valor da propriedade events.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEventDefinition }
     *     
     */
    public void setEvents(ArrayOfEventDefinition value) {
        this.events = value;
    }

}
