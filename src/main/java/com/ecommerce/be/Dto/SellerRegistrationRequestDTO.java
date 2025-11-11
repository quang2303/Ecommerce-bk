package com.ecommerce.be.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SellerRegistrationRequestDTO {
    @Schema(example = "Cửa hàng của Saul")
    @Size(min = 6, max = 20, message = "Shop name must be between 3 and 50 characters")
    private String shopName;

    @Schema(description = "The name of the seller", example = "Saul Goodman")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Schema(description = "The email of the seller and will receive the verification email", example = "SaulGoodman@gmail.com")
    @Email(message = "Email is not valid")
    private String email;

    @Schema(example = "TP Hồ Chí Minh")
    @NotBlank(message = "Address cannot be blank")
    private String address;

    @Schema(example = "0123456789")
    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 10, max = 11, message = "Phone number only have 10 or 11 digits")
    private String numPhone;

    @Schema(example = "Vietnam")
    @NotBlank(message = "Country cannot be blank")
    private String nation;

    @Schema(description = "Email verification code" , example = "i21k12na1")
    @NotBlank(message = "Email verification code cannot be blank")
    private String emailCode;
}
