package com.soapboxrace.core.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMCAR")
public class CustomCarEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int baseCar;
	private int carClassHash;
	private boolean isPreset;
	private int level;
	private String name;
	private int physicsProfileHash;
	private int rating;
	private float resalePrice;
	private float rideHeightDrop;
	private int skillModSlotCount;
	private int version;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ownedCarId", referencedColumnName = "ID")
	private OwnedCarEntity ownedCar;

	@Lob
	@Column(length = 65535)
	private String paints;

	@OneToMany(mappedBy = "customCar", targetEntity = PerformancePartEntity.class)
	private List<PerformancePartEntity> performanceParts;

	@OneToMany(mappedBy = "customCar", targetEntity = SkillModPartEntity.class)
	private List<SkillModPartEntity> skillModParts;

	@Lob
	@Column(length = 65535)
	private String vinyls;

	@OneToMany(mappedBy = "customCar", targetEntity = VisualPartEntity.class)
	private List<VisualPartEntity> visualParts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBaseCar() {
		return baseCar;
	}

	public void setBaseCar(int baseCar) {
		this.baseCar = baseCar;
	}

	public int getCarClassHash() {
		return carClassHash;
	}

	public void setCarClassHash(int carClassHash) {
		this.carClassHash = carClassHash;
	}

	public boolean isPreset() {
		return isPreset;
	}

	public void setPreset(boolean isPreset) {
		this.isPreset = isPreset;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhysicsProfileHash() {
		return physicsProfileHash;
	}

	public void setPhysicsProfileHash(int physicsProfileHash) {
		this.physicsProfileHash = physicsProfileHash;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public float getResalePrice() {
		return resalePrice;
	}

	public void setResalePrice(float resalePrice) {
		this.resalePrice = resalePrice;
	}

	public float getRideHeightDrop() {
		return rideHeightDrop;
	}

	public void setRideHeightDrop(float rideHeightDrop) {
		this.rideHeightDrop = rideHeightDrop;
	}

	public int getSkillModSlotCount() {
		return skillModSlotCount;
	}

	public void setSkillModSlotCount(int skillModSlotCount) {
		this.skillModSlotCount = skillModSlotCount;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<PerformancePartEntity> getPerformanceParts() {
		return performanceParts;
	}

	public void setPerformanceParts(List<PerformancePartEntity> performanceParts) {
		this.performanceParts = performanceParts;
	}

	public List<SkillModPartEntity> getSkillModParts() {
		return skillModParts;
	}

	public void setSkillModParts(List<SkillModPartEntity> skillModParts) {
		this.skillModParts = skillModParts;
	}

	public List<VisualPartEntity> getVisualParts() {
		return visualParts;
	}

	public void setVisualParts(List<VisualPartEntity> visualParts) {
		this.visualParts = visualParts;
	}

	public String getPaints() {
		return paints;
	}

	public void setPaints(String paints) {
		this.paints = paints;
	}

	public String getVinyls() {
		return vinyls;
	}

	public void setVinyls(String vinyls) {
		this.vinyls = vinyls;
	}

	public OwnedCarEntity getOwnedCar() {
		return ownedCar;
	}

	public void setOwnedCar(OwnedCarEntity ownedCar) {
		this.ownedCar = ownedCar;
	}

}
