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
public class OwnedCarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "carSlotId", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_OWNEDCAR_CARSLOT"))
    private CarSlotEntity carSlot;

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

    public CarSlotEntity getCarSlot() {
        return carSlot;
    }

    public void setCarSlot(CarSlotEntity carSlot) {
        this.carSlot = carSlot;
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
