package com.soapboxrace.core.jpa;

import javax.persistence.Column;
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
@Table(name = "PAINT")
@NamedQueries({ @NamedQuery(name = "PaintEntity.deleteByCustomCar", //
		query = "DELETE FROM PaintEntity obj WHERE obj.customCar = :customCar") //
})
public class PaintEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "customCarId", referencedColumnName = "ID")
	private CustomCarEntity customCar;

	@Column(name = "paintGroup")
	private int group;
	private int hue;
	private int sat;
	private int slot;
	@Column(name = "paintVar")
	private int var;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomCarEntity getCustomCar() {
		return customCar;
	}

	public void setCustomCar(CustomCarEntity customCar) {
		this.customCar = customCar;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public int getHue() {
		return hue;
	}

	public void setHue(int hue) {
		this.hue = hue;
	}

	public int getSat() {
		return sat;
	}

	public void setSat(int sat) {
		this.sat = sat;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public int getVar() {
		return var;
	}

	public void setVar(int var) {
		this.var = var;
	}

}
