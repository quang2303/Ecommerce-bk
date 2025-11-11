package com.ecommerce.be.Controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.be.Dto.AddProductToCartRequestDTO;
import com.ecommerce.be.Dto.CartInformationDTO;
import com.ecommerce.be.Dto.UpdateCartRequestDTO;
import com.ecommerce.be.Service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Currently, only registered users can use cart.
 * Maybe in the future, we can allow guest users to use it too.
 */
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }




    @Operation(summary = "Get user cart", tags = { "Cart" }, responses = {
        @ApiResponse(responseCode = "200", description = "Successful operation", content = 
            @Content(mediaType = "application/json", schema = @Schema(type = "object", 
                description = "A map of product IDs (keys) and their quantities (values)",
                example = "{ \"101\": 2, \"102\": 3 }"))),
        @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @GetMapping()
    public ResponseEntity<CartInformationDTO> getCart(Principal principal) {
        return ResponseEntity.ok(cartService.getCart(principal.getName()));
    }





    @Operation(summary = "Get user cart", tags = { "Cart" }, responses = {
        @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content()),
        @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @PostMapping()
    public ResponseEntity<Void> addItemToCart(@RequestBody AddProductToCartRequestDTO item, Principal principal) {
        cartService.addProductToCart(principal.getName(), item.productId(), item.quantity());
        return ResponseEntity.noContent().build();
    }




    @Operation(summary = "Remove product from cart", tags = { "Cart" }, responses = {
        @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content()),
        @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Bad request - invalid product ID or product ID doesn't exist in cart", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())}
    )

    @DeleteMapping("{productId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Integer productId, Principal principal) {
        cartService.removeProductFromCart(principal.getName(), productId);
        return ResponseEntity.noContent().build();
    }




    @Operation(summary = "Update product on cart", tags = { "Cart" }, responses = {
        @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content()),
        @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Bad request - invalid product ID or product ID doesn't exist in cart", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())}
    )
    
    @PatchMapping("{productId}")
    public ResponseEntity<Void> updateItemOnCart(@PathVariable Integer productId, @RequestBody UpdateCartRequestDTO item, Principal principal) {
        cartService.updateProductOnCart(principal.getName(), productId, item.quantity(), item.note());
        return ResponseEntity.noContent().build();
    }
}
