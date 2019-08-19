package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "LEVEL_REP")
@NamedQueries({
        @NamedQuery(name = "LevelRepEntity.findAll", query = "SELECT obj FROM LevelRepEntity obj"), //
        @NamedQuery(name = "LevelRepEntity.findByLevel", query = "SELECT obj FROM LevelRepEntity obj WHERE obj.level " +
                "= :level"), //
        @NamedQuery(name = "LevelRepEntity.findMaxLevel", query = "SELECT MAX(obj.level) FROM LevelRepEntity obj"), //
})
public class LevelRepEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long level;
    private Long expPoint;

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getExpPoint() {
        return expPoint;
    }

    public void setExpPoint(Long expPoint) {
        this.expPoint = expPoint;
    }

}