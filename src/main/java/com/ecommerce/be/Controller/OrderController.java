package com.ecommerce.be.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.be.Constant.OrderStatus;
import com.ecommerce.be.Dto.OrderDetailForSellerPageDTO;
import com.ecommerce.be.Dto.OrderForBuyerPageDTO;
import com.ecommerce.be.Dto.OrderForSellerPageDTO;
import com.ecommerce.be.Service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;




@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    


    @Operation(summary = "Get orders of a buyer", tags = { "Order" }, responses = {
        @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = OrderForBuyerPageDTO.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    @GetMapping("buyer")
    public ResponseEntity<List<OrderForBuyerPageDTO>> getBuyerOrders(@RequestParam(required = false) OrderStatus status, Principal principal) {
        return ResponseEntity.ok(orderService.getBuyerOrders(principal.getName(), status));
    }
    




    @Operation(summary = "Get orders of a seller", tags = { "Order" }, responses = {
        @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = OrderForSellerPageDTO.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    @GetMapping("seller")
    @Secured("seller")
    public ResponseEntity<List<OrderForSellerPageDTO>> getSellerOrders(@Schema(defaultValue = "1") @RequestParam(required = false) Integer page, 
                                @Schema(defaultValue = "10") @RequestParam(required = false) Integer offset, Principal principal) {
        return ResponseEntity.ok(orderService.getSellerOrders(principal.getName(), PageRequest.of(page - 1, offset)));
    }




    @Operation(summary = "Get detail of an order for seller page", tags = { "Order" }, responses = {
        @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderForSellerPageDTO.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    @GetMapping("seller/{orderId}")
    @Secured("seller")
    public ResponseEntity<OrderDetailForSellerPageDTO> getOrderDetailForSeller(@PathVariable String orderId, Principal principal) {
        return ResponseEntity.ok(orderService.getOrderDetailForSeller(principal.getName(), orderId));
    }
}
