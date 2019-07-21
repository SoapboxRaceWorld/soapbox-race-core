package com.soapboxrace.core.jpa.convert;

import com.soapboxrace.core.jpa.util.NewsArticleType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NewsArticleTypeConverter implements AttributeConverter<NewsArticleType, String> {
    @Override
    public String convertToDatabaseColumn(NewsArticleType attribute) {
        return attribute.name();
    }

    @Override
    public NewsArticleType convertToEntityAttribute(String dbData) {
        return NewsArticleType.valueOf(dbData);
    }
}