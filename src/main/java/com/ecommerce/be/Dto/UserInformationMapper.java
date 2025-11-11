package com.ecommerce.be.Dto;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.ecommerce.be.Entity.User;

@Service
public class UserInformationMapper implements Function<User, UserInformationDTO> {
    @Override
    public UserInformationDTO apply(User user) {
        return new UserInformationDTO(user.getUsername(), user.getName(), user.getEmail(), user.getAddress(),
                user.getNumPhone(), user.getAvatar(), user.getRole(), user.getNation(), user.getGender(),
                user.getLockUntil(), user.getCreatedAt());
    }
}