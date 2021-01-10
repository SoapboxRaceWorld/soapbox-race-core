/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import com.soapboxrace.core.jpa.convert.NewsArticleFilterConverter;
import com.soapboxrace.core.jpa.convert.NewsArticleTypeConverter;
import com.soapboxrace.core.jpa.util.NewsArticleFilters;
import com.soapboxrace.core.jpa.util.NewsArticleType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NEWS_ARTICLE")
@NamedQueries({
        @NamedQuery(name = "NewsArticleEntity.findAllByPersona",
                query = "SELECT obj FROM NewsArticleEntity obj WHERE obj.persona.personaId = :id OR obj.persona IS NULL"),
        @NamedQuery(name = "NewsArticleEntity.deleteAllByPersona",
                query = "DELETE FROM NewsArticleEntity obj WHERE obj.persona.personaId = :id"),
        @NamedQuery(name = "NewsArticleEntity.findAllByReferencedPersona",
                query = "SELECT obj FROM NewsArticleEntity obj WHERE obj.referencedPersona IS NOT NULL AND obj" +
                        ".referencedPersona.personaId = :id"),
        @NamedQuery(name = "NewsArticleEntity.deleteAllByReferencedPersona",
                query = "DELETE FROM NewsArticleEntity obj WHERE obj.referencedPersona IS NOT NULL AND obj" +
                        ".referencedPersona.personaId = :id"),
})
public class NewsArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String parameters;

    @Column
    @Convert(converter = NewsArticleFilterConverter.class)
    private NewsArticleFilters filters;

    @Column
    private int iconType;

    @ManyToOne(targetEntity = PersonaEntity.class)
    @JoinColumn(name = "persona_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_NEWS_ARTICLE_PERSONA_persona_id"))
    private PersonaEntity persona;

    @ManyToOne(targetEntity = PersonaEntity.class)
    @JoinColumn(name = "referenced_persona_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_NEWS_ARTICLE_PERSONA_persona_id"))
    private PersonaEntity referencedPersona;

    @Column
    private int sticky;

    @Column
    private LocalDateTime timestamp;

    @Column
    @Convert(converter = NewsArticleTypeConverter.class)
    private NewsArticleType type;

    @Column
    private String shortHALId;

    @Column
    private String longHALId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public NewsArticleFilters getFilters() {
        return filters;
    }

    public void setFilters(NewsArticleFilters filters) {
        this.filters = filters;
    }

    public int getIconType() {
        return iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    public PersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PersonaEntity persona) {
        this.persona = persona;
    }

    public PersonaEntity getReferencedPersona() {
        return referencedPersona;
    }

    public void setReferencedPersona(PersonaEntity referencedPersona) {
        this.referencedPersona = referencedPersona;
    }

    public int getSticky() {
        return sticky;
    }

    public void setSticky(int sticky) {
        this.sticky = sticky;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public NewsArticleType getType() {
        return type;
    }

    public void setType(NewsArticleType type) {
        this.type = type;
    }

    public String getLongHALId() {
        return longHALId;
    }

    public void setLongHALId(String longHALId) {
        this.longHALId = longHALId;
    }

    public String getShortHALId() {
        return shortHALId;
    }

    public void setShortHALId(String shortHALId) {
        this.shortHALId = shortHALId;
    }
}