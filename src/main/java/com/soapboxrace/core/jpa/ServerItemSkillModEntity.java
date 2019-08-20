package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "serveritems_skillmod_part")
@NamedQueries({
        @NamedQuery(name = "ServerItemSkillModEntity.findByQualityAndCategory",
                query = "SELECT obj FROM ServerItemSkillModEntity obj WHERE obj.skillModPartQuality = :quality AND " +
                        "obj.skillModCategory = :category")
})
public class ServerItemSkillModEntity {

    @Id
    @Column(name = "CollectionName")
    private String collectionName;

    @Column(name = "Name")
    private String name;

    @Column(name = "SkillModCategory")
    private String skillModCategory;

    @Column(name = "SkillModEffects_ARRAY")
    private String skillModEffectsArray;

    @Column(name = "SkillModPartQuality")
    private Short skillModPartQuality;

    @Column(name = "Template")
    private boolean template;

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkillModCategory() {
        return skillModCategory;
    }

    public void setSkillModCategory(String skillModCategory) {
        this.skillModCategory = skillModCategory;
    }

    public String getSkillModEffectsArray() {
        return skillModEffectsArray;
    }

    public void setSkillModEffectsArray(String skillModEffectsArray) {
        this.skillModEffectsArray = skillModEffectsArray;
    }

    public Short getSkillModPartQuality() {
        return skillModPartQuality;
    }

    public void setSkillModPartQuality(Short skillModPartQuality) {
        this.skillModPartQuality = skillModPartQuality;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }
}
