package com.ecommerce.be.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class RegisterRequestDTO {

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50, message = "Username cannot be longer than 50 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 255, message = "Password cannot be longer than 255 characters")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50, message = "Email cannot be longer than 50 characters")
    @Email(message = "Email is not valid")
    private String email;

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String email() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}