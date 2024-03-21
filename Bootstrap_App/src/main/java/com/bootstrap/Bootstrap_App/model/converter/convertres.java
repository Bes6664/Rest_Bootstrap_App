package com.bootstrap.Bootstrap_App.model.converter;

import org.hibernate.SessionFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class convertres implements AttributeConverter {
    @Override
    public Object convertToDatabaseColumn(Object attribute) {
        return null;
    }

    @Override
    public Object convertToEntityAttribute(Object dbData) {
        return null;
    }
}
