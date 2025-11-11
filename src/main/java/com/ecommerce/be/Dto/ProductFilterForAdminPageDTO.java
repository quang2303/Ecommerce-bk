package com.ecommerce.be.Dto;

import java.time.LocalDate;


public record ProductFilterForAdminPageDTO(
        String product,
        String seller,
        String collab,
        Integer startPrice,
        Integer endPrice,
        LocalDate startDate,
        LocalDate endDate,
        String orders,
        Integer offset,
        Integer page) {
}
