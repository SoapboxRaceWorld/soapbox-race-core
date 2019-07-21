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
