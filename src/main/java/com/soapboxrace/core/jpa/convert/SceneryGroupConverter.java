package com.soapboxrace.core.jpa.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class SceneryGroupConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return String.join(";", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.asList(dbData.split(";"));
    }
}
