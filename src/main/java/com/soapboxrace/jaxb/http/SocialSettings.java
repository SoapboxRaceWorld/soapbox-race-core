
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de SocialSettings complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="SocialSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AppearOffline" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DeclineGroupInvite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DeclineIncommingFriendRequests" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DeclinePrivateInvite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HideOfflineFriends" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ShowNewsOnSignIn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ShowOnlyPlayersInSameChatChannel" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocialSettings", propOrder = {
    "appearOffline",
    "declineGroupInvite",
    "declineIncommingFriendRequests",
    "declinePrivateInvite",
    "hideOfflineFriends",
    "showNewsOnSignIn",
    "showOnlyPlayersInSameChatChannel"
})
public class SocialSettings {

    @XmlElement(name = "AppearOffline")
    protected boolean appearOffline;
    @XmlElement(name = "DeclineGroupInvite")
    protected int declineGroupInvite;
    @XmlElement(name = "DeclineIncommingFriendRequests")
    protected boolean declineIncommingFriendRequests;
    @XmlElement(name = "DeclinePrivateInvite")
    protected int declinePrivateInvite;
    @XmlElement(name = "HideOfflineFriends")
    protected boolean hideOfflineFriends;
    @XmlElement(name = "ShowNewsOnSignIn")
    protected boolean showNewsOnSignIn;
    @XmlElement(name = "ShowOnlyPlayersInSameChatChannel")
    protected boolean showOnlyPlayersInSameChatChannel;

    /**
     * Obtém o valor da propriedade appearOffline.
     * 
     */
    public boolean isAppearOffline() {
        return appearOffline;
    }

    /**
     * Define o valor da propriedade appearOffline.
     * 
     */
    public void setAppearOffline(boolean value) {
        this.appearOffline = value;
    }

    /**
     * Obtém o valor da propriedade declineGroupInvite.
     * 
     */
    public int getDeclineGroupInvite() {
        return declineGroupInvite;
    }

    /**
     * Define o valor da propriedade declineGroupInvite.
     * 
     */
    public void setDeclineGroupInvite(int value) {
        this.declineGroupInvite = value;
    }

    /**
     * Obtém o valor da propriedade declineIncommingFriendRequests.
     * 
     */
    public boolean isDeclineIncommingFriendRequests() {
        return declineIncommingFriendRequests;
    }

    /**
     * Define o valor da propriedade declineIncommingFriendRequests.
     * 
     */
    public void setDeclineIncommingFriendRequests(boolean value) {
        this.declineIncommingFriendRequests = value;
    }

    /**
     * Obtém o valor da propriedade declinePrivateInvite.
     * 
     */
    public int getDeclinePrivateInvite() {
        return declinePrivateInvite;
    }

    /**
     * Define o valor da propriedade declinePrivateInvite.
     * 
     */
    public void setDeclinePrivateInvite(int value) {
        this.declinePrivateInvite = value;
    }

    /**
     * Obtém o valor da propriedade hideOfflineFriends.
     * 
     */
    public boolean isHideOfflineFriends() {
        return hideOfflineFriends;
    }

    /**
     * Define o valor da propriedade hideOfflineFriends.
     * 
     */
    public void setHideOfflineFriends(boolean value) {
        this.hideOfflineFriends = value;
    }

    /**
     * Obtém o valor da propriedade showNewsOnSignIn.
     * 
     */
    public boolean isShowNewsOnSignIn() {
        return showNewsOnSignIn;
    }

    /**
     * Define o valor da propriedade showNewsOnSignIn.
     * 
     */
    public void setShowNewsOnSignIn(boolean value) {
        this.showNewsOnSignIn = value;
    }

    /**
     * Obtém o valor da propriedade showOnlyPlayersInSameChatChannel.
     * 
     */
    public boolean isShowOnlyPlayersInSameChatChannel() {
        return showOnlyPlayersInSameChatChannel;
    }

    /**
     * Define o valor da propriedade showOnlyPlayersInSameChatChannel.
     * 
     */
    public void setShowOnlyPlayersInSameChatChannel(boolean value) {
        this.showOnlyPlayersInSameChatChannel = value;
    }

}
