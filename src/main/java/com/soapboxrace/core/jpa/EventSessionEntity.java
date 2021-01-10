/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "EVENT_SESSION")
public class EventSessionEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EVENTID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_EVENT_SESSION_EVENT_EVENTID"))
    private EventEntity event;

    @OneToOne
    @JoinColumn(name = "LOBBYID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_EVENT_SESSION_LOBBY_LOBBYID"))
    private LobbyEntity lobby;

    @OneToOne
    @JoinColumn(name = "NEXTLOBBYID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_EVENT_SESSION_LOBBY_NEXTLOBBYID"))
    private LobbyEntity nextLobby;

    @Column(name = "STARTED")
    private Long started;

    @Column(name = "ENDED")
    private Long ended;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public LobbyEntity getLobby() {
        return lobby;
    }

    public void setLobby(LobbyEntity lobby) {
        this.lobby = lobby;
    }

    public LobbyEntity getNextLobby() {
        return nextLobby;
    }

    public void setNextLobby(LobbyEntity nextLobby) {
        this.nextLobby = nextLobby;
    }

    public Long getEnded() {
        return ended;
    }

    public void setEnded(Long ended) {
        this.ended = ended;
    }

    public Long getStarted() {
        return started;
    }

    public void setStarted(Long started) {
        this.started = started;
    }
}
