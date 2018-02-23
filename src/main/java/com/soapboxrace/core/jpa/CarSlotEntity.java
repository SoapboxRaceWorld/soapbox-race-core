package com.soapboxrace.core.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CARSLOT")
@NamedQueries({ @NamedQuery(name = "CarSlotEntity.findByPersonaId", //
		query = "SELECT obj FROM CarSlotEntity obj WHERE obj.persona = :persona ORDER by obj.id"), //
		@NamedQuery(name = "CarSlotEntity.deleteByPersona", //
				query = "DELETE FROM CarSlotEntity obj WHERE obj.persona = :persona") //
})
public class CarSlotEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PersonaId", referencedColumnName = "ID")
	private PersonaEntity persona;

	@OneToOne(mappedBy = "carSlot", targetEntity = OwnedCarEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private OwnedCarEntity ownedCar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PersonaEntity getPersona() {
		return persona;
	}

	public void setPersona(PersonaEntity persona) {
		this.persona = persona;
	}

	public OwnedCarEntity getOwnedCar() {
		return ownedCar;
	}

	public void setOwnedCar(OwnedCarEntity ownedCar) {
		this.ownedCar = ownedCar;
	}

}