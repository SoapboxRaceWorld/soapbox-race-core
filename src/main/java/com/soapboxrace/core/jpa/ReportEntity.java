/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "REPORT")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long personaId;
    private Long abuserPersonaId;
    private String description;
    private Integer petitionType;
    private Integer customCarID;
    private Integer chatMinutes;
    private Long hacksdetected;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public Long getAbuserPersonaId() {
        return abuserPersonaId;
    }

    public void setAbuserPersonaId(Long abuserPersonaId) {
        this.abuserPersonaId = abuserPersonaId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPetitionType() {
        return petitionType;
    }

    public void setPetitionType(Integer petitionType) {
        this.petitionType = petitionType;
    }

    public Integer getCustomCarID() {
        return customCarID;
    }

    public void setCustomCarID(Integer customCarID) {
        this.customCarID = customCarID;
    }

    public Integer getChatMinutes() {
        return chatMinutes;
    }

    public void setChatMinutes(Integer chatMinutes) {
        this.chatMinutes = chatMinutes;
    }

    public Long getHacksDetected() {
        return hacksdetected;
    }

    public void setHacksDetected(Long hacksdetected) {
        this.hacksdetected = hacksdetected;
    }

}