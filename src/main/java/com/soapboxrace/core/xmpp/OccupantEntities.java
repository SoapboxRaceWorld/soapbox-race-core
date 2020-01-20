/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.xmpp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "occupants")
public class OccupantEntities {
    List<OccupantEntity> occupants;

    public OccupantEntities() {
    }

    public OccupantEntities(List<OccupantEntity> occupants) {
        this.occupants = occupants;
    }

    @XmlElement(name = "occupant")
    public List<OccupantEntity> getOccupants() {
        return occupants;
    }

    public void setOccupants(List<OccupantEntity> occupants) {
        this.occupants = occupants;
    }
}