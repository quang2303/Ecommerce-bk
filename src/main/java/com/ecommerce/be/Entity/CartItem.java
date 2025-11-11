package com.ecommerce.be.Entity;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Cart item stored in Redis")
public class CartItem implements Serializable {
    private String note;
    private Integer quantity;
}
