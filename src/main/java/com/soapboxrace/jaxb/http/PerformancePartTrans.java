
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de PerformancePartTrans complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="PerformancePartTrans">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PerformancePartAttribHash" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PerformancePartTrans", propOrder = {
    "performancePartAttribHash"
})
public class PerformancePartTrans {

    @XmlElement(name = "PerformancePartAttribHash")
    protected int performancePartAttribHash;

    /**
     * Obtém o valor da propriedade performancePartAttribHash.
     * 
     */
    public int getPerformancePartAttribHash() {
        return performancePartAttribHash;
    }

    /**
     * Define o valor da propriedade performancePartAttribHash.
     * 
     */
    public void setPerformancePartAttribHash(int value) {
        this.performancePartAttribHash = value;
    }

}
