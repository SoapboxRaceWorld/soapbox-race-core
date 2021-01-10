/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PERSONA_ACHIEVEMENT_RANK")
@NamedQueries({
        @NamedQuery(name = "PersonaAchievementRankEntity.findAllByPersonaId", query = "SELECT p FROM " +
                "PersonaAchievementRankEntity p WHERE p.personaAchievementEntity.personaEntity.personaId = :personaId"),
        @NamedQuery(name = "PersonaAchievementRankEntity.findByPersonaIdAndAchievementRankId", query = "SELECT r FROM" +
                " PersonaAchievementRankEntity r WHERE r.achievementRankEntity.id = :achievementRankId AND r" +
                ".personaAchievementEntity.personaEntity.personaId = :personaId"),
        @NamedQuery(name = "PersonaAchievementRankEntity.countPersonasWithRank", query = "SELECT count(r) FROM " +
                "PersonaAchievementRankEntity r WHERE r.achievementRankEntity.id = :achievementRankId AND r" +
                ".achievedOn IS NOT NULL"),
        @NamedQuery(name = "PersonaAchievementRankEntity.findHighestCompletedRankOfAchievementByPersona",
                query = "SELECT obj FROM PersonaAchievementRankEntity obj " +
                        "WHERE obj.personaAchievementEntity.personaEntity.personaId = :personaId " +
                        "AND obj.personaAchievementEntity.achievementEntity.id = :achievementId " +
                        "AND (obj.state = 'Completed' OR obj.state = 'RewardWaiting') " +
                        "ORDER BY obj.achievementRankEntity.rank DESC")
})
public class PersonaAchievementRankEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = PersonaAchievementEntity.class, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "persona_achievement_id", referencedColumnName = "ID", nullable = false, foreignKey = @ForeignKey(name = "FK_PERSONA_ACHIEVEMENT_RANK_PERSONA_ACHIEVEMENT_id"))
    private PersonaAchievementEntity personaAchievementEntity;

    @ManyToOne(targetEntity = AchievementRankEntity.class, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "achievement_rank_id", referencedColumnName = "ID", nullable = false, foreignKey = @ForeignKey(name = "FK_PERSONA_ACHIEVEMENT_RANK_ACHIEVEMENT_RANK_id"))
    private AchievementRankEntity achievementRankEntity;

    @Column(columnDefinition = "ENUM('Locked', 'InProgress', 'Completed', 'RewardWaiting')")
    private String state;

    @Column(name = "achieved_on")
    private LocalDateTime achievedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonaAchievementEntity getPersonaAchievementEntity() {
        return personaAchievementEntity;
    }

    public void setPersonaAchievementEntity(PersonaAchievementEntity personaAchievementEntity) {
        this.personaAchievementEntity = personaAchievementEntity;
    }

    public AchievementRankEntity getAchievementRankEntity() {
        return achievementRankEntity;
    }

    public void setAchievementRankEntity(AchievementRankEntity achievementRankEntity) {
        this.achievementRankEntity = achievementRankEntity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getAchievedOn() {
        return achievedOn;
    }

    public void setAchievedOn(LocalDateTime achievedOn) {
        this.achievedOn = achievedOn;
    }
}
