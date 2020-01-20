/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

import com.soapboxrace.jaxb.http.*;

import java.util.List;

public class AchievementCustomizationContext {
    private final List<CustomPaintTrans> paintsAdded;
    private final List<PerformancePartTrans> performancePartsAdded;
    private final List<VisualPartTrans> visualPartsAdded;
    private final List<SkillModPartTrans> skillModPartsAdded;
    private final List<CustomVinylTrans> vinylsAdded;

    public AchievementCustomizationContext(List<CustomPaintTrans> paintsAdded,
                                           List<PerformancePartTrans> performancePartsAdded,
                                           List<VisualPartTrans> visualPartsAdded,
                                           List<SkillModPartTrans> skillModPartsAdded,
                                           List<CustomVinylTrans> vinylsAdded) {
        this.paintsAdded = paintsAdded;
        this.performancePartsAdded = performancePartsAdded;
        this.visualPartsAdded = visualPartsAdded;
        this.skillModPartsAdded = skillModPartsAdded;
        this.vinylsAdded = vinylsAdded;
    }
}
