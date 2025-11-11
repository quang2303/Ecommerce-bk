package com.ecommerce.be.Dto;

import java.time.ZonedDateTime;

public record UserInformationDTO(
        String username,
        String name,
        String email,
        String address,
        String numPhone,
        String avatar,
        String role,
        String nation,
        Character gender,
        ZonedDateTime lockUntil,
        ZonedDateTime createdAt) {
}
