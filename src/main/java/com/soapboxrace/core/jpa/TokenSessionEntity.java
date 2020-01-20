/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TOKEN_SESSION")
@NamedQueries({ //
        @NamedQuery(name = "TokenSessionEntity.findByUserId", query = "SELECT obj FROM TokenSessionEntity obj WHERE " +
                "obj.userEntity.id = :userId"), //
        @NamedQuery(name = "TokenSessionEntity.deleteByUserId", query = "DELETE FROM TokenSessionEntity obj WHERE obj" +
                ".userEntity.id = :userId"), //
        @NamedQuery(name = "TokenSessionEntity.updateRelayCrytoTicket", //
                query = "UPDATE TokenSessionEntity obj " //
                        + "SET obj.relayCryptoTicket = :relayCryptoTicket WHERE obj.activePersonaId = :personaId"), //
        @NamedQuery(name = "TokenSessionEntity.updateLobbyId", //
                query = "UPDATE TokenSessionEntity obj " //
                        + "SET obj.activeLobbyId = :activeLobbyId WHERE obj.activePersonaId = :personaId") //
})
public class TokenSessionEntity {

    @Id
    @Column(name = "ID", nullable = false)
    private String securityToken;

    @OneToOne(targetEntity = UserEntity.class, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "userId", referencedColumnName = "ID")
    private UserEntity userEntity;

    private Date expirationDate;

    private Long activePersonaId;

    private String relayCryptoTicket;

    private Long activeLobbyId;

    private boolean premium = false;

    private String clientHostIp;

    private String webToken;

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getActivePersonaId() {
        return activePersonaId;
    }

    public void setActivePersonaId(Long activePersonaId) {
        this.activePersonaId = activePersonaId;
    }

    public String getRelayCryptoTicket() {
        return relayCryptoTicket;
    }

    public void setRelayCryptoTicket(String relayCryptoTicket) {
        this.relayCryptoTicket = relayCryptoTicket;
    }

    public Long getActiveLobbyId() {
        return activeLobbyId;
    }

    public void setActiveLobbyId(Long activeLobbyId) {
        this.activeLobbyId = activeLobbyId;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getClientHostIp() {
        return clientHostIp;
    }

    public void setClientHostIp(String clientHostIp) {
        this.clientHostIp = clientHostIp;
    }

    public String getWebToken() {
        return webToken;
    }

    public void setWebToken(String webToken) {
        this.webToken = webToken;
    }
}
