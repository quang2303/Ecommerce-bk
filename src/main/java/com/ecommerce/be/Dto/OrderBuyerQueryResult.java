package com.ecommerce.be.Dto;

import java.time.ZonedDateTime;

import com.ecommerce.be.Constant.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Query result in database used for construct OrderForBuyerPageDTO")
public class OrderBuyerQueryResult {
    // Order Information
    private String orderId;
    private OrderStatus status;
    private Integer totalPrice;
    private Integer shipPrice;
    private Integer discountPrice;
    private ZonedDateTime createAt;
    private String paymentMethod;
    // Seller Information
    private String seller;
    private String sellerName;
    private String sellerAvatar;
    private String coverImage;
    // Product Information
    private String name;
    private Integer quantity;
    private Integer price;
    private Integer discount;
}
