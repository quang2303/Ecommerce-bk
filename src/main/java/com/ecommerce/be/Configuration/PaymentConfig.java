package com.ecommerce.be.Configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaymentConfig {
    @Getter
    @Value("${vnpay.url}")
    private String vnp_PayUrl;
    @Value("${vnpay.return-url}")
    private String vnp_ReturnUrl;
    @Value("${vnpay.tmn-code}")
    private String vnp_TmnCode ;
    @Getter
    @Value("${vnpay.hash-secret}")
    private String vnp_SecretKey;
    @Value("${vnpay.version}")
    private String vnp_Version;
    @Value("${vnpay.command}")
    private String vnp_Command;
    @Value("${vnpay.order-type}")
    private String vnp_OrderType;

    @Getter
    @Value("${payment.url.success-payment-redirect}")
    private String successPaymentRedirectUrl;

    @Getter
    @Value("${payment.url.fail-payment-redirect}")
    private String failPaymentRedirectUrl;

    public Map<String, String> getVNPayConfig(String txnRefCode, String username, Integer amount, String ipAddress) {
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Amount", Integer.toString(amount * 100));
        vnpParamsMap.put("vnp_IpAddr", ipAddress);
        vnpParamsMap.put("vnp_Version", this.vnp_Version);
        vnpParamsMap.put("vnp_Command", this.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef",  txnRefCode);
        vnpParamsMap.put("vnp_OrderInfo", username);
        vnpParamsMap.put("vnp_OrderType", this.vnp_OrderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl);

        // Set create date
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);

        // Set expire date
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);

        return vnpParamsMap;
    }
}