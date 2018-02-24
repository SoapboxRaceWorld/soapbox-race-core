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
@Table(name = "VISUALPART")
@NamedQueries({ @NamedQuery(name = "VisualPartEntity.deleteByCustomCar", //
		query = "DELETE FROM VisualPartEntity obj WHERE obj.customCar = :customCar") //
})
public class VisualPartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int partHash;
	private int slotHash;

	@ManyToOne
	@JoinColumn(name = "customCarId", referencedColumnName = "ID")
	private CustomCarEntity customCar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPartHash() {
		return partHash;
	}

	public void setPartHash(int partHash) {
		this.partHash = partHash;
	}

	public int getSlotHash() {
		return slotHash;
	}

	public void setSlotHash(int slotHash) {
		this.slotHash = slotHash;
	}

	public CustomCarEntity getCustomCar() {
		return customCar;
	}

	public void setCustomCar(CustomCarEntity customCar) {
		this.customCar = customCar;
	}

}
