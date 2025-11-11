package com.ecommerce.be.Dto;

public record ProductForHomePageDTO(
        Integer productId,
        Integer price,
        String name,
        String seller,
        String coverImage,
        Integer numberOfStar,
        Integer numberOfRating,
        Integer discount) {
}
