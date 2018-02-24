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
@Table(name = "PERFORMANCEPART")
@NamedQueries({ @NamedQuery(name = "PerformancePartEntity.deleteByCustomCar", //
		query = "DELETE FROM PerformancePartEntity obj WHERE obj.customCar = :customCar") //
})
public class PerformancePartEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int performancePartAttribHash;

	@ManyToOne
	@JoinColumn(name = "customCarId", referencedColumnName = "ID")
	private CustomCarEntity customCar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPerformancePartAttribHash() {
		return performancePartAttribHash;
	}

	public void setPerformancePartAttribHash(int performancePartAttribHash) {
		this.performancePartAttribHash = performancePartAttribHash;
	}

	public CustomCarEntity getCustomCar() {
		return customCar;
	}

	public void setCustomCar(CustomCarEntity customCar) {
		this.customCar = customCar;
	}

}
