package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "PERSONA_ACHIEVEMENT")
@NamedQueries({
        @NamedQuery(name = "PersonaAchievementEntity.findAllForPersona", 
                query = "SELECT obj FROM PersonaAchievementEntity obj WHERE obj.persona.id = :id"),
        @NamedQuery(name = "PersonaAchievementEntity.findAllForAchievement", 
                query = "SELECT obj FROM PersonaAchievementEntity obj WHERE obj.achievement.id = :id"),
        @NamedQuery(name = "PersonaAchievementEntity.getForPersonaAchievement",
                query = "SELECT obj FROM PersonaAchievementEntity obj WHERE obj.persona.id = :personaId AND obj.achievement.id = :achId"), 
        @NamedQuery(name = "PersonaAchievementEntity.deleteByPersona", query = "DELETE FROM PersonaAchievementEntity obj WHERE obj.persona.id = :personaId")
})
public class PersonaAchievementEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "personaId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonaEntity persona;

    @JoinColumn(name = "achievementId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private AchievementDefinitionEntity achievement;

    @Column(name = "currentValue")
    private Long currentValue;

    @Column(name = "canProgress")
    private boolean canProgress;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
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

    public Long getCurrentValue()
    {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue)
    {
        this.currentValue = currentValue;
    }

    public boolean isCanProgress()
    {
        return canProgress;
    }

    public void setCanProgress(boolean canProgress)
    {
        this.canProgress = canProgress;
    }
}
