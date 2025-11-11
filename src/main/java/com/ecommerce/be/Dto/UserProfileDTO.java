package com.ecommerce.be.Dto;

import java.time.ZonedDateTime;

import com.ecommerce.be.Entity.User;

public record UserProfileDTO(
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
        ZonedDateTime createdAt,
        Integer totalPrice,
        Integer cartItems) {
    
    public UserProfileDTO(User user, Integer totalPrice, Integer cartItems) {
        this(user.getUsername(), user.getName(), user.getEmail(), user.getAddress(), user.getNumPhone(), user.getAvatar(),
                user.getRole(), user.getNation(), user.getGender(), user.getLockUntil(), user.getCreatedAt(),
                totalPrice, cartItems); 
    }
}
