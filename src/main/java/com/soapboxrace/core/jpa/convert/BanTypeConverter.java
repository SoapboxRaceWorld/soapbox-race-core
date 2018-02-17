package com.soapboxrace.core.jpa.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.soapboxrace.core.jpa.BanEntity;

@Converter
public class BanTypeConverter implements AttributeConverter<BanEntity.BanType, String> {
    @Override
    public String convertToDatabaseColumn(BanEntity.BanType attribute) {
        return attribute.name();
    }

    @Override
    public BanEntity.BanType convertToEntityAttribute(String dbData) {
        return BanEntity.BanType.valueOf(dbData);
    }
}
