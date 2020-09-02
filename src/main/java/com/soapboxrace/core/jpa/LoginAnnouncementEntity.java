/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "LOGIN_ANNOUNCEMENT")
@NamedQueries({ //
        @NamedQuery(name = "LoginAnnouncementEntity.findAll", query = "SELECT obj FROM LoginAnnouncementEntity obj"), //
        @NamedQuery(name = "LoginAnnouncementEntity.findAllByLanguage",
                query = "SELECT obj FROM LoginAnnouncementEntity obj WHERE obj.language IS NULL OR obj.language = :language") //
})
public class LoginAnnouncementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imageUrl;
    private String type;
    private String target;
    private String context;
    private String language;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}