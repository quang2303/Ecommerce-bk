package com.ecommerce.be.Dto;

import java.time.ZonedDateTime;

public record ProductForAdminPageDTO(
        Integer productId,
        String coverImage,
        String name,
        String seller,
        Integer price,
        ZonedDateTime approvedAt) {
}
