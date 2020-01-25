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
import java.util.Set;

@Entity
@Table(name = "CUSTOMCAR")
public class CustomCarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int baseCar;
    private int carClassHash;
    private boolean isPreset;
    private int level;
    private String name;
    private int physicsProfileHash;
    private int rating;
    private float resalePrice;
    private float rideHeightDrop;
    private int skillModSlotCount;
    private int version;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownedCarId", referencedColumnName = "ID", foreignKey = @ForeignKey(name =
            "FK_CUSTOMCAR_OWNEDCAR"))
    private OwnedCarEntity ownedCar;

    @OneToMany(mappedBy = "customCar", targetEntity = PaintEntity.class,
            orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.JOIN)
    private Set<PaintEntity> paints;

    @OneToMany(mappedBy = "customCar", targetEntity = PerformancePartEntity.class,
            orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.JOIN)
    private Set<PerformancePartEntity> performanceParts;

    @OneToMany(mappedBy = "customCar", targetEntity = SkillModPartEntity.class,
            orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.JOIN)
    private Set<SkillModPartEntity> skillModParts;

    @OneToMany(mappedBy = "customCar", targetEntity = VinylEntity.class,
            orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.JOIN)
    private Set<VinylEntity> vinyls;

    @OneToMany(mappedBy = "customCar", targetEntity = VisualPartEntity.class,
            orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.JOIN)
    private Set<VisualPartEntity> visualParts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBaseCar() {
        return baseCar;
    }

    public void setBaseCar(int baseCar) {
        this.baseCar = baseCar;
    }

    public int getCarClassHash() {
        return carClassHash;
    }

    public void setCarClassHash(int carClassHash) {
        this.carClassHash = carClassHash;
    }

    public boolean isPreset() {
        return isPreset;
    }

    public void setPreset(boolean isPreset) {
        this.isPreset = isPreset;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhysicsProfileHash() {
        return physicsProfileHash;
    }

    public void setPhysicsProfileHash(int physicsProfileHash) {
        this.physicsProfileHash = physicsProfileHash;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public float getResalePrice() {
        return resalePrice;
    }

    public void setResalePrice(float resalePrice) {
        this.resalePrice = resalePrice;
    }

    public float getRideHeightDrop() {
        return rideHeightDrop;
    }

    public void setRideHeightDrop(float rideHeightDrop) {
        this.rideHeightDrop = rideHeightDrop;
    }

    public int getSkillModSlotCount() {
        return skillModSlotCount;
    }

    public void setSkillModSlotCount(int skillModSlotCount) {
        this.skillModSlotCount = skillModSlotCount;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<PerformancePartEntity> getPerformanceParts() {
        return performanceParts;
    }

    public void setPerformanceParts(Set<PerformancePartEntity> performanceParts) {
        this.performanceParts = performanceParts;
    }

    public Set<SkillModPartEntity> getSkillModParts() {
        return skillModParts;
    }

    public void setSkillModParts(Set<SkillModPartEntity> skillModParts) {
        this.skillModParts = skillModParts;
    }

    public Set<VisualPartEntity> getVisualParts() {
        return visualParts;
    }

    public void setVisualParts(Set<VisualPartEntity> visualParts) {
        this.visualParts = visualParts;
    }

    public Set<PaintEntity> getPaints() {
        return paints;
    }

    public void setPaints(Set<PaintEntity> paints) {
        this.paints = paints;
    }

    public Set<VinylEntity> getVinyls() {
        return vinyls;
    }

    public void setVinyls(Set<VinylEntity> vinyls) {
        this.vinyls = vinyls;
    }

    public OwnedCarEntity getOwnedCar() {
        return ownedCar;
    }

    public void setOwnedCar(OwnedCarEntity ownedCar) {
        this.ownedCar = ownedCar;
    }

}
