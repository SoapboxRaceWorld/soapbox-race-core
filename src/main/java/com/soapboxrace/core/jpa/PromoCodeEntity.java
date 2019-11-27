/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2019.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "PROMO_CODE")
@NamedQueries({@NamedQuery(name = "PromoCodeEntity.findByUser", query = "SELECT obj FROM PromoCodeEntity obj WHERE " +
        "obj.user = :user AND obj.isUsed = false"), //
        @NamedQuery(name = "PromoCodeEntity.findByCode", query = "SELECT obj FROM PromoCodeEntity obj WHERE obj" +
                ".promoCode = :promoCode") //
})
public class PromoCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USERID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_PROMOCODE_USER"))
    private UserEntity user;

    private String promoCode;
    private Boolean isUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

}
