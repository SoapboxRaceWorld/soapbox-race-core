package com.soapboxrace.core.jpa;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OWNEDCAR")
public class OwnedCarEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "carSlotId", referencedColumnName = "ID")
	private CarSlotEntity carSlot;

	private int durability;
	private float heat;
	private Date expirationDate;
	private String ownershipType = "CustomizedCar";

	@OneToOne(mappedBy = "ownedCar", targetEntity = CustomCarEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private CustomCarEntity customCar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CarSlotEntity getCarSlot() {
		return carSlot;
	}

	public void setCarSlot(CarSlotEntity carSlot) {
		this.carSlot = carSlot;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public float getHeat() {
		return heat;
	}

	public void setHeat(float heat) {
		this.heat = heat;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getOwnershipType() {
		return ownershipType;
	}

	public void setOwnershipType(String ownershipType) {
		this.ownershipType = ownershipType;
	}

	public CustomCarEntity getCustomCar() {
		return customCar;
	}

	public void setCustomCar(CustomCarEntity customCar) {
		this.customCar = customCar;
	}

}
