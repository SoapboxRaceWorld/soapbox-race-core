/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "LOBBY_ENTRANT")
@NamedQueries({ //
        @NamedQuery(name = "LobbyEntrantEntity.deleteByPersona", query = "DELETE FROM LobbyEntrantEntity obj WHERE " +
                "obj.persona = :persona"), //
        @NamedQuery(name = "LobbyEntrantEntity.deleteByPersonaAndLobby", query = "DELETE FROM LobbyEntrantEntity obj WHERE " +
                "obj.persona = :persona and obj.lobby = :lobby") //
})
public class LobbyEntrantEntity implements Comparable<LobbyEntrantEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @XmlTransient
    @JoinColumn(name = "PERSONAID", referencedColumnName = "ID", foreignKey = @ForeignKey(name =
            "FK_LOBBY_ENTRANT_PERSONA_PERSONAID"))
    private PersonaEntity persona;

    @ManyToOne
    @XmlTransient
    @JoinColumn(name = "LOBBYID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_LOBBY_ENTRANT_LOBBY_LOBBYID"))
    private LobbyEntity lobby;

    private int gridIndex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PersonaEntity persona) {
        this.persona = persona;
    }

    public LobbyEntity getLobby() {
        return lobby;
    }

    public void setLobby(LobbyEntity lobby) {
        this.lobby = lobby;
    }

    @Override
    public int compareTo(LobbyEntrantEntity lobbyEntrantEntity) {
        return this.getPersona().getPersonaId().compareTo(lobbyEntrantEntity.getPersona().getPersonaId());
    }

    public int getGridIndex() {
        return gridIndex;
    }

    public void setGridIndex(int gridIndex) {
        this.gridIndex = gridIndex;
    }

}
