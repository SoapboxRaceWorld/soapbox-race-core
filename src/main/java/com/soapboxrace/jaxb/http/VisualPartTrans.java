//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.04.19 às 11:07:08 PM AMT 
//


package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de VisualPartTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="VisualPartTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PartHash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SlotHash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VisualPartTrans", propOrder = {
    "partHash",
    "slotHash"
})
public class VisualPartTrans {

    @XmlElement(name = "PartHash")
    protected int partHash;
    @XmlElement(name = "SlotHash")
    protected int slotHash;

    /**
     * Obtém o valor da propriedade partHash.
     * 
     */
    public int getPartHash() {
        return partHash;
    }

    /**
     * Define o valor da propriedade partHash.
     * 
     */
    public void setPartHash(int value) {
        this.partHash = value;
    }

    /**
     * Obtém o valor da propriedade slotHash.
     * 
     */
    public int getSlotHash() {
        return slotHash;
    }

    /**
     * Define o valor da propriedade slotHash.
     * 
     */
    public void setSlotHash(int value) {
        this.slotHash = value;
    }

}
