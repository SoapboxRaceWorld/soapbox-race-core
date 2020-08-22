/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER")
@NamedQueries({ //
        @NamedQuery(name = "UserEntity.findByEmail", query = "SELECT obj FROM UserEntity obj WHERE obj.email = " +
                ":email"), //
        @NamedQuery(name = "UserEntity.findByIpAddress", query = "SELECT obj FROM UserEntity obj WHERE obj.ipAddress " +
                "= :ipAddress"), //
        @NamedQuery(name = "UserEntity.countUsers", query = "SELECT COUNT(obj) FROM UserEntity obj") //
})
public class UserEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD", length = 50)
    private String password;

    @Column(name = "HWID")
    private String hwid;

    private String gameHardwareHash;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @OneToMany(mappedBy = "user", targetEntity = PersonaEntity.class, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.JOIN)
    private List<PersonaEntity> personas;

    @Column(name = "premium")
    private boolean premium;

    @Column(name = "isAdmin")
    private boolean isAdmin;

    @Column(name = "isLocked")
    private boolean isLocked;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;

    @Column(columnDefinition = "integer default 0")
    private Integer selectedPersonaIndex = 0;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PersonaEntity> getPersonas() {
        return personas;
    }

    public String getHwid() {
        return hwid;
    }

    public void setHwid(String hwid) {
        this.hwid = hwid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean ownsPersona(Long id) {
        return this.personas.stream().anyMatch(p -> p.getPersonaId().equals(id));
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getGameHardwareHash() {
        return gameHardwareHash;
    }

    public void setGameHardwareHash(String gameHardwareHash) {
        this.gameHardwareHash = gameHardwareHash;
    }

    public Integer getSelectedPersonaIndex() {
        return selectedPersonaIndex;
    }

    public void setSelectedPersonaIndex(Integer selectedPersonaIndex) {
        this.selectedPersonaIndex = selectedPersonaIndex;
    }
}
