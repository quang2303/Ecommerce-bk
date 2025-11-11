package com.ecommerce.be.Converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ecommerce.be.Constant.ProductStatus;

import jakarta.persistence.AttributeConverter;

/**
 * Note that:
 * - AttributeConverter: conversion between database and server.
 * - Converter: general-purpose conversion, such as converting request parameters to method arguments.
 */
@Component
@jakarta.persistence.Converter()
public class ProductStatusConverter implements Converter<String, ProductStatus>, AttributeConverter<ProductStatus, String> {

    @Override
    public String convertToDatabaseColumn(ProductStatus status) {
        if (status == null) {
            return null;
        }
        return status.toString();
    }

    @Override
    public ProductStatus convertToEntityAttribute(String dbData) {
        return ProductStatus.getEnumConstantFromString(dbData);
    }

    @Override
    public ProductStatus convert(String value) {
        return ProductStatus.getEnumConstantFromString(value);
    }
}
