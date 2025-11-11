package com.ecommerce.be.Constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductStatus {
    ACTIVE("Đang bán"),
    INACTIVE("Tạm ngưng"),
    LOCKING("Vi phạm"),
    REJECTED("Từ chối"),
    REVIEWING("Đang duyệt"),
    DELETED("Đã xóa");

    private final String valueInDB;

    ProductStatus(String valueInDB) {
        this.valueInDB = valueInDB;
    }

    @JsonValue
    public String toString() {
        return valueInDB;
    } 

    // For example: getEnumConstantFromString("Đang bán") will return ACTIVE.
    public static ProductStatus getEnumConstantFromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        for (ProductStatus status : ProductStatus.values()) {
            if (status.toString().equals(value)) {
                return status;
            }
        }

        throw new RuntimeException("Unknown product status value: " + value);
    }
}
