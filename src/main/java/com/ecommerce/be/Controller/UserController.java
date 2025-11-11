package com.ecommerce.be.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.be.Dto.SellerRegistrationRequestDTO;
import com.ecommerce.be.Service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import io.swagger.v3.oas.annotations.media.Content;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



   
    
    @Operation(summary = "Verify seller email", tags = { "User" }, responses = {
            @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content()),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Email", required = true, content = @Content(mediaType = "text/plain"))

    @PostMapping("user/seller-code")
    @Secured("buyer")
    public ResponseEntity<Void> verifySellerEmail(@Valid @Email @RequestBody String email) {
        userService.verifySellerEmail(email);
        return ResponseEntity.noContent().build();
    }




    
    @Operation(summary = "Change user from buyer to seller", tags = { "User" }, responses = {
            @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content()),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content())
    })

    @PatchMapping("user/seller")
    @Secured("buyer")
    public ResponseEntity<Void> registerSeller(
            @Valid @RequestBody SellerRegistrationRequestDTO sellerRegistrationRequestDTO, Principal principal,
            HttpServletRequest request) {
        userService.registerSeller(sellerRegistrationRequestDTO, principal.getName());
        try {
            request.logout();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.noContent().build();
    }

}
