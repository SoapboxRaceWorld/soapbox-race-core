/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BAN")
@NamedQueries({
        @NamedQuery(name = "BanEntity.findAllExpired",
                query = "SELECT obj FROM BanEntity obj WHERE obj.endsAt IS NOT NULL AND obj.endsAt <= CURRENT_TIMESTAMP AND obj.active = true"),
        @NamedQuery(name = "BanEntity.findByUser",
                query = "SELECT obj FROM BanEntity obj WHERE obj.userEntity = :user AND (obj.endsAt IS NULL OR obj.endsAt > CURRENT_TIMESTAMP) AND obj.active = true")
})
public class BanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_BAN_USER_user_id"))
    private UserEntity userEntity;

    @ManyToOne(targetEntity = PersonaEntity.class)
    @JoinColumn(name = "banned_by_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_BAN_PERSONA_banned_by_id"))
    private PersonaEntity bannedBy;

    @Column
    private String reason;

    @Column
    private LocalDateTime started;

    @Column(name = "ends_at")
    private LocalDateTime endsAt;

    @Column
    private Boolean active;

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

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
