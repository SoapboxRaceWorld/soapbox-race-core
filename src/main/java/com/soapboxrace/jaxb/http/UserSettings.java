//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2017.04.19 às 11:07:08 PM AMT 
//


package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de UserSettings complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="UserSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="desktopResHeight" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="desktopResWidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="fullscreen" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="gameResHeight" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="gameResWidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="globalDetailLevel" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
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
@XmlType(name = "UserSettings", propOrder = {
    "desktopResHeight",
    "desktopResWidth",
    "fullscreen",
    "gameResHeight",
    "gameResWidth",
    "globalDetailLevel",
    "userID"
})
public class UserSettings {

    @XmlSchemaType(name = "unsignedInt")
    protected long desktopResHeight;
    @XmlSchemaType(name = "unsignedInt")
    protected long desktopResWidth;
    protected boolean fullscreen;
    @XmlSchemaType(name = "unsignedInt")
    protected long gameResHeight;
    @XmlSchemaType(name = "unsignedInt")
    protected long gameResWidth;
    @XmlSchemaType(name = "unsignedInt")
    protected long globalDetailLevel;
    protected long userID;

    /**
     * Obtém o valor da propriedade desktopResHeight.
     * 
     */
    public long getDesktopResHeight() {
        return desktopResHeight;
    }

    /**
     * Define o valor da propriedade desktopResHeight.
     * 
     */
    public void setDesktopResHeight(long value) {
        this.desktopResHeight = value;
    }

    /**
     * Obtém o valor da propriedade desktopResWidth.
     * 
     */
    public long getDesktopResWidth() {
        return desktopResWidth;
    }

    /**
     * Define o valor da propriedade desktopResWidth.
     * 
     */
    public void setDesktopResWidth(long value) {
        this.desktopResWidth = value;
    }

    /**
     * Obtém o valor da propriedade fullscreen.
     * 
     */
    public boolean isFullscreen() {
        return fullscreen;
    }

    /**
     * Define o valor da propriedade fullscreen.
     * 
     */
    public void setFullscreen(boolean value) {
        this.fullscreen = value;
    }

    /**
     * Obtém o valor da propriedade gameResHeight.
     * 
     */
    public long getGameResHeight() {
        return gameResHeight;
    }

    /**
     * Define o valor da propriedade gameResHeight.
     * 
     */
    public void setGameResHeight(long value) {
        this.gameResHeight = value;
    }

    /**
     * Obtém o valor da propriedade gameResWidth.
     * 
     */
    public long getGameResWidth() {
        return gameResWidth;
    }

    /**
     * Define o valor da propriedade gameResWidth.
     * 
     */
    public void setGameResWidth(long value) {
        this.gameResWidth = value;
    }

    /**
     * Obtém o valor da propriedade globalDetailLevel.
     * 
     */
    public long getGlobalDetailLevel() {
        return globalDetailLevel;
    }

    /**
     * Define o valor da propriedade globalDetailLevel.
     * 
     */
    public void setGlobalDetailLevel(long value) {
        this.globalDetailLevel = value;
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
