/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "BADGE_DEFINITION")
@NamedQueries({
        @NamedQuery(name = "BadgeDefinitionEntity.findAll", query = "SELECT b FROM BadgeDefinitionEntity b")
})
public class BadgeDefinitionEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String background;

    @Column
    private String border;

    @Column
    private String description;

    @Column
    private String icon;

    @Column
    private String name;

    @OneToOne(targetEntity = AchievementEntity.class, mappedBy = "badgeDefinitionEntity")
    private AchievementEntity achievementEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AchievementEntity getAchievementEntity() {
        return achievementEntity;
    }

    public void setAchievementEntity(AchievementEntity achievementEntity) {
        this.achievementEntity = achievementEntity;
    }
}
