package com.ecommerce.be.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentResultDTO(
    @JsonProperty("RspCode")
    String responseCode,
    @JsonProperty("Message")
    String message
) {
    
}
