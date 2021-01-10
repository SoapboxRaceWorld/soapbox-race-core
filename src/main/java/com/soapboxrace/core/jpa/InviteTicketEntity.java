/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "INVITE_TICKET")
// InviteTicketEntity.findByTicket
@NamedQueries({ //
        @NamedQuery(name = "InviteTicketEntity.findAll", query = "SELECT obj FROM InviteTicketEntity obj"),
        @NamedQuery(name = "InviteTicketEntity.findByTicket", query = "SELECT obj FROM InviteTicketEntity obj WHERE " +
                "obj.ticket = :ticket"), //
        @NamedQuery(name = "InviteTicketEntity.findByDiscordName", query = "SELECT obj FROM InviteTicketEntity obj " +
                "WHERE obj.discordName = :discordName")})
public class InviteTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TICKET", length = 255)
    private String ticket;

    @Column(name = "DISCORD_NAME", length = 255)
    private String discordName;

    @ManyToOne
    @JoinColumn(name = "USERID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_INVITE_TICKET_USER_USERID"))
    private UserEntity user;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getDiscordName() {
        return discordName;
    }

    public void setDiscordName(String discordName) {
        this.discordName = discordName;
    }

}
