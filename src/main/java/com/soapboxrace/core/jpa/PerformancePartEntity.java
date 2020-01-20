/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "PERFORMANCEPART")
@NamedQueries({@NamedQuery(name = "PerformancePartEntity.deleteByCustomCar", //
        query = "DELETE FROM PerformancePartEntity obj WHERE obj.customCar = :customCar") //
})
public class PerformancePartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int performancePartAttribHash;

    @ManyToOne
    @JoinColumn(name = "customCarId", referencedColumnName = "ID", foreignKey = @ForeignKey(name =
            "FK_PERFPART_CUSTOMCAR"))
    private CustomCarEntity customCar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPerformancePartAttribHash() {
        return performancePartAttribHash;
    }

    public void setPerformancePartAttribHash(int performancePartAttribHash) {
        this.performancePartAttribHash = performancePartAttribHash;
    }

    public CustomCarEntity getCustomCar() {
        return customCar;
    }

    public void setCustomCar(CustomCarEntity customCar) {
        this.customCar = customCar;
    }

}
