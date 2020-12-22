/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OWNEDCAR")
@NamedQueries({
        @NamedQuery(name = "OwnedCarEntity.findByPersonaId", //
                query = "SELECT obj FROM OwnedCarEntity obj WHERE obj.persona.id = :persona ORDER BY obj.id"),
        @NamedQuery(name = "OwnedCarEntity.findByPersonaIdEager", //
                query = "SELECT obj FROM OwnedCarEntity obj " +
                        "       INNER JOIN FETCH obj.customCar cc " +
                        "WHERE obj.persona.id = :persona ORDER BY obj.id"), //
        @NamedQuery(name = "OwnedCarEntity.findNumNonRentalsByPersonaId", //
                query = "SELECT COUNT(obj) FROM OwnedCarEntity obj  WHERE obj.persona.id = :persona" +
                        " AND obj.expirationDate IS NULL"), //
        @NamedQuery(name = "OwnedCarEntity.findNumByPersonaId",
                query = "SELECT COUNT(obj) FROM OwnedCarEntity obj WHERE obj.persona.id = :persona"),
        @NamedQuery(name = "OwnedCarEntity.deleteByPersona", //
                query = "DELETE FROM OwnedCarEntity obj WHERE obj.persona = :persona"), //
        @NamedQuery(name = "OwnedCarEntity.deleteAllExpired", //
                query = "DELETE FROM OwnedCarEntity obj  WHERE obj.expirationDate <= CURRENT_TIMESTAMP")
})
public class OwnedCarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = PersonaEntity.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personaId", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_OWNEDCAR_PERSONA_personaId"))
    private PersonaEntity persona;

    private int durability;
    private float heat;
    private LocalDateTime expirationDate;
    private String ownershipType = "CustomizedCar";

    @OneToOne(mappedBy = "ownedCar", targetEntity = CustomCarEntity.class, fetch = FetchType.LAZY, cascade =
            CascadeType.ALL)
    private CustomCarEntity customCar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PersonaEntity personaEntity) {
        this.persona = personaEntity;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public float getHeat() {
        return heat;
    }

    public void setHeat(float heat) {
        this.heat = heat;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public CustomCarEntity getCustomCar() {
        return customCar;
    }

    public void setCustomCar(CustomCarEntity customCar) {
        this.customCar = customCar;
    }

}
