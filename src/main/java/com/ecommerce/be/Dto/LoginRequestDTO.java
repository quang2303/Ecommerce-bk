package com.ecommerce.be.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(

        @Schema(example = "SaulGoofman") 
        @NotBlank(message = "Username cannot be blank") 
        @Size(max = 50, message = "Username cannot be longer than 50 characters") 
        String username,

        @Schema(example = "123456789") 
        @NotBlank(message = "Password cannot be blank") 
        @Size(max = 255, message = "Password cannot be longer than 255 characters") 
        String password

) {}
