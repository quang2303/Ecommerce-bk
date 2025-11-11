package com.ecommerce.be.Dto;

import com.ecommerce.be.Constant.ProductStatus;

public record ProductForSellerPageDTO(
        Integer productId,
        Integer price,
        String name,
        String seller,
        String coverImage,
        Integer quantity,
        ProductStatus status) {
}
