package com.ecommerce.be.Controller;

import java.util.List;
import java.util.Map;
import java.security.Principal;

import com.ecommerce.be.Dto.CreatePaymentRequestDTO;
import com.ecommerce.be.Dto.OrderForCheckoutPageDTO;
import com.ecommerce.be.Dto.PaymentResultDTO;
import com.ecommerce.be.Service.OrderService;
import com.ecommerce.be.Service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {

    private PaymentService paymentService;
    private OrderService orderService;

    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }




    @Operation(summary = "Checkout order before procceed to payment", tags = { "Payment" }, responses = {
            @ApiResponse(responseCode = "200", content = {
                @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = OrderForCheckoutPageDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    @GetMapping("checkout")
    public ResponseEntity<List<OrderForCheckoutPageDTO>> checkoutOrder(Principal principal) {
        List<OrderForCheckoutPageDTO> orders = paymentService.checkoutOrder(principal.getName());
        return ResponseEntity.ok(orders);
    }




    @Operation(summary = "Create vnpay payment url", tags = { "Payment" }, responses = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "text/plain")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    @PostMapping()
    public String createPaymentUrl(@RequestBody CreatePaymentRequestDTO createPaymentRequestDTO,
            HttpServletRequest request, Principal principal) {
        String commonId = orderService.convertCartItemsToOrders(createPaymentRequestDTO, principal.getName());
        String vnpayUrl = paymentService.createPaymentUrl(createPaymentRequestDTO, commonId, principal.getName(), request);
        return vnpayUrl;   
    }




    @Operation(summary = "Return url which is called from vnpay", tags = { "Payment" }, responses = {
        @ApiResponse(responseCode = "303", description = "Redirect client to successful page or failure page"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    @GetMapping("vnpay_return")
    public RedirectView returnUrlCallbackHandler(@RequestParam Map<String, String> requestParams) {
        String responseCode = requestParams.get("vnp_ResponseCode");
        return new RedirectView(paymentService.getRedirectUrl(responseCode));
    }
    



    // TODO: Log payment result.
    @Operation(summary = "IPN URL which receive payment result from VNPay", tags = { "Payment" }, responses = {
        @ApiResponse(responseCode = "200", content = 
            {@Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResultDTO.class))}),
    })

    @GetMapping("vnpay_ipn")
    public ResponseEntity<PaymentResultDTO> ipnUrlCallbackHandler(HttpServletRequest request) {
        PaymentResultDTO response = paymentService.handlePaymentResultFromVnPay(request);
        return ResponseEntity.ok(response);
    }
}
