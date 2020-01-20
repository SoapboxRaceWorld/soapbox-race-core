/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "REWARD_TABLE")
@NamedQueries({
        @NamedQuery(name = "RewardTableEntity.findAll", query = "SELECT obj FROM RewardTableEntity obj"),
        @NamedQuery(name = "RewardTableEntity.findByName", query = "SELECT obj FROM RewardTableEntity obj WHERE obj" +
                ".name = :name")
})
public class RewardTableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "rewardTableEntity", targetEntity = RewardTableItemEntity.class, orphanRemoval = true)
    private List<RewardTableItemEntity> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RewardTableItemEntity> getItems() {
        return items;
    }

    public void setItems(List<RewardTableItemEntity> items) {
        this.items = items;
    }
}
