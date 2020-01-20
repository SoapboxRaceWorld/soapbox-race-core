/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "HARDWARE_INFO")
@NamedQueries({ //
        @NamedQuery(name = "HardwareInfoEntity.findByHardwareHash", query = "SELECT obj FROM HardwareInfoEntity obj " +
                "WHERE obj.hardwareHash = :hardwareHash"), //
        @NamedQuery(name = "HardwareInfoEntity.findByUserId", query = "SELECT obj FROM HardwareInfoEntity obj WHERE " +
                "obj.userId = :userId") //
})
public class HardwareInfoEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length = 65535)
    private String hardwareInfo;

    private String hardwareHash;

    private Long userId;

    private boolean banned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHardwareInfo() {
        return hardwareInfo;
    }

    public void setHardwareInfo(String hardwareInfo) {
        this.hardwareInfo = hardwareInfo;
    }

    public String getHardwareHash() {
        return hardwareHash;
    }

    public void setHardwareHash(String hardwareHash) {
        this.hardwareHash = hardwareHash;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

}
