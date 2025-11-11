package com.ecommerce.be.Service;

import org.springframework.stereotype.Service;

import com.ecommerce.be.Configuration.PaymentConfig;
import com.ecommerce.be.Constant.OrderStatus;
import com.ecommerce.be.Dto.CartProductDTO;
import com.ecommerce.be.Dto.CreatePaymentRequestDTO;
import com.ecommerce.be.Dto.OrderForCheckoutPageDTO;
import com.ecommerce.be.Dto.PaymentResultDTO;
import com.ecommerce.be.Entity.Order;
import com.ecommerce.be.Repository.OrderRepository;
import com.ecommerce.be.Util.PaymentUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Service
public class PaymentService {

    private CartService cartService;
    private PaymentConfig paymentConfig;
    private PaymentUtil paymentUtil;
    private OrderRepository orderRepository;

    public PaymentService(CartService cartService, PaymentConfig paymentConfig, PaymentUtil paymentUtil,
            OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.paymentConfig = paymentConfig;
        this.cartService = cartService;
        this.paymentUtil = paymentUtil;
    }

    public List<OrderForCheckoutPageDTO> checkoutOrder(String username) {
        // Map products into respective seller
        Map<String, List<CartProductDTO>> sellerProductsMap = cartService.getCartProductsToSellerMap(username);

        // Convert the map into a list of OrderForCheckoutPageDTO
        List<OrderForCheckoutPageDTO> orders = new ArrayList<>();

        for (Map.Entry<String, List<CartProductDTO>> entry : sellerProductsMap.entrySet()) {
            List<CartProductDTO> products = entry.getValue();
            OrderForCheckoutPageDTO order = new OrderForCheckoutPageDTO(products);
            orders.add(order);
        }

        return orders;
    }

    /**
     * Create payment url including:
     * - vnp_PayUrl
     * - List of parameters specified in
     * https://sandbox.vnpayment.vn/apis/docs/thanh-toan-pay/pay.html#danh-s%C3%A1ch-tham-s%E1%BB%91
     */
    public String createPaymentUrl(CreatePaymentRequestDTO paymentRequestDTO, String txnRefCode, String username,
            HttpServletRequest request) {
        Map<String, String> vnpParamsMap = paymentConfig.getVNPayConfig(txnRefCode, username,
                cartService.getTotalPrice(username),
                paymentUtil.getIpAddress(request));

        // build query url
        String queryUrl = paymentUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = paymentUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = paymentUtil.hmacSHA512(paymentConfig.getVnp_SecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = paymentConfig.getVnp_PayUrl() + "?" + queryUrl;

        return paymentUrl;
    }

    public String getRedirectUrl(String vnPayResponseCode) {
        String redirectUrl = null;
        if (vnPayResponseCode != null && vnPayResponseCode.equals("00")) {
            redirectUrl = paymentConfig.getSuccessPaymentRedirectUrl();
        } else {
            redirectUrl = paymentConfig.getFailPaymentRedirectUrl();
        }

        return redirectUrl;
    }

    // Verify checksum VNPay
    private Boolean isChecksumValid(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            try {
                String fieldName = URLEncoder.encode((String) params.nextElement(),
                        StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName),
                        StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }

        String signValue = paymentUtil.hashAllFields(fields, paymentConfig.getVnp_SecretKey());
        return signValue.equals(vnp_SecureHash);
    }

    @Transactional
    public PaymentResultDTO handlePaymentResultFromVnPay(HttpServletRequest request) {
        try {
            if (!isChecksumValid(request)) {
                System.out.println("Invalid checksum");
                return new PaymentResultDTO("97", "Invalid Checksum");
            }

            List<Order> orders = orderRepository.findByCommonId(request.getParameter("vnp_TxnRef"));

            // vnp_TxnRef doesn't exists in database
            if (orders.size() == 0) {
                return new PaymentResultDTO("01", "Order not Found");
            }
            // vnp_Amount isn't valid (Check vnp_Amount VNPAY returns compared to the
            // total price in database).
            Integer totalPrice = orders.stream().mapToInt(order -> order.getTotalPrice()).sum();
            Integer vnp_Amount = Integer.parseInt(request.getParameter("vnp_Amount")) / 100;
            if (!totalPrice.equals(vnp_Amount)) {
                return new PaymentResultDTO("04", "Invalid Amount");
            }
            // Check order status in database - If one order in orders have OrderStatus
            // isn't WAITING then it's error
            if (orders.stream().anyMatch(order -> !order.getStatus().equals(OrderStatus.WAITING))) {
                return new PaymentResultDTO("02", "Order already confirmed");
            }

            // Update order status
            OrderStatus updatedStatus = OrderStatus.CANCELED;
            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                updatedStatus = OrderStatus.PROCESSING;
            }

            orderRepository.updateStatus(request.getParameter("vnp_TxnRef"), updatedStatus);

            String username = request.getParameter("vnp_OrderInfo");
            cartService.clearCart(username);

            return new PaymentResultDTO("00", "Confirm Success");
        } catch (Exception e) {
            e.printStackTrace();
            return new PaymentResultDTO("99", "Unknow error");
        }
    }
}
