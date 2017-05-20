
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour LobbyEntrantRemoved complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="LobbyEntrantRemoved">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LobbyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="PersonaId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LobbyEntrantRemoved", propOrder = {
    "lobbyId",
    "personaId"
})
public class LobbyEntrantRemoved {

    @XmlElement(name = "LobbyId")
    protected long lobbyId;
    @XmlElement(name = "PersonaId")
    protected long personaId;

    /**
     * Obtient la valeur de la propriété lobbyId.
     * 
     */
    public long getLobbyId() {
        return lobbyId;
    }

    /**
     * Définit la valeur de la propriété lobbyId.
     * 
     */
    public void setLobbyId(long value) {
        this.lobbyId = value;
    }

    /**
     * Obtient la valeur de la propriété personaId.
     * 
     */
    public long getPersonaId() {
        return personaId;
    }

    /**
     * Définit la valeur de la propriété personaId.
     * 
     */
    public void setPersonaId(long value) {
        this.personaId = value;
    }

}
