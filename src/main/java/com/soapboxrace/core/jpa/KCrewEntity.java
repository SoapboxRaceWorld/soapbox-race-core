/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2021.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "K_CREW")
public class KCrewEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int crewId;

    private String tag;
    private String name;
    private String motto;
    private String color;

    @Column(name = "cover_version")
    private Long coverVersion;

    @Column(name = "thumb_version")
    private Long thumbVersion;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    private boolean open;

    @Column(name = "nb_member")
    private Long nbMember;

    @Column(name = "nb_points")
    private Long nbPoints;

    private Long level;
    private Long ownerUserId;
    private Long maxPoints;
    private String invite;
    private int dropSB;
    private String discord;

    public String getTag() { 
        return tag; 
    }

    public void setTag(String tag) { 
        this.tag = tag; 
    }
}
