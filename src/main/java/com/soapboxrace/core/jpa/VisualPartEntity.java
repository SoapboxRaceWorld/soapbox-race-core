/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "VISUALPART")
@NamedQueries({@NamedQuery(name = "VisualPartEntity.deleteByCustomCar", //
        query = "DELETE FROM VisualPartEntity obj WHERE obj.customCar = :customCar") //
})
public class VisualPartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int partHash;
    private int slotHash;

    @ManyToOne
    @JoinColumn(name = "customCarId", referencedColumnName = "ID", foreignKey = @ForeignKey(name =
            "FK_VISUALPART_CUSTOMCAR"))
    private CustomCarEntity customCar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPartHash() {
        return partHash;
    }

    public void setPartHash(int partHash) {
        this.partHash = partHash;
    }

    public int getSlotHash() {
        return slotHash;
    }

    public void setSlotHash(int slotHash) {
        this.slotHash = slotHash;
    }

    public CustomCarEntity getCustomCar() {
        return customCar;
    }

    public void setCustomCar(CustomCarEntity customCar) {
        this.customCar = customCar;
    }

}
