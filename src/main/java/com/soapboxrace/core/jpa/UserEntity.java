package com.soapboxrace.core.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@NamedQueries({ //
		@NamedQuery(name = "UserEntity.findAll", query = "SELECT obj FROM UserEntity obj"),
		@NamedQuery(name = "UserEntity.findByEmail", query = "SELECT obj FROM UserEntity obj WHERE obj.email = :email") //
})
public class UserEntity {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "EMAIL", length = 255)
	private String email;

	@Column(name = "PASSWORD", length = 50)
	private String password;

	@OneToMany(mappedBy = "user", targetEntity = PersonaEntity.class)
	private List<PersonaEntity> listOfProfile;

	private boolean premium;

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

	public List<PersonaEntity> getListOfProfile() {
		return listOfProfile;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

}
