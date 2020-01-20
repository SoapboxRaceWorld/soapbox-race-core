/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "SKILLMODPART")
@NamedQueries({@NamedQuery(name = "SkillModPartEntity.deleteByCustomCar", //
        query = "DELETE FROM SkillModPartEntity obj WHERE obj.customCar = :customCar") //
})
public class SkillModPartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isFixed;
    private int skillModPartAttribHash;

    @ManyToOne
    @JoinColumn(name = "customCarId", referencedColumnName = "ID", foreignKey = @ForeignKey(name =
            "FK_SKILLPART_CUSTOMCAR"))
    private CustomCarEntity customCar;

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

    public CustomCarEntity getCustomCar() {
        return customCar;
    }

    public void setCustomCar(CustomCarEntity customCar) {
        this.customCar = customCar;
    }

}
