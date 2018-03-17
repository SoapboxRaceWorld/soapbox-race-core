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
@Table(name = "PROMO_CODE")
@NamedQueries({ @NamedQuery(name = "PromoCodeEntity.findByUser", query = "SELECT obj FROM PromoCodeEntity obj WHERE obj.user = :user AND obj.isUsed = false"), //
		@NamedQuery(name = "PromoCodeEntity.findByCode", query = "SELECT obj FROM PromoCodeEntity obj WHERE obj.promoCode = :promoCode") //
})
public class PromoCodeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USERID", referencedColumnName = "ID")
	private UserEntity user;

	private String promoCode;
	private Boolean isUsed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

}
