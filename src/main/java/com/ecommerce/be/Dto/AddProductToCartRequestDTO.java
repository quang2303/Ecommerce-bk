package com.ecommerce.be.Dto;

public record AddProductToCartRequestDTO(
    Integer productId,
    Integer quantity
) {
}
