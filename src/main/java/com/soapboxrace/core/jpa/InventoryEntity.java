package com.soapboxrace.core.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONAINVENTORY")
@NamedQueries({ //
        @NamedQuery(name = "InventoryEntity.findByPersona", query = "SELECT obj FROM InventoryEntity obj WHERE obj.persona = :persona"), //
        @NamedQuery(name = "InventoryEntity.deleteByPersona", query = "DELETE FROM InventoryEntity obj WHERE obj.persona.id = :personaId") //
})
public class InventoryEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToOne
    @JoinColumn(name = "personaId")
    private PersonaEntity persona;

    private int performancePartsCapacity;

    private int performancePartsUsedSlotCount;

    private int skillModPartsCapacity;

    private int skillModPartsUsedSlotCount;

    private int visualPartsCapacity;

    private int visualPartsUsedSlotCount;

    @OneToMany(mappedBy = "inventory", targetEntity = InventoryItemEntity.class)
    private List<InventoryItemEntity> items = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PersonaEntity persona) {
        this.persona = persona;
    }

    public List<InventoryItemEntity> getItems() {
        return items;
    }

    public void setItems(List<InventoryItemEntity> items) {
        this.items = items;
    }

    public int getPerformancePartsCapacity()
    {
        return performancePartsCapacity;
    }

    public void setPerformancePartsCapacity(int performancePartsCapacity)
    {
        this.performancePartsCapacity = performancePartsCapacity;
    }

    public int getSkillModPartsCapacity()
    {
        return skillModPartsCapacity;
    }

    public void setSkillModPartsCapacity(int skillModPartsCapacity)
    {
        this.skillModPartsCapacity = skillModPartsCapacity;
    }

    public int getVisualPartsCapacity()
    {
        return visualPartsCapacity;
    }

    public void setVisualPartsCapacity(int visualPartsCapacity)
    {
        this.visualPartsCapacity = visualPartsCapacity;
    }

    public int getPerformancePartsUsedSlotCount()
    {
        return performancePartsUsedSlotCount;
    }

    public void setPerformancePartsUsedSlotCount(int performancePartsUsedSlotCount)
    {
        this.performancePartsUsedSlotCount = performancePartsUsedSlotCount;
    }

    public int getSkillModPartsUsedSlotCount()
    {
        return skillModPartsUsedSlotCount;
    }

    public void setSkillModPartsUsedSlotCount(int skillModPartsUsedSlotCount)
    {
        this.skillModPartsUsedSlotCount = skillModPartsUsedSlotCount;
    }

    public int getVisualPartsUsedSlotCount()
    {
        return visualPartsUsedSlotCount;
    }

    public void setVisualPartsUsedSlotCount(int visualPartsUsedSlotCount)
    {
        this.visualPartsUsedSlotCount = visualPartsUsedSlotCount;
    }
}
