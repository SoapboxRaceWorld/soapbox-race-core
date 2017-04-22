//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.04.22 às 07:20:05 AM AMT 
//


package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de InvalidBasketItemTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="InvalidBasketItemTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Reason" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvalidBasketItemTrans", propOrder = {
    "index",
    "reason"
})
public class InvalidBasketItemTrans {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Reason")
    protected int reason;

    /**
     * Obtém o valor da propriedade index.
     * 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Define o valor da propriedade index.
     * 
     */
    public void setIndex(int value) {
        this.index = value;
    }

    /**
     * Obtém o valor da propriedade reason.
     * 
     */
    public int getReason() {
        return reason;
    }

    /**
     * Define o valor da propriedade reason.
     * 
     */
    public void setReason(int value) {
        this.reason = value;
    }

}
