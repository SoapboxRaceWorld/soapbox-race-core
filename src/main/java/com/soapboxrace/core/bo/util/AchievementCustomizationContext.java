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
        return countPerfPartsWithRating(rating, true);
    }

    public long countPerfPartsWithRating(Integer rating, boolean exact) {
        return countPartsWithRating(performancePartsAdded, rating, exact);
    }

    public long countSkillModsWithRating(Integer rating) {
        return countSkillModsWithRating(rating, true);
    }

    public long countSkillModsWithRating(Integer rating, boolean exact) {
        return countPartsWithRating(skillModPartsAdded, rating, exact);
    }

    public long countPerfPartsWithRatingAndType(Integer rating, String subType) {
        return countPerfPartsWithRatingAndType(rating, subType, true);
    }

    public long countPerfPartsWithRatingAndType(Integer rating, String subType, boolean exact) {
        return countPartsWithRatingAndType(performancePartsAdded, rating, subType, exact);
    }

    public long countSkillModsWithRatingAndType(Integer rating, String subType) {
        return countSkillModsWithRatingAndType(rating, subType, true);
    }

    public long countSkillModsWithRatingAndType(Integer rating, String subType, boolean exact) {
        return countPartsWithRatingAndType(skillModPartsAdded, rating, subType, exact);
    }

    public <T> long countPartsWithRating(Collection<WrappedPart<T>> collection, Integer rating) {
        return countPartsWithRating(collection, rating, true);
    }

    public <T> long countPartsWithRating(Collection<WrappedPart<T>> collection, Integer rating, boolean exact) {
        Objects.requireNonNull(rating);
        long cnt = 0;

        for (WrappedPart<T> wrappedPart : collection) {
            ProductEntity productEntity = wrappedPart.getProductEntity();

            if (productEntity.getRarity() == null) continue;

            if (exact && productEntity.getRarity().equals(rating)) {
                cnt++;
            } else if (!exact && (rating <= productEntity.getRarity())) {
                cnt++;
            }
        }

        return cnt;
    }

    public <T> long countPartsWithRatingAndType(Collection<WrappedPart<T>> collection, Integer rating, String subType) {
        return countPartsWithRatingAndType(collection, rating, subType, true);
    }

    public <T> long countPartsWithRatingAndType(Collection<WrappedPart<T>> collection, Integer rating, String subType, boolean exact) {
        Objects.requireNonNull(rating);
        Objects.requireNonNull(subType);
        long cnt = 0;

        for (WrappedPart<T> wrappedPart : collection) {
            ProductEntity productEntity = wrappedPart.getProductEntity();

            if (subType.equalsIgnoreCase(productEntity.getSubType())) {
                if (productEntity.getRarity() == null) continue;
                if (exact && productEntity.getRarity().equals(rating)) {
                    cnt++;
                } else if (!exact && (rating <= productEntity.getRarity())) {
                    cnt++;
                }
            }
        }

        return cnt;
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

        public WrappedPart(TP part, ProductEntity productEntity) {
            this.part = part;
            this.productEntity = productEntity;
        }

        public TP getPart() {
            return part;
        }

        public ProductEntity getProductEntity() {
            return productEntity;
        }
    }
}
