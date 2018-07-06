package com.soapboxrace.jaxb.http;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "BadgeBundle")
@XmlAccessorType(XmlAccessType.FIELD)
public class BadgeBundle
{
    @XmlElementWrapper(name = "Badges")
    @XmlElement(name = "BadgeInput")
    private List<BadgeInput> badgeInputs = new ArrayList<>();

    public List<BadgeInput> getBadgeInputs()
    {
        return badgeInputs;
    }

    public void setBadgeInputs(List<BadgeInput> badgeInputs)
    {
        this.badgeInputs = badgeInputs;
    }
}