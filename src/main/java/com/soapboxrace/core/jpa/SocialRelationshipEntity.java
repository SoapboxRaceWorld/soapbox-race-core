/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "SOCIAL_RELATIONSHIP")
@NamedQueries({ //
        @NamedQuery(name = "SocialRelationshipEntity.findByUser", query = "SELECT obj FROM SocialRelationshipEntity " +
                "obj WHERE obj.user.id = :id"), //
        @NamedQuery(name = "SocialRelationshipEntity.deleteAllByPersonaId", query = "DELETE FROM " +
                "SocialRelationshipEntity obj WHERE obj.remotePersonaId = :personaId"),
        @NamedQuery(name = "SocialRelationshipEntity.findByUserAndStatus", query = "SELECT obj FROM " +
                "SocialRelationshipEntity obj WHERE obj.user.id = :id AND obj.status=:status"), //
        @NamedQuery(name = "SocialRelationshipEntity.findByRemoteUserAndStatus", query = "SELECT obj FROM " +
                "SocialRelationshipEntity obj WHERE obj.remoteUser.id = :remoteId AND obj.status=:status"), //
        @NamedQuery(name = "SocialRelationshipEntity.findByLocalAndRemoteUser", query = "SELECT obj FROM " +
                "SocialRelationshipEntity" +
                " obj" +
                " WHERE " +
                "obj.user.id = :localId AND obj.remoteUser.id = :remoteId"), //
})
public class SocialRelationshipEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "fromUserId")
    private UserEntity remoteUser;

    @Column
    private Long remotePersonaId;

    @Column
    private Long status; // 0: pending friend request; 1: accepted friend; 2: blocked user, 3: pending friend request
    // (not accepted by target)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemotePersonaId() {
        return remotePersonaId;
    }

    public void setRemotePersonaId(Long personaId) {
        this.remotePersonaId = personaId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserEntity getRemoteUser() {
        return remoteUser;
    }

    public void setRemoteUser(UserEntity fromUser) {
        this.remoteUser = fromUser;
    }
}