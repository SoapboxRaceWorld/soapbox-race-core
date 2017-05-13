package com.soapboxrace.core.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "TOKEN_SESSION")
@NamedQueries({ //
		@NamedQuery(name = "TokenSessionEntity.deleteByUserId", query = "DELETE FROM TokenSessionEntity obj WHERE obj.userId = :userId") //
})
public class TokenSessionEntity {

	@Id
	@Column(name = "ID", nullable = false)
	private String securityToken;

	private Long userId;

	private Date expirationDate;

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
