package com.soapboxrace.core.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class UserEntity {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "EMAIL", length = 255)
	private String email;

	@Column(name = "PASSWORD", length = 50)
	private String password;

	// @OneToMany(mappedBy = "user", targetEntity = PersonaEntity.class)
	// private List<PersonaEntity> listOfPersona;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	// public void setListOfPersona(List<PersonaEntity> listOfPersona) {
	// this.listOfPersona = listOfPersona;
	// }
	//
	// public List<PersonaEntity> getListOfPersona() {
	// return this.listOfPersona;
	// }
}
