/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "LEVEL_REP")
@NamedQueries({
        @NamedQuery(name = "LevelRepEntity.findAll", query = "SELECT obj FROM LevelRepEntity obj"),
        @NamedQuery(name = "LevelRepEntity.findMaxLevel", query = "SELECT MAX(obj.level) FROM LevelRepEntity obj"), //
})
public class LevelRepEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long level;
    private Long expPoint;

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getExpPoint() {
        return expPoint;
    }

    public void setExpPoint(Long expPoint) {
        this.expPoint = expPoint;
    }

}