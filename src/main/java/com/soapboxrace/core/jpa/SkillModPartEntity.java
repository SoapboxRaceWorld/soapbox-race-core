package com.soapboxrace.core.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SKILLMODPART")
@NamedQueries({ @NamedQuery(name = "SkillModPartEntity.deleteByCustomCar", //
		query = "DELETE FROM SkillModPartEntity obj WHERE obj.customCar = :customCar") //
})
public class SkillModPartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private boolean isFixed;
	private int skillModPartAttribHash;

	@ManyToOne
	@JoinColumn(name = "customCarId", referencedColumnName = "ID")
	private CustomCarEntity customCar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isFixed() {
		return isFixed;
	}

	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}

	public int getSkillModPartAttribHash() {
		return skillModPartAttribHash;
	}

	public void setSkillModPartAttribHash(int skillModPartAttribHash) {
		this.skillModPartAttribHash = skillModPartAttribHash;
	}

	public CustomCarEntity getCustomCar() {
		return customCar;
	}

	public void setCustomCar(CustomCarEntity customCar) {
		this.customCar = customCar;
	}

}
