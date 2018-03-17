package com.soapboxrace.core.jpa;

import java.util.Date;

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
	@NamedQuery(name = "RecoveryPasswordEntity.findByUserId", query = "SELECT obj FROM RecoveryPasswordEntity obj WHERE obj.userId = :userId"), //
	@NamedQuery(name = "RecoveryPasswordEntity.findAllOpenByUserId", query = "SELECT obj FROM RecoveryPasswordEntity obj WHERE obj.userId = :userId AND obj.isClose = false AND obj.expirationDate > :expirationDate"), //
	@NamedQuery(name = "RecoveryPasswordEntity.findByRandomKey", query = "SELECT obj FROM RecoveryPasswordEntity obj WHERE obj.randomKey = :randomKey") //
})
public class RecoveryPasswordEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;
	private String randomKey;
	private Date expirationDate;
	private Boolean isClose;

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

	public String getRandomKey() {
		return randomKey;
	}

	public void setRandomKey(String randomKey) {
		this.randomKey = randomKey;
	}
	
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public Boolean getIsClose() {
		return isClose;
	}
	
	public void setIsClose(Boolean isClose) {
		this.isClose = isClose;
	}

}