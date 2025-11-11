package com.ecommerce.be.Entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class VnPayPaymentInfor implements Serializable {
    private String buyerName;
    private String numPhone;
    private String nation;
    private String address;
    private String vnp_TxnRefCode;
    private Integer totalPrice;
}
