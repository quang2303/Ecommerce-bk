package com.ecommerce.be.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

import com.ecommerce.be.Constant.OrderStatus;
import com.ecommerce.be.Converter.OrderStatusConverter;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * When customer make an order when enter "Payment" in Cart page, the order will be split into smaller orders.
 * The original order is split by seller to create these smaller orders.
 * They all have the same commonId to refer later. 
 */

@Entity
@Getter
@Setter
@Builder
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(length = 10)
    private String orderId;

    @Column
    private String seller;

    @Column(insertable = false, updatable = false)
    private ZonedDateTime createAt;

    @Column
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;

    @Column
    private String paymentMethod;

    @Column
    @Schema(description = "The order contains gift-type product or not")
    private Boolean hasGift;

    @Column
    private Integer totalPrice;

    @Column
    @Schema(description = "Refer to username column of user table")
    private String buyer;

    @Column
    @Schema(description = "Refer to name column of user table")
    private String buyerName;

    @Column
    @Builder.Default
    private Integer shipPrice = 0;

    @Column
    @Schema(description = "The amount of money discounted")
    private Integer discountPrice;

    @Column
    private Integer discountId;

    @Column(length = 50)
    private String bankCode;

    @Column(length = 12, nullable = false)
    private String commonId;

    @Column(length = 50)
    private String nation;

    @Column(length = 255)
    private String address;

    @Column(length = 20)
    private String numPhone;

    @Column
    private Boolean isReturn;
}
