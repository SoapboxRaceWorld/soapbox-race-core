package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "USED_POWERUP", indexes = {
        @Index(name = "session_index", columnList = "eventSessionId"),
        @Index(name = "session_persona_index", columnList = "eventSessionId, personaId"),
        @Index(name = "persona_index", columnList = "personaId"),
        @Index(name = "hash_index", columnList = "powerupHash"),
})
public class UsedPowerupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = PersonaEntity.class, optional = false)
    @JoinColumn(name = "personaId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_USED_POWERUP_PERSONA_personaId"))
    private PersonaEntity personaEntity;

    @ManyToOne(targetEntity = EventSessionEntity.class)
    @JoinColumn(name = "eventSessionId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USED_POWERUP_EVENT_SESSION_eventSessionId"))
    private EventSessionEntity eventSessionEntity;

    @Column
    private Integer powerupHash;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventSessionEntity getEventSessionEntity() {
        return eventSessionEntity;
    }

    public void setEventSessionEntity(EventSessionEntity eventDataEntity) {
        this.eventSessionEntity = eventDataEntity;
    }

    public Integer getPowerupHash() {
        return powerupHash;
    }

    public void setPowerupHash(Integer powerupHash) {
        this.powerupHash = powerupHash;
    }

    public PersonaEntity getPersonaEntity() {
        return personaEntity;
    }

    public void setPersonaEntity(PersonaEntity personaEntity) {
        this.personaEntity = personaEntity;
    }
}
