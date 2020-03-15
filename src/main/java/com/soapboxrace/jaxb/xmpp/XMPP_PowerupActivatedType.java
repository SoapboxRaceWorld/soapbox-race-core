/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.xmpp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XMPP_PowerupActivatedType", propOrder = {"count", "id", "personaId", "targetPersonaId"})
public class XMPP_PowerupActivatedType {
    @XmlElement(name = "Count", required = true)
    private Integer count = 1;
    @XmlElement(name = "Id", required = true)
    private Long id;
    @XmlElement(name = "PersonaId", required = true)
    private Long personaId;
    @XmlElement(name = "TargetPersonaId", required = true)
    private Long targetPersonaId;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public Long getTargetPersonaId() {
        return targetPersonaId;
    }

    public void setTargetPersonaId(Long targetPersonaId) {
        this.targetPersonaId = targetPersonaId;
    }
}