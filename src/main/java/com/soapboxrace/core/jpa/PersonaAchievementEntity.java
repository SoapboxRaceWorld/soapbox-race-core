/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PERSONA_ACHIEVEMENT")
@NamedQueries({
        @NamedQuery(name = "PersonaAchievementEntity.findAllByPersonaId",
                query = "SELECT p FROM PersonaAchievementEntity p WHERE p.personaEntity.personaId = :personaId"),
        @NamedQuery(name = "PersonaAchievementEntity.findByPersonaIdAndAchievementId",
                query = "SELECT p FROM PersonaAchievementEntity p WHERE p.personaEntity.personaId = :personaId AND p.achievementEntity.id = :achievementId"),
})
public class PersonaAchievementEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = PersonaEntity.class, cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "persona_id", referencedColumnName = "ID", nullable = false, foreignKey = @ForeignKey(name = "FK_PERSONA_ACHIEVEMENT_PERSONA_persona_id"))
    private PersonaEntity personaEntity;

    @ManyToOne(targetEntity = AchievementEntity.class, cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "achievement_id", referencedColumnName = "ID", nullable = false, foreignKey = @ForeignKey(name = "FK_PERSONA_ACHIEVEMENT_ACHIEVEMENT_achievement_id"))
    private AchievementEntity achievementEntity;

    @Column(name = "can_progress")
    private boolean canProgress;

    @Column(name = "current_value")
    private Long currentValue;

    @OneToMany(mappedBy = "personaAchievementEntity", targetEntity = PersonaAchievementRankEntity.class, cascade =
            CascadeType.DETACH, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
    private List<PersonaAchievementRankEntity> ranks = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonaEntity getPersonaEntity() {
        return personaEntity;
    }

    public void setPersonaEntity(PersonaEntity personaEntity) {
        this.personaEntity = personaEntity;
    }

    public AchievementEntity getAchievementEntity() {
        return achievementEntity;
    }

    public void setAchievementEntity(AchievementEntity achievementEntity) {
        this.achievementEntity = achievementEntity;
    }

    public boolean isCanProgress() {
        return canProgress;
    }

    public void setCanProgress(boolean canProgress) {
        this.canProgress = canProgress;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    public List<PersonaAchievementRankEntity> getRanks() {
        return ranks;
    }

    public void setRanks(List<PersonaAchievementRankEntity> ranks) {
        this.ranks = ranks;
    }
}
