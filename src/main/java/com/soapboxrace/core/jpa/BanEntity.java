/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import com.soapboxrace.core.jpa.convert.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BAN")
@NamedQueries({ //
        @NamedQuery(name = "BanEntity.findAll", query = "SELECT obj FROM BanEntity obj")})
public class BanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_BAN_USER"))
    private UserEntity userEntity;

    @ManyToOne(targetEntity = PersonaEntity.class)
    @JoinColumn(name = "banned_by_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_BANNED_BY"))
    private PersonaEntity bannedBy;

    @Column
    private String reason;

    @Column
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime started;

    @Column
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime endsAt;

    @Column
    private boolean willEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public PersonaEntity getBannedBy() {
        return bannedBy;
    }

    public void setBannedBy(PersonaEntity bannedBy) {
        this.bannedBy = bannedBy;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isWillEnd() {
        return willEnd;
    }

    public void setWillEnd(boolean willEnd) {
        this.willEnd = willEnd;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }
}
