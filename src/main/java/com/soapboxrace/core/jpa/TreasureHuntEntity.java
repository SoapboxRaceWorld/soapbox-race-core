/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TREASURE_HUNT")
@NamedQueries({ //
        @NamedQuery(name = "TreasureHuntEntity.deleteByPersona", query = "DELETE FROM TreasureHuntEntity obj WHERE " +
                "obj.personaId = :personaId") //
})
public class TreasureHuntEntity {

    @Id
    private Long personaId;
    private Integer coinsCollected;
    private Boolean isStreakBroken;
    @Column(nullable = false)
    private Boolean isCompleted = false;
    private Integer numCoins;
    private Integer seed;
    private Integer streak;
    private LocalDate thDate;

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public Integer getCoinsCollected() {
        return coinsCollected;
    }

    public void setCoinsCollected(Integer coinsCollected) {
        this.coinsCollected = coinsCollected;
    }

    public Boolean getIsStreakBroken() {
        return isStreakBroken;
    }

    public void setIsStreakBroken(Boolean isStreakBroken) {
        this.isStreakBroken = isStreakBroken;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Integer getNumCoins() {
        return numCoins;
    }

    public void setNumCoins(Integer numCoins) {
        this.numCoins = numCoins;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public Integer getStreak() {
        return streak;
    }

    public void setStreak(Integer streak) {
        this.streak = streak;
    }

    public LocalDate getThDate() {
        return thDate;
    }

    public void setThDate(LocalDate thDate) {
        this.thDate = thDate;
    }

}