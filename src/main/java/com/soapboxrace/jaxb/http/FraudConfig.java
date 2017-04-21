//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.04.20 às 09:47:17 PM AMT 
//


package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de FraudConfig complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="FraudConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enabledBitField" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="gameFileFreq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="moduleFreq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startUpFreq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FraudConfig", propOrder = {
    "enabledBitField",
    "gameFileFreq",
    "moduleFreq",
    "startUpFreq",
    "userID"
})
public class FraudConfig {

    protected int enabledBitField;
    protected int gameFileFreq;
    protected int moduleFreq;
    protected int startUpFreq;
    protected long userID;

    /**
     * Obtém o valor da propriedade enabledBitField.
     * 
     */
    public int getEnabledBitField() {
        return enabledBitField;
    }

    /**
     * Define o valor da propriedade enabledBitField.
     * 
     */
    public void setEnabledBitField(int value) {
        this.enabledBitField = value;
    }

    /**
     * Obtém o valor da propriedade gameFileFreq.
     * 
     */
    public int getGameFileFreq() {
        return gameFileFreq;
    }

    /**
     * Define o valor da propriedade gameFileFreq.
     * 
     */
    public void setGameFileFreq(int value) {
        this.gameFileFreq = value;
    }

    /**
     * Obtém o valor da propriedade moduleFreq.
     * 
     */
    public int getModuleFreq() {
        return moduleFreq;
    }

    /**
     * Define o valor da propriedade moduleFreq.
     * 
     */
    public void setModuleFreq(int value) {
        this.moduleFreq = value;
    }

    /**
     * Obtém o valor da propriedade startUpFreq.
     * 
     */
    public int getStartUpFreq() {
        return startUpFreq;
    }

    /**
     * Define o valor da propriedade startUpFreq.
     * 
     */
    public void setStartUpFreq(int value) {
        this.startUpFreq = value;
    }

    /**
     * Obtém o valor da propriedade userID.
     * 
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Define o valor da propriedade userID.
     * 
     */
    public void setUserID(long value) {
        this.userID = value;
    }

}
