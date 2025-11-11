package com.ecommerce.be.Converter;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import com.ecommerce.be.Constant.OrderStatus;

import jakarta.persistence.AttributeConverter;

@Component
@jakarta.persistence.Converter
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String>, Converter<String, OrderStatus> {
    @Override
    public String convertToDatabaseColumn(OrderStatus status) {
        if (status == null) {
            return null;
        }
        return status.toString();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String dbData) {
        return OrderStatus.getEnumConstantFromString(dbData);
    }

    @Override
    public OrderStatus convert(String value) {
        return OrderStatus.getEnumConstantFromString(value);
    }
}
