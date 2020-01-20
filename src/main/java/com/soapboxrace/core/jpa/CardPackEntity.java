/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CARD_PACK")
@NamedQueries({
        @NamedQuery(name = "CardPackEntity.findByEntitlementTag", query = "SELECT obj FROM CardPackEntity obj WHERE " +
                "obj.entitlementTag = :entitlementTag")
})
public class CardPackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column
    private String entitlementTag;

    @OneToMany(mappedBy = "cardPackEntity", targetEntity = CardPackItemEntity.class, orphanRemoval = true)
    private List<CardPackItemEntity> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntitlementTag() {
        return entitlementTag;
    }

    public void setEntitlementTag(String entitlementTag) {
        this.entitlementTag = entitlementTag;
    }

    public List<CardPackItemEntity> getItems() {
        return items;
    }

    public void setItems(List<CardPackItemEntity> items) {
        this.items = items;
    }
}
