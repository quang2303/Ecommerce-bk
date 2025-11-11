package com.ecommerce.be.Dto;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Information of order at each shop is shown in checkout page")
public class OrderForCheckoutPageDTO {

    @Getter
    @Schema(description = "The products are bought at each shop")
    private class OrderItem {
        String productImage;
        String productName;
        @Schema(description = "The price after applying discount")
        Integer finalPrice;
        Integer quantity;

        public OrderItem(CartProductDTO product) {
            this.productImage = product.getCoverImage();
            this.productName = product.getName();
            this.finalPrice = product.getPrice();
            this.quantity = product.getQuantity();
        }
    }

    List<OrderItem> items;
    String seller;
    @Schema(description = "Total amount of money discounted")
    Integer discountPrice;
    Integer totalPrice;


    // TODO: Apply discount
    public OrderForCheckoutPageDTO(List<CartProductDTO> products) {
        this.seller = products.get(0).getSellerName();
        this.totalPrice = products.stream().mapToInt((product) -> product.getPrice() * product.getQuantity()).sum();
        this.discountPrice = 0;
        this.items = products.stream().map(OrderItem::new).collect(Collectors.toList());
    }
}
