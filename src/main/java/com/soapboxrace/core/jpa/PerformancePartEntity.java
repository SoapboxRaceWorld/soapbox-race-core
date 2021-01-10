/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "PERFORMANCEPART")
@NamedQueries({@NamedQuery(name = "PerformancePartEntity.deleteByCar", //
        query = "DELETE FROM PerformancePartEntity obj WHERE obj.car = :car") //
})
public class PerformancePartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int performancePartAttribHash;

    @ManyToOne
    @JoinColumn(name = "carId", referencedColumnName = "ID", foreignKey = @ForeignKey(name =
            "FK_PERFORMANCEPART_CAR_carId"))
    private CarEntity car;

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

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }

}
