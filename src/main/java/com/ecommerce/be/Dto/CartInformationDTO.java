package com.ecommerce.be.Dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Cart information send back to the client")
public class CartInformationDTO {
    Integer numberOfItems = 0;
    Integer totalPrice = 0;
    List<CartProductDTO> items = new ArrayList<>();
}
