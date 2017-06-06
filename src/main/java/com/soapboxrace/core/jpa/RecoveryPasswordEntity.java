package com.soapboxrace.core.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "RECOVERY_PASSWORD")
@NamedQueries({ //
	@NamedQuery(name = "RecoveryPasswordEntity.findByUserId", query = "SELECT obj FROM RecoveryPasswordEntity obj WHERE obj.userId = :userId") //
})
public class RecoveryPasswordEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;
	private String randomKey;
	private Integer time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOwnedCarTrans() {
		return randomKey;
	}

	public void setOwnedCarTrans(String randomKey) {
		this.randomKey = randomKey;
	}
	
	public Integer getTime() {
		return time;
	}
	
	public void setTime(Integer time) {
		this.time = time;
	}

}