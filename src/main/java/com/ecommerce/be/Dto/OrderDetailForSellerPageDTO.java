package com.ecommerce.be.Dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class OrderDetailForSellerPageDTO {

    @Getter
    @AllArgsConstructor
    class Item {
        Integer productId;
        String name;
        Integer quantity;
        Integer price;
    }

    String orderId;
    Integer shipPrice;
    Integer discountPrice;
    Integer totalPrice;
    Integer totalQuantity;
    Boolean hasGift;
    Boolean isReturn;
    String buyerName;
    String buyerUsername;
    String buyerNumPhone;
    String buyerEmail;
    String buyerAddress;
    String buyerAvatar;
    List<Item> items = new ArrayList<>();

    public OrderDetailForSellerPageDTO(OrderDetailSellerQueryResult order) {
        this.orderId = order.getOrderId();
        this.shipPrice = order.getShipPrice();
        this.discountPrice = order.getDiscountPrice();
        this.totalPrice = order.getTotalPrice();
        this.totalQuantity = order.getProductQuantity();
        this.hasGift = order.getHasGift();
        this.isReturn = order.getIsReturn();
        this.buyerName = order.getBuyerName();
        this.buyerUsername = order.getBuyerUsername();
        this.buyerNumPhone = order.getBuyerNumPhone();
        this.buyerEmail = order.getBuyerEmail();
        this.buyerAddress = order.getBuyerAddress();
        this.buyerAvatar = order.getBuyerAvatar();
        this.items.add(new Item(order.productId, order.productName, order.productQuantity, order.productPrice));
    }

    public void addItem(OrderDetailSellerQueryResult order) {
        this.items.add(new Item(order.productId, order.productName, order.productQuantity, order.productPrice));
        this.totalQuantity += order.productQuantity;
    }
}
