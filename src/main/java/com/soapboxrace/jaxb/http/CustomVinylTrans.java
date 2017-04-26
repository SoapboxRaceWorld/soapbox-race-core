
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de CustomVinylTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
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
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomVinylTrans", propOrder = {
    "hash",
    "hue1",
    "hue2",
    "hue3",
    "hue4",
    "layer",
    "mir",
    "rot",
    "sat1",
    "sat2",
    "sat3",
    "sat4",
    "scaleX",
    "scaleY",
    "shear",
    "tranX",
    "tranY",
    "var1",
    "var2",
    "var3",
    "var4"
})
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
     * Obtém o valor da propriedade hash.
     * 
     */
    public int getHash() {
        return hash;
    }

    /**
     * Define o valor da propriedade hash.
     * 
     */
    public void setHash(int value) {
        this.hash = value;
    }

    /**
     * Obtém o valor da propriedade hue1.
     * 
     */
    public int getHue1() {
        return hue1;
    }

    /**
     * Define o valor da propriedade hue1.
     * 
     */
    public void setHue1(int value) {
        this.hue1 = value;
    }

    /**
     * Obtém o valor da propriedade hue2.
     * 
     */
    public int getHue2() {
        return hue2;
    }

    /**
     * Define o valor da propriedade hue2.
     * 
     */
    public void setHue2(int value) {
        this.hue2 = value;
    }

    /**
     * Obtém o valor da propriedade hue3.
     * 
     */
    public int getHue3() {
        return hue3;
    }

    /**
     * Define o valor da propriedade hue3.
     * 
     */
    public void setHue3(int value) {
        this.hue3 = value;
    }

    /**
     * Obtém o valor da propriedade hue4.
     * 
     */
    public int getHue4() {
        return hue4;
    }

    /**
     * Define o valor da propriedade hue4.
     * 
     */
    public void setHue4(int value) {
        this.hue4 = value;
    }

    /**
     * Obtém o valor da propriedade layer.
     * 
     */
    public int getLayer() {
        return layer;
    }

    /**
     * Define o valor da propriedade layer.
     * 
     */
    public void setLayer(int value) {
        this.layer = value;
    }

    /**
     * Obtém o valor da propriedade mir.
     * 
     */
    public boolean isMir() {
        return mir;
    }

    /**
     * Define o valor da propriedade mir.
     * 
     */
    public void setMir(boolean value) {
        this.mir = value;
    }

    /**
     * Obtém o valor da propriedade rot.
     * 
     */
    public int getRot() {
        return rot;
    }

    /**
     * Define o valor da propriedade rot.
     * 
     */
    public void setRot(int value) {
        this.rot = value;
    }

    /**
     * Obtém o valor da propriedade sat1.
     * 
     */
    public int getSat1() {
        return sat1;
    }

    /**
     * Define o valor da propriedade sat1.
     * 
     */
    public void setSat1(int value) {
        this.sat1 = value;
    }

    /**
     * Obtém o valor da propriedade sat2.
     * 
     */
    public int getSat2() {
        return sat2;
    }

    /**
     * Define o valor da propriedade sat2.
     * 
     */
    public void setSat2(int value) {
        this.sat2 = value;
    }

    /**
     * Obtém o valor da propriedade sat3.
     * 
     */
    public int getSat3() {
        return sat3;
    }

    /**
     * Define o valor da propriedade sat3.
     * 
     */
    public void setSat3(int value) {
        this.sat3 = value;
    }

    /**
     * Obtém o valor da propriedade sat4.
     * 
     */
    public int getSat4() {
        return sat4;
    }

    /**
     * Define o valor da propriedade sat4.
     * 
     */
    public void setSat4(int value) {
        this.sat4 = value;
    }

    /**
     * Obtém o valor da propriedade scaleX.
     * 
     */
    public int getScaleX() {
        return scaleX;
    }

    /**
     * Define o valor da propriedade scaleX.
     * 
     */
    public void setScaleX(int value) {
        this.scaleX = value;
    }

    /**
     * Obtém o valor da propriedade scaleY.
     * 
     */
    public int getScaleY() {
        return scaleY;
    }

    /**
     * Define o valor da propriedade scaleY.
     * 
     */
    public void setScaleY(int value) {
        this.scaleY = value;
    }

    /**
     * Obtém o valor da propriedade shear.
     * 
     */
    public int getShear() {
        return shear;
    }

    /**
     * Define o valor da propriedade shear.
     * 
     */
    public void setShear(int value) {
        this.shear = value;
    }

    /**
     * Obtém o valor da propriedade tranX.
     * 
     */
    public int getTranX() {
        return tranX;
    }

    /**
     * Define o valor da propriedade tranX.
     * 
     */
    public void setTranX(int value) {
        this.tranX = value;
    }

    /**
     * Obtém o valor da propriedade tranY.
     * 
     */
    public int getTranY() {
        return tranY;
    }

    /**
     * Define o valor da propriedade tranY.
     * 
     */
    public void setTranY(int value) {
        this.tranY = value;
    }

    /**
     * Obtém o valor da propriedade var1.
     * 
     */
    public int getVar1() {
        return var1;
    }

    /**
     * Define o valor da propriedade var1.
     * 
     */
    public void setVar1(int value) {
        this.var1 = value;
    }

    /**
     * Obtém o valor da propriedade var2.
     * 
     */
    public int getVar2() {
        return var2;
    }

    /**
     * Define o valor da propriedade var2.
     * 
     */
    public void setVar2(int value) {
        this.var2 = value;
    }

    /**
     * Obtém o valor da propriedade var3.
     * 
     */
    public int getVar3() {
        return var3;
    }

    /**
     * Define o valor da propriedade var3.
     * 
     */
    public void setVar3(int value) {
        this.var3 = value;
    }

    /**
     * Obtém o valor da propriedade var4.
     * 
     */
    public int getVar4() {
        return var4;
    }

    /**
     * Define o valor da propriedade var4.
     * 
     */
    public void setVar4(int value) {
        this.var4 = value;
    }

}
