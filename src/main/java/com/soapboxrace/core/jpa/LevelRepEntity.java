package com.soapboxrace.core.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "LEVEL_REP")
@NamedQueries({ //
	@NamedQuery(name = "LevelRepEntity.findByLevel", query = "SELECT obj FROM LevelRepEntity obj WHERE obj.level = :level") //
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