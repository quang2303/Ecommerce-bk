package com.ecommerce.be.Dto;

import java.util.List;

import com.ecommerce.be.Constant.OrderStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class OrderForBuyerPageDTO {

    @Getter
    @AllArgsConstructor
    class Item {
        private String coverImage;
        private String name;
        private Integer quantity;
        private Integer price;
        private Integer discount;
    }

    private String orderId;
    private OrderStatus status;
    private Integer totalPrice;
    private Integer shipPrice;
    private Integer discountPrice;
    private ZonedDateTime createAt;
    private String paymentMethod;
    private String seller;
    private String sellerName;
    private String sellerAvatar;
    private List<Item> items = new ArrayList<>();

    public OrderForBuyerPageDTO(OrderBuyerQueryResult order) {
        this.orderId = order.getOrderId();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.shipPrice = order.getShipPrice();
        this.discountPrice = order.getDiscountPrice();
        this.createAt = order.getCreateAt();
        this.paymentMethod = order.getPaymentMethod();
        this.seller = order.getSeller();
        this.sellerName = order.getSellerName();
        this.sellerAvatar = order.getSellerAvatar();
        this.items.add(new Item(order.getCoverImage(), order.getName(), order.getQuantity(), order.getPrice(), order.getDiscount()));
    }

    public void addItem(OrderBuyerQueryResult order) {
        this.items.add(new Item(order.getCoverImage(), order.getName(), order.getQuantity(), order.getPrice(), order.getDiscount()));
    }
}
