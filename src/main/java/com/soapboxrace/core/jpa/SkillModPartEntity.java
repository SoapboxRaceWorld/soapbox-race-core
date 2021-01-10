/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "SKILLMODPART")
@NamedQueries({@NamedQuery(name = "SkillModPartEntity.deleteByCar", //
        query = "DELETE FROM SkillModPartEntity obj WHERE obj.car = :car") //
})
public class SkillModPartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isFixed;
    private int skillModPartAttribHash;

    @ManyToOne
    @JoinColumn(name = "carId", referencedColumnName = "ID", foreignKey = @ForeignKey(name =
            "FK_SKILLMODPART_CAR_carId"))
    private CarEntity car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    public int getSkillModPartAttribHash() {
        return skillModPartAttribHash;
    }

    public void setSkillModPartAttribHash(int skillModPartAttribHash) {
        this.skillModPartAttribHash = skillModPartAttribHash;
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity customCar) {
        this.car = customCar;
    }

}
