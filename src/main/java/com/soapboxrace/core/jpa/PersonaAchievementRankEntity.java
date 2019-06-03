package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PERSONA_ACHIEVEMENT_RANK")
@NamedQueries({
        @NamedQuery(name = "PersonaAchievementRankEntity.findByPersonaIdAndAchievementRankId", query = "SELECT r FROM PersonaAchievementRankEntity r WHERE r.achievementRankEntity.id = :achievementRankId AND r.personaAchievementEntity.personaEntity.personaId = :personaId"),
        @NamedQuery(name = "PersonaAchievementRankEntity.countPersonasWithRank", query = "SELECT count(*) FROM PersonaAchievementRankEntity r WHERE r.achievementRankEntity.id = :achievementRankId AND r.achievedOn IS NOT NULL")
})
public class PersonaAchievementRankEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = PersonaAchievementEntity.class, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "persona_achievement_id", referencedColumnName = "ID", nullable = false)
    private PersonaAchievementEntity personaAchievementEntity;

    @ManyToOne(targetEntity = AchievementRankEntity.class, optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "achievement_rank_id", referencedColumnName = "ID", nullable = false)
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
