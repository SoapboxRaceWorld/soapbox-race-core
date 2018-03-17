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
@Table(name = "VINYL")
@NamedQueries({ @NamedQuery(name = "VinylEntity.deleteByCustomCar", //
		query = "DELETE FROM VinylEntity obj WHERE obj.customCar = :customCar") //
})
public class VinylEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "customCarId", referencedColumnName = "ID")
	private CustomCarEntity customCar;

	private int hash;
	private int hue1;
	private int hue2;
	private int hue3;
	private int hue4;
	private int layer;
	private boolean mir;
	private int rot;
	private int sat1;
	private int sat2;
	private int sat3;
	private int sat4;
	private int scalex;
	private int scaley;
	private int shear;
	private int tranx;
	private int trany;
	private int var1;
	private int var2;
	private int var3;
	private int var4;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomCarEntity getCustomCar() {
		return customCar;
	}

	public void setCustomCar(CustomCarEntity customCar) {
		this.customCar = customCar;
	}

	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public int getHue1() {
		return hue1;
	}

	public void setHue1(int hue1) {
		this.hue1 = hue1;
	}

	public int getHue2() {
		return hue2;
	}

	public void setHue2(int hue2) {
		this.hue2 = hue2;
	}

	public int getHue3() {
		return hue3;
	}

	public void setHue3(int hue3) {
		this.hue3 = hue3;
	}

	public int getHue4() {
		return hue4;
	}

	public void setHue4(int hue4) {
		this.hue4 = hue4;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public boolean isMir() {
		return mir;
	}

	public void setMir(boolean mir) {
		this.mir = mir;
	}

	public int getRot() {
		return rot;
	}

	public void setRot(int rot) {
		this.rot = rot;
	}

	public int getSat1() {
		return sat1;
	}

	public void setSat1(int sat1) {
		this.sat1 = sat1;
	}

	public int getSat2() {
		return sat2;
	}

	public void setSat2(int sat2) {
		this.sat2 = sat2;
	}

	public int getSat3() {
		return sat3;
	}

	public void setSat3(int sat3) {
		this.sat3 = sat3;
	}

	public int getSat4() {
		return sat4;
	}

	public void setSat4(int sat4) {
		this.sat4 = sat4;
	}

	public int getScalex() {
		return scalex;
	}

	public void setScalex(int scalex) {
		this.scalex = scalex;
	}

	public int getScaley() {
		return scaley;
	}

	public void setScaley(int scaley) {
		this.scaley = scaley;
	}

	public int getShear() {
		return shear;
	}

	public void setShear(int shear) {
		this.shear = shear;
	}

	public int getTranx() {
		return tranx;
	}

	public void setTranx(int tranx) {
		this.tranx = tranx;
	}

	public int getTrany() {
		return trany;
	}

	public void setTrany(int trany) {
		this.trany = trany;
	}

	public int getVar1() {
		return var1;
	}

	public void setVar1(int var1) {
		this.var1 = var1;
	}

	public int getVar2() {
		return var2;
	}

	public void setVar2(int var2) {
		this.var2 = var2;
	}

	public int getVar3() {
		return var3;
	}

	public void setVar3(int var3) {
		this.var3 = var3;
	}

	public int getVar4() {
		return var4;
	}

	public void setVar4(int var4) {
		this.var4 = var4;
	}

}
