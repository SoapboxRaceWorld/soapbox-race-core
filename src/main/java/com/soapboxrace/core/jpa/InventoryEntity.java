/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "INVENTORY")
@NamedQueries({
        @NamedQuery(name = "InventoryEntity.findByPersonaId", query = "SELECT obj FROM InventoryEntity obj WHERE obj.personaEntity.personaId = :personaId")
})
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = PersonaEntity.class)
    @JoinColumn(name = "personaId", foreignKey = @ForeignKey(name = "FK_INVENTORY_PERSONA_personaId"))
    private PersonaEntity personaEntity;

    @OneToMany(mappedBy = "inventoryEntity", fetch =
            FetchType.EAGER)
    private List<InventoryItemEntity> inventoryItems = new ArrayList<>();

    @Column
    private int performancePartsCapacity;

    @Column
    private int performancePartsUsedSlotCount;

    @Column
    private int skillModPartsCapacity;

    @Column
    private int skillModPartsUsedSlotCount;

    @Column
    private int visualPartsCapacity;

    @Column
    private int visualPartsUsedSlotCount;

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

    public List<InventoryItemEntity> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItemEntity> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public int getPerformancePartsCapacity() {
        return performancePartsCapacity;
    }

    public void setPerformancePartsCapacity(int performancePartsCapacity) {
        this.performancePartsCapacity = performancePartsCapacity;
    }

    public int getPerformancePartsUsedSlotCount() {
        return performancePartsUsedSlotCount;
    }

    public void setPerformancePartsUsedSlotCount(int performancePartsUsedSlotCount) {
        this.performancePartsUsedSlotCount = performancePartsUsedSlotCount;
    }

    public int getSkillModPartsCapacity() {
        return skillModPartsCapacity;
    }

    public void setSkillModPartsCapacity(int skillModPartsCapacity) {
        this.skillModPartsCapacity = skillModPartsCapacity;
    }

    public int getSkillModPartsUsedSlotCount() {
        return skillModPartsUsedSlotCount;
    }

    public void setSkillModPartsUsedSlotCount(int skillModPartsUsedSlotCount) {
        this.skillModPartsUsedSlotCount = skillModPartsUsedSlotCount;
    }

    public int getVisualPartsCapacity() {
        return visualPartsCapacity;
    }

    public void setVisualPartsCapacity(int visualPartsCapacity) {
        this.visualPartsCapacity = visualPartsCapacity;
    }

    public int getVisualPartsUsedSlotCount() {
        return visualPartsUsedSlotCount;
    }

    public void setVisualPartsUsedSlotCount(int visualPartsUsedSlotCount) {
        this.visualPartsUsedSlotCount = visualPartsUsedSlotCount;
    }
}
