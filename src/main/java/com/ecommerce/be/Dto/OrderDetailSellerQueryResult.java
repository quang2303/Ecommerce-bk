package com.ecommerce.be.Dto;

import com.ecommerce.be.Constant.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Query result in database used for construct OrderDetailForSellerPageDTO")
public class OrderDetailSellerQueryResult {
    // Order Information
    String orderId;
    OrderStatus status;
    Integer totalPrice;
    Integer shipPrice;
    Integer discountPrice;
    Boolean hasGift;
    Boolean isReturn;
    // Buyer Information
    String buyerName;
    String buyerUsername;
    String buyerNumPhone;
    String buyerEmail;
    String buyerAddress;
    String buyerAvatar;
    // Product Information
    Integer productId;
    String productName;
    Integer productQuantity;
    Integer productPrice;
}
