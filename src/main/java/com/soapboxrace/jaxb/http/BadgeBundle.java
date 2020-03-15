/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "BadgeBundle")
@XmlAccessorType(XmlAccessType.FIELD)
public class BadgeBundle {
    @XmlElementWrapper(name = "Badges")
    @XmlElement(name = "BadgeInput")
    private List<BadgeInput> badgeInputs = new ArrayList<>();

    public List<BadgeInput> getBadgeInputs() {
        return badgeInputs;
    }

    public void setBadgeInputs(List<BadgeInput> badgeInputs) {
        this.badgeInputs = badgeInputs;
    }
}