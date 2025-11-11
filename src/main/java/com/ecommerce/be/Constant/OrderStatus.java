package com.ecommerce.be.Constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    WAITING("Chờ xác nhận"),  // init khi người dùng tạo đơn hàng
    PROCESSING("Đang xử lý"),    // người mua đã thanh toán
    PREPARING("Chờ lấy hàng"),   // người bán đã xác nhận
    SHIPPING("Đang vận chuyển"), // người bán đã xác nhận
    DONE("Đã giao"),             // người mua đã xác nhận
    REQUIRE_RETURN("Yêu cầu trả hàng"), // người mua yêu cầu
    DENY_RETURN("Từ chối trả hàng"),    // người bán từ chối
    RETURNED("Đã trả hàng"),     // Người bán xác nhận, admin xác nhận hoàn tiền
    CANCELED("Đã hủy");          // Đang ở bước đang xử lý, người mua hủy thì hoàn tiền

    private final String valueInDB;

    OrderStatus(String valueInDB) {
        this.valueInDB = valueInDB;
    }
    
    @JsonValue
    public String toString() {
        return valueInDB;
    }

    public static OrderStatus getEnumConstantFromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        for (OrderStatus status : OrderStatus.values()) {
            if (status.toString().equals(value)) {
                return status;
            }
        }

        throw new RuntimeException("Unknown order status value: " + value);
    }
}
