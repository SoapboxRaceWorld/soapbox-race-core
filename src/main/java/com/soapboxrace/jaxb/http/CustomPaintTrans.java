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
 * <p>Classe Java de CustomPaintTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="CustomPaintTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Group" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Hue" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sat" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Slot" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Var" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomPaintTrans", propOrder = {
    "group",
    "hue",
    "sat",
    "slot",
    "var"
})
public class CustomPaintTrans {

    @XmlElement(name = "Group")
    protected int group;
    @XmlElement(name = "Hue")
    protected int hue;
    @XmlElement(name = "Sat")
    protected int sat;
    @XmlElement(name = "Slot")
    protected int slot;
    @XmlElement(name = "Var")
    protected int var;

    /**
     * Obtém o valor da propriedade group.
     * 
     */
    public int getGroup() {
        return group;
    }

    /**
     * Define o valor da propriedade group.
     * 
     */
    public void setGroup(int value) {
        this.group = value;
    }

    /**
     * Obtém o valor da propriedade hue.
     * 
     */
    public int getHue() {
        return hue;
    }

    /**
     * Define o valor da propriedade hue.
     * 
     */
    public void setHue(int value) {
        this.hue = value;
    }

    /**
     * Obtém o valor da propriedade sat.
     * 
     */
    public int getSat() {
        return sat;
    }

    /**
     * Define o valor da propriedade sat.
     * 
     */
    public void setSat(int value) {
        this.sat = value;
    }

    /**
     * Obtém o valor da propriedade slot.
     * 
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Define o valor da propriedade slot.
     * 
     */
    public void setSlot(int value) {
        this.slot = value;
    }

    /**
     * Obtém o valor da propriedade var.
     * 
     */
    public int getVar() {
        return var;
    }

    /**
     * Define o valor da propriedade var.
     * 
     */
    public void setVar(int value) {
        this.var = value;
    }

}
