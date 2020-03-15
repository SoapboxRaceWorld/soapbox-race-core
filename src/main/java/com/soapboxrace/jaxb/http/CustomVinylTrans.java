
/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for CustomVinylTrans complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="CustomVinylTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Hash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Hue1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Hue2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Hue3" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Hue4" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Layer" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Mir" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Rot" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sat1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sat2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sat3" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sat4" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ScaleX" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ScaleY" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Shear" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TranX" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TranY" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Var1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Var2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Var3" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Var4" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomVinylTrans", propOrder = {"hash", "hue1", "hue2", "hue3", "hue4", "layer", "mir", "rot", "sat1", "sat2", "sat3", "sat4", "scaleX",
        "scaleY", "shear", "tranX", "tranY", "var1", "var2", "var3", "var4"})
public class CustomVinylTrans {

    @XmlElement(name = "Hash")
    protected int hash;
    @XmlElement(name = "Hue1")
    protected int hue1;
    @XmlElement(name = "Hue2")
    protected int hue2;
    @XmlElement(name = "Hue3")
    protected int hue3;
    @XmlElement(name = "Hue4")
    protected int hue4;
    @XmlElement(name = "Layer")
    protected int layer;
    @XmlElement(name = "Mir")
    protected boolean mir;
    @XmlElement(name = "Rot")
    protected int rot;
    @XmlElement(name = "Sat1")
    protected int sat1;
    @XmlElement(name = "Sat2")
    protected int sat2;
    @XmlElement(name = "Sat3")
    protected int sat3;
    @XmlElement(name = "Sat4")
    protected int sat4;
    @XmlElement(name = "ScaleX")
    protected int scaleX;
    @XmlElement(name = "ScaleY")
    protected int scaleY;
    @XmlElement(name = "Shear")
    protected int shear;
    @XmlElement(name = "TranX")
    protected int tranX;
    @XmlElement(name = "TranY")
    protected int tranY;
    @XmlElement(name = "Var1")
    protected int var1;
    @XmlElement(name = "Var2")
    protected int var2;
    @XmlElement(name = "Var3")
    protected int var3;
    @XmlElement(name = "Var4")
    protected int var4;

    /**
     * Gets the value of the hash property.
     */
    public int getHash() {
        return hash;
    }

    /**
     * Sets the value of the hash property.
     */
    public void setHash(int value) {
        this.hash = value;
    }

    /**
     * Gets the value of the hue1 property.
     */
    public int getHue1() {
        return hue1;
    }

    /**
     * Sets the value of the hue1 property.
     */
    public void setHue1(int value) {
        this.hue1 = value;
    }

    /**
     * Gets the value of the hue2 property.
     */
    public int getHue2() {
        return hue2;
    }

    /**
     * Sets the value of the hue2 property.
     */
    public void setHue2(int value) {
        this.hue2 = value;
    }

    /**
     * Gets the value of the hue3 property.
     */
    public int getHue3() {
        return hue3;
    }

    /**
     * Sets the value of the hue3 property.
     */
    public void setHue3(int value) {
        this.hue3 = value;
    }

    /**
     * Gets the value of the hue4 property.
     */
    public int getHue4() {
        return hue4;
    }

    /**
     * Sets the value of the hue4 property.
     */
    public void setHue4(int value) {
        this.hue4 = value;
    }

    /**
     * Gets the value of the layer property.
     */
    public int getLayer() {
        return layer;
    }

    /**
     * Sets the value of the layer property.
     */
    public void setLayer(int value) {
        this.layer = value;
    }

    /**
     * Gets the value of the mir property.
     */
    public boolean isMir() {
        return mir;
    }

    /**
     * Sets the value of the mir property.
     */
    public void setMir(boolean value) {
        this.mir = value;
    }

    /**
     * Gets the value of the rot property.
     */
    public int getRot() {
        return rot;
    }

    /**
     * Sets the value of the rot property.
     */
    public void setRot(int value) {
        this.rot = value;
    }

    /**
     * Gets the value of the sat1 property.
     */
    public int getSat1() {
        return sat1;
    }

    /**
     * Sets the value of the sat1 property.
     */
    public void setSat1(int value) {
        this.sat1 = value;
    }

    /**
     * Gets the value of the sat2 property.
     */
    public int getSat2() {
        return sat2;
    }

    /**
     * Sets the value of the sat2 property.
     */
    public void setSat2(int value) {
        this.sat2 = value;
    }

    /**
     * Gets the value of the sat3 property.
     */
    public int getSat3() {
        return sat3;
    }

    /**
     * Sets the value of the sat3 property.
     */
    public void setSat3(int value) {
        this.sat3 = value;
    }

    /**
     * Gets the value of the sat4 property.
     */
    public int getSat4() {
        return sat4;
    }

    /**
     * Sets the value of the sat4 property.
     */
    public void setSat4(int value) {
        this.sat4 = value;
    }

    /**
     * Gets the value of the scaleX property.
     */
    public int getScaleX() {
        return scaleX;
    }

    /**
     * Sets the value of the scaleX property.
     */
    public void setScaleX(int value) {
        this.scaleX = value;
    }

    /**
     * Gets the value of the scaleY property.
     */
    public int getScaleY() {
        return scaleY;
    }

    /**
     * Sets the value of the scaleY property.
     */
    public void setScaleY(int value) {
        this.scaleY = value;
    }

    /**
     * Gets the value of the shear property.
     */
    public int getShear() {
        return shear;
    }

    /**
     * Sets the value of the shear property.
     */
    public void setShear(int value) {
        this.shear = value;
    }

    /**
     * Gets the value of the tranX property.
     */
    public int getTranX() {
        return tranX;
    }

    /**
     * Sets the value of the tranX property.
     */
    public void setTranX(int value) {
        this.tranX = value;
    }

    /**
     * Gets the value of the tranY property.
     */
    public int getTranY() {
        return tranY;
    }

    /**
     * Sets the value of the tranY property.
     */
    public void setTranY(int value) {
        this.tranY = value;
    }

    /**
     * Gets the value of the var1 property.
     */
    public int getVar1() {
        return var1;
    }

    /**
     * Sets the value of the var1 property.
     */
    public void setVar1(int value) {
        this.var1 = value;
    }

    /**
     * Gets the value of the var2 property.
     */
    public int getVar2() {
        return var2;
    }

    /**
     * Sets the value of the var2 property.
     */
    public void setVar2(int value) {
        this.var2 = value;
    }

    /**
     * Gets the value of the var3 property.
     */
    public int getVar3() {
        return var3;
    }

    /**
     * Sets the value of the var3 property.
     */
    public void setVar3(int value) {
        this.var3 = value;
    }

    /**
     * Gets the value of the var4 property.
     */
    public int getVar4() {
        return var4;
    }

    /**
     * Sets the value of the var4 property.
     */
    public void setVar4(int value) {
        this.var4 = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + hash;
        result = prime * result + hue1;
        result = prime * result + hue2;
        result = prime * result + hue3;
        result = prime * result + hue4;
        result = prime * result + layer;
        result = prime * result + (mir ? 1231 : 1237);
        result = prime * result + rot;
        result = prime * result + sat1;
        result = prime * result + sat2;
        result = prime * result + sat3;
        result = prime * result + sat4;
        result = prime * result + scaleX;
        result = prime * result + scaleY;
        result = prime * result + shear;
        result = prime * result + tranX;
        result = prime * result + tranY;
        result = prime * result + var1;
        result = prime * result + var2;
        result = prime * result + var3;
        result = prime * result + var4;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomVinylTrans other = (CustomVinylTrans) obj;
        if (hash != other.hash)
            return false;
        if (hue1 != other.hue1)
            return false;
        if (hue2 != other.hue2)
            return false;
        if (hue3 != other.hue3)
            return false;
        if (hue4 != other.hue4)
            return false;
        if (layer != other.layer)
            return false;
        if (mir != other.mir)
            return false;
        if (rot != other.rot)
            return false;
        if (sat1 != other.sat1)
            return false;
        if (sat2 != other.sat2)
            return false;
        if (sat3 != other.sat3)
            return false;
        if (sat4 != other.sat4)
            return false;
        if (scaleX != other.scaleX)
            return false;
        if (scaleY != other.scaleY)
            return false;
        if (shear != other.shear)
            return false;
        if (tranX != other.tranX)
            return false;
        if (tranY != other.tranY)
            return false;
        if (var1 != other.var1)
            return false;
        if (var2 != other.var2)
            return false;
        if (var3 != other.var3)
            return false;
        return var4 == other.var4;
    }

}
