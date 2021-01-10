/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "PAINT")
@NamedQueries({@NamedQuery(name = "PaintEntity.deleteByCar", //
        query = "DELETE FROM PaintEntity obj WHERE obj.car = :car") //
})
public class PaintEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carId", referencedColumnName = "ID", foreignKey = @ForeignKey(name =
            "FK_PAINT_CAR_carId"))
    private CarEntity car;

    @Column(name = "paintGroup")
    private int group;
    private int hue;
    private int sat;
    private int slot;
    @Column(name = "paintVar")
    private int var;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getVar() {
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }

}
