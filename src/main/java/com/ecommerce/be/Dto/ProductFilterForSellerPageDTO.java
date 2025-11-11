package com.ecommerce.be.Dto;

import com.ecommerce.be.Constant.ProductStatus;


public record ProductFilterForSellerPageDTO(
        String searchTerm,
        ProductStatus status,
        Boolean isSoldOut,
        Integer page,
        Integer offset) {
}
