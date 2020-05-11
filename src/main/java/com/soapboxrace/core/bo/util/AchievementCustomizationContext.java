/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.bo.util;

import com.soapboxrace.core.jpa.ProductEntity;
import com.soapboxrace.jaxb.http.*;

import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
public class AchievementCustomizationContext {

    private final Type type;
    private Collection<CustomPaintTrans> paintsAdded;
    private Collection<WrappedPart<PerformancePartTrans>> performancePartsAdded;
    private Collection<VisualPartTrans> visualPartsAdded;
    private Collection<WrappedPart<SkillModPartTrans>> skillModPartsAdded;
    private Collection<CustomVinylTrans> vinylsAdded;

    public AchievementCustomizationContext(Type type) {
        this.type = type;
    }

    public int getTypeVal() {
        return this.type.ordinal();
    }

    private void checkType(Type expected) {
        if (this.type != expected) {
            throw new UnsupportedOperationException("Context type is not " + expected.name());
        }
    }

    public Collection<CustomPaintTrans> getPaintsAdded() {
        checkType(Type.PAINT);
        return paintsAdded;
    }

    public void setPaintsAdded(Collection<CustomPaintTrans> paintsAdded) {
        checkType(Type.PAINT);
        this.paintsAdded = paintsAdded;
    }

    public Collection<WrappedPart<PerformancePartTrans>> getPerformancePartsAdded() {
        checkType(Type.PERF);
        return performancePartsAdded;
    }

    public void setPerformancePartsAdded(Collection<WrappedPart<PerformancePartTrans>> performancePartsAdded) {
        checkType(Type.PERF);
        this.performancePartsAdded = performancePartsAdded;
    }

    public Collection<VisualPartTrans> getVisualPartsAdded() {
        checkType(Type.AFTERMARKET);
        return visualPartsAdded;
    }

    public void setVisualPartsAdded(Collection<VisualPartTrans> visualPartsAdded) {
        checkType(Type.AFTERMARKET);
        this.visualPartsAdded = visualPartsAdded;
    }

    public Collection<WrappedPart<SkillModPartTrans>> getSkillModPartsAdded() {
        checkType(Type.SKILLS);
        return skillModPartsAdded;
    }

    public void setSkillModPartsAdded(Collection<WrappedPart<SkillModPartTrans>> skillModPartsAdded) {
        checkType(Type.SKILLS);
        this.skillModPartsAdded = skillModPartsAdded;
    }

    public Collection<CustomVinylTrans> getVinylsAdded() {
        checkType(Type.VINYLS);
        return vinylsAdded;
    }

    public void setVinylsAdded(Collection<CustomVinylTrans> vinylsAdded) {
        checkType(Type.VINYLS);
        this.vinylsAdded = vinylsAdded;
    }

    public Type getType() {
        return type;
    }

    public long countPerfPartsWithRating(Integer rating) {
        return countPartsWithRating(performancePartsAdded, rating);
    }

    public long countSkillModsWithRating(Integer rating) {
        return countPartsWithRating(skillModPartsAdded, rating);
    }

    public long countPerfPartsWithRatingAndType(Integer rating, String subType) {
        return countPartsWithRatingAndType(performancePartsAdded, rating, subType);
    }

    public long countSkillModsWithRatingAndType(Integer rating, String subType) {
        return countPartsWithRatingAndType(skillModPartsAdded, rating, subType);
    }

    public <T> long countPartsWithRating(Collection<WrappedPart<T>> collection, Integer rating) {
        Objects.requireNonNull(rating);
        return collection.stream()
                .filter(p -> rating.equals(p.getProductEntity().getRarity()))
                .count();
    }

    public <T> long countPartsWithRatingAndType(Collection<WrappedPart<T>> collection, Integer rating, String subType) {
        Objects.requireNonNull(rating);
        Objects.requireNonNull(subType);
        return collection.stream()
                .filter(p -> rating.equals(p.getProductEntity().getRarity()) && subType.equals(p.getProductEntity().getSubType()))
                .count();
    }

    public enum Type {
        SKILLS,
        PERF,
        VINYLS,
        AFTERMARKET,
        PAINT
    }

    public static class WrappedPart<TP> {
        private final TP part;

        private final ProductEntity productEntity;

        private final Integer rating;

        public WrappedPart(TP part, ProductEntity productEntity, Integer rating) {
            this.part = part;
            this.productEntity = productEntity;
            this.rating = rating;
        }

        public TP getPart() {
            return part;
        }

        public ProductEntity getProductEntity() {
            return productEntity;
        }

        public Integer getRating() {
            return rating;
        }
    }
}
