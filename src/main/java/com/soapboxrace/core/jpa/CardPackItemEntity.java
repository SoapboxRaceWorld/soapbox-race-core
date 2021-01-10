/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "CARD_PACK_ITEM")
public class CardPackItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(targetEntity = CardPackEntity.class, optional = false)
    @JoinColumn(name = "cardPackEntity_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_CARD_PACK_ITEM_CARD_PACK_cardPackEntity_ID"))
    private CardPackEntity cardPackEntity;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String script;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardPackEntity getCardPackEntity() {
        return cardPackEntity;
    }

    public void setCardPackEntity(CardPackEntity cardPackEntity) {
        this.cardPackEntity = cardPackEntity;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
