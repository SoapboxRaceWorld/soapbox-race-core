
package com.soapboxrace.core.jpa;

import java.time.LocalDateTime;

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
@Table(name = "PERSONA")
@NamedQueries({ //
		@NamedQuery(name = "PersonaEntity.findByName", query = "SELECT obj FROM PersonaEntity obj WHERE obj.name = :name") //
})
public class PersonaEntity {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long personaId;
	private double boost;
	private double cash;
	private int iconIndex;
	private int level;
	private String motto;
	private String name;
	private float percentToLevel;
	private double rating;
	private double rep;
	private int repAtCurrentLevel;
	private int score;
	private int curCarIndex = 0;
	@ManyToOne
	@JoinColumn(name = "USERID", referencedColumnName = "ID")
	private UserEntity user;
	
	@Column(name = "created")
	private LocalDateTime created;

	public double getBoost() {
		return boost;
	}

	public void setBoost(double boost) {
		this.boost = boost;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public int getIconIndex() {
		return iconIndex;
	}

	public void setIconIndex(int iconIndex) {
		this.iconIndex = iconIndex;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPercentToLevel() {
		return percentToLevel;
	}

	public void setPercentToLevel(float percentToLevel) {
		this.percentToLevel = percentToLevel;
	}

	public Long getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Long personaId) {
		this.personaId = personaId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public double getRep() {
		return rep;
	}

	public void setRep(double rep) {
		this.rep = rep;
	}

	public int getRepAtCurrentLevel() {
		return repAtCurrentLevel;
	}

	public void setRepAtCurrentLevel(int repAtCurrentLevel) {
		this.repAtCurrentLevel = repAtCurrentLevel;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public int getCurCarIndex() {
		return curCarIndex;
	}

	public void setCurCarIndex(int curCarIndex) {
		this.curCarIndex = curCarIndex;
	}

	public LocalDateTime getCreated()
	{
		return created;
	}

	public void setCreated(LocalDateTime created)
	{
		this.created = created;
	}
}
