/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "AMPLIFIERS")
@NamedQueries({
        @NamedQuery(name = "AmplifierEntity.findAmplifierByHash", query = "SELECT obj FROM AmplifierEntity obj WHERE " +
                "obj.productEntity.hash = :hash"),
})
public class AmplifierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = ProductEntity.class, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "productId", foreignKey = @ForeignKey(name = "FK_AMPLIFIERS_PRODUCT_product_id"))
    private ProductEntity productEntity;

    @Column
    private String ampType;

    @Column
    private Float cashMultiplier;

    @Column
    private Float repMultiplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public String getAmpType() {
        return ampType;
    }

    public void setAmpType(String ampType) {
        this.ampType = ampType;
    }

    public Float getCashMultiplier() {
        return cashMultiplier;
    }

    public void setCashMultiplier(Float cashMultiplier) {
        this.cashMultiplier = cashMultiplier;
    }

    public Float getRepMultiplier() {
        return repMultiplier;
    }

    public void setRepMultiplier(Float repMultiplier) {
        this.repMultiplier = repMultiplier;
    }
}
