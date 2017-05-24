
package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RouteEntrantResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RouteEntrantResult">
 *   &lt;complexContent>
 *     &lt;extension base="{}EntrantResult">
 *       &lt;sequence>
 *         &lt;element name="BestLapDurationInMilliseconds" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="TopSpeed" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RouteEntrantResult", propOrder = {
    "bestLapDurationInMilliseconds",
    "topSpeed"
})
public class RouteEntrantResult
    extends EntrantResult
{

    @XmlElement(name = "BestLapDurationInMilliseconds")
    @XmlSchemaType(name = "unsignedInt")
    protected long bestLapDurationInMilliseconds;
    @XmlElement(name = "TopSpeed")
    protected float topSpeed;

    /**
     * Gets the value of the bestLapDurationInMilliseconds property.
     * 
     */
    public long getBestLapDurationInMilliseconds() {
        return bestLapDurationInMilliseconds;
    }

    /**
     * Sets the value of the bestLapDurationInMilliseconds property.
     * 
     */
    public void setBestLapDurationInMilliseconds(long value) {
        this.bestLapDurationInMilliseconds = value;
    }

    /**
     * Gets the value of the topSpeed property.
     * 
     */
    public float getTopSpeed() {
        return topSpeed;
    }

    /**
     * Sets the value of the topSpeed property.
     * 
     */
    public void setTopSpeed(float value) {
        this.topSpeed = value;
    }

}
