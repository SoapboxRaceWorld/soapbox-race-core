package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "PERSONA_ACHIEVEMENT_RANK")
@NamedQueries({
        @NamedQuery(name = "PersonaAchievementRankEntity.findAllForPersonaAchievement",
                query = "SELECT obj FROM PersonaAchievementRankEntity obj WHERE obj.persona.id = :personaId AND obj.achievement.id = :achievementId"),
        @NamedQuery(name = "PersonaAchievementRankEntity.findAllForRank",
                query = "SELECT obj FROM PersonaAchievementRankEntity obj WHERE obj.rank.id = :rankId"),
        @NamedQuery(name = "PersonaAchievementRankEntity.findByPersonaAchievement",
                query = "SELECT obj FROM PersonaAchievementRankEntity obj WHERE obj.persona.id = :personaId AND obj.achievement.id = :achievementId AND obj.rank.id = :rankId"),
        @NamedQuery(name = "PersonaAchievementRankEntity.deleteByPersona", query = "DELETE FROM PersonaAchievementRankEntity obj WHERE obj.persona.id = :personaId")
})
public class PersonaAchievementRankEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "personaId", referencedColumnName = "id")
    @ManyToOne
    private PersonaEntity persona;

    @JoinColumn(name = "achievementId", referencedColumnName = "id")
    @ManyToOne
    private AchievementDefinitionEntity achievement;

    @JoinColumn(name = "rankId", referencedColumnName = "id")
    @ManyToOne
    private AchievementRankEntity rank;

    @Column(name = "state")
    private String state;

    @Column(name = "achievedOn")
    private String achievedOn;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonaEntity getPersona()
    {
        return persona;
    }

    public void setPersona(PersonaEntity persona)
    {
        this.persona = persona;
    }

    public AchievementDefinitionEntity getAchievement()
    {
        return achievement;
    }

    public void setAchievement(AchievementDefinitionEntity achievement)
    {
        this.achievement = achievement;
    }

    public AchievementRankEntity getRank()
    {
        return rank;
    }

    public void setRank(AchievementRankEntity rank)
    {
        this.rank = rank;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getAchievedOn()
    {
        return achievedOn;
    }

    public void setAchievedOn(String achievedOn)
    {
        this.achievedOn = achievedOn;
    }

    @Override
    public String toString()
    {
        return String.format("PARE(personaId=%d,state=%s,achievedOn=%s,achievementId=%s)", persona.getPersonaId(), state, achievedOn, achievement.getId());
    }
}
