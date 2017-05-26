package com.soapboxrace.core.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BASKETDEFINITION")
public class BasketDefinitionEntity {

	@Id
	private String productId;

	@Column(length = 40000)
	private String ownedCarTrans;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOwnedCarTrans() {
		return ownedCarTrans;
	}

	public void setOwnedCarTrans(String ownedCarTrans) {
		this.ownedCarTrans = ownedCarTrans;
	}

}
