/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa.convert;

import com.soapboxrace.core.jpa.util.NewsArticleFilters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NewsArticleFilterConverter implements AttributeConverter<NewsArticleFilters, String> {
    @Override
    public String convertToDatabaseColumn(NewsArticleFilters attribute) {
        return attribute.name();
    }

    @Override
    public NewsArticleFilters convertToEntityAttribute(String dbData) {
        return NewsArticleFilters.valueOf(dbData);
    }
}