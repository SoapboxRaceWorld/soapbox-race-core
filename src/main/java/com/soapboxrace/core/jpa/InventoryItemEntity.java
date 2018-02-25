package com.soapboxrace.core.jpa;

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
@Table(name = "PERSONAINVENTORYITEM")
@NamedQueries({ //
		@NamedQuery(name = "InventoryItemEntity.findAllForInventory", //
				query = "SELECT obj FROM InventoryItemEntity obj " //
						+ " WHERE obj.inventory = :inventory"),
		@NamedQuery(name = "InventoryItemEntity.findByPersonaEntitlEmentId", //
				query = "SELECT obj FROM InventoryItemEntity obj " //
						+ " WHERE obj.persona.id= :personaId and obj.entitlementTag =:entitlementTag"),
		@NamedQuery(name = "InventoryItemEntity.findByPersonaHash", //
				query = "SELECT obj FROM InventoryItemEntity obj " //
						+ " WHERE obj.persona.id= :personaId and obj.hash =:hash"),
		@NamedQuery(name = "InventoryItemEntity.deleteByPersona", //
				query = "DELETE FROM InventoryItemEntity obj WHERE obj.persona.id = :personaId")//
})
public class InventoryItemEntity {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "inventoryId", referencedColumnName = "ID")
	private InventoryEntity inventory;

	@ManyToOne
	@JoinColumn(name = "personaId")
	private PersonaEntity persona;

	private String entitlementTag;

	private String expirationDate;

	private Integer hash;

	private String productId;

	private int remainingUseCount;

	private float resalePrice;

	private String status;

	private String stringHash;

	private String virtualItemType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InventoryEntity getInventory() {
		return inventory;
	}

	public void setInventory(InventoryEntity inventory) {
		this.inventory = inventory;
	}

	public PersonaEntity getPersona() {
		return persona;
	}

	public void setPersona(PersonaEntity persona) {
		this.persona = persona;
	}

	public String getEntitlementTag() {
		return entitlementTag;
	}

	public void setEntitlementTag(String entitlementTag) {
		this.entitlementTag = entitlementTag;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Integer getHash() {
		return hash;
	}

	public void setHash(Integer hash) {
		this.hash = hash;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getRemainingUseCount() {
		return remainingUseCount;
	}

	public void setRemainingUseCount(int remainingUseCount) {
		this.remainingUseCount = remainingUseCount;
	}

	public float getResalePrice() {
		return resalePrice;
	}

	public void setResalePrice(float resalePrice) {
		this.resalePrice = resalePrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStringHash() {
		return stringHash;
	}

	public void setStringHash(String stringHash) {
		this.stringHash = stringHash;
	}

	public String getVirtualItemType() {
		return virtualItemType;
	}

	public void setVirtualItemType(String virtualItemType) {
		this.virtualItemType = virtualItemType;
	}
}
