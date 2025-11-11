package com.ecommerce.be.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePaymentRequestDTO(
    @JsonProperty("name")
    String buyerName,
    String numPhone,
    String nation,
    String address,
    String paymentMethod
) {
}
