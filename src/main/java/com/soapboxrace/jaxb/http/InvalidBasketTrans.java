
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de InvalidBasketTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="InvalidBasketTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Basket" type="{}BasketTrans" minOccurs="0"/>
 *         &lt;element name="InvalidItems" type="{}ArrayOfInvalidBasketItemTrans" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvalidBasketTrans", propOrder = {
    "basket",
    "invalidItems"
})
public class InvalidBasketTrans {

    @XmlElement(name = "Basket")
    protected BasketTrans basket;
    @XmlElement(name = "InvalidItems")
    protected ArrayOfInvalidBasketItemTrans invalidItems;

    /**
     * Obtém o valor da propriedade basket.
     * 
     * @return
     *     possible object is
     *     {@link BasketTrans }
     *     
     */
    public BasketTrans getBasket() {
        return basket;
    }

    /**
     * Define o valor da propriedade basket.
     * 
     * @param value
     *     allowed object is
     *     {@link BasketTrans }
     *     
     */
    public void setBasket(BasketTrans value) {
        this.basket = value;
    }

    /**
     * Obtém o valor da propriedade invalidItems.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInvalidBasketItemTrans }
     *     
     */
    public ArrayOfInvalidBasketItemTrans getInvalidItems() {
        return invalidItems;
    }

    /**
     * Define o valor da propriedade invalidItems.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInvalidBasketItemTrans }
     *     
     */
    public void setInvalidItems(ArrayOfInvalidBasketItemTrans value) {
        this.invalidItems = value;
    }

}
