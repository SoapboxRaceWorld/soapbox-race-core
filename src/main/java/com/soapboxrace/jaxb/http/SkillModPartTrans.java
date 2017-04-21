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
 * <p>Classe Java de SkillModPartTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="SkillModPartTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IsFixed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SkillModPartAttribHash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillModPartTrans", propOrder = {
    "isFixed",
    "skillModPartAttribHash"
})
public class SkillModPartTrans {

    @XmlElement(name = "IsFixed")
    protected boolean isFixed;
    @XmlElement(name = "SkillModPartAttribHash")
    protected int skillModPartAttribHash;

    /**
     * Obtém o valor da propriedade isFixed.
     * 
     */
    public boolean isIsFixed() {
        return isFixed;
    }

    /**
     * Define o valor da propriedade isFixed.
     * 
     */
    public void setIsFixed(boolean value) {
        this.isFixed = value;
    }

    /**
     * Obtém o valor da propriedade skillModPartAttribHash.
     * 
     */
    public int getSkillModPartAttribHash() {
        return skillModPartAttribHash;
    }

    /**
     * Define o valor da propriedade skillModPartAttribHash.
     * 
     */
    public void setSkillModPartAttribHash(int value) {
        this.skillModPartAttribHash = value;
    }

}
