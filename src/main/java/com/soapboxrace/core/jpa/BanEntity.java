package com.soapboxrace.core.jpa;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.soapboxrace.core.jpa.convert.LocalDateTimeConverter;

@Entity
@Table(name = "BAN")
@NamedQueries({ //
		@NamedQuery(name = "BanEntity.findAll", query = "SELECT obj FROM BanEntity obj") })
public class BanEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@OneToOne(targetEntity = UserEntity.class, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_BAN_USER"))
	private UserEntity userEntity;

	@Column
	private String type;

	@Column
	private String data;

	@Column
	private String reason;

	@Column
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime endsAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public LocalDateTime getEndsAt() {
		return endsAt;
	}

	public void setEndsAt(LocalDateTime endsAt) {
		this.endsAt = endsAt;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean stillApplies() {
		return this.endsAt == null || LocalDateTime.now().isBefore(this.endsAt);
	}

	public enum BanType {
		USER_BAN, IP_BAN, HWID_BAN, EMAIL_BAN
	}
}
