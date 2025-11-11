package com.ecommerce.be.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.be.Dto.LoginRequestDTO;
import com.ecommerce.be.Dto.RegisterRequestDTO;
import com.ecommerce.be.Dto.UserProfileDTO;
import com.ecommerce.be.Entity.User;
import com.ecommerce.be.ExceptionHandler.BadRequestException;
import com.ecommerce.be.Service.CartService;
import com.ecommerce.be.Service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RestController
public class AuthController {

    private AuthService authService;
    private CartService cartService;

    public AuthController(AuthService authService, CartService cartService) {
        this.authService = authService;
        this.cartService = cartService;
    }




    @Operation(summary = "Register a new user", description = "Register a new user", tags = { "Authentication" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully and send back success message", content = {
                    @Content(mediaType = "text/plain", examples = {
                            @ExampleObject(value = "User registered successfully!") })
            }),
            @ApiResponse(responseCode = "400", description = "Request validation error", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Internal server error or user already exists in system", content = @Content()),
    })

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequestDTO user, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }

        authService.registerUser(user);
        return ResponseEntity.ok("Registered successfully!");
    }




    @Operation(summary = "Log in a user", tags = { "Authentication" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign in successfully and return User role either 'buyer', 'seller', 'admin' or 'collab'", content = {
                    @Content(mediaType = "text/plain") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error or Bad Credentials Error", content = @Content()),
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User credentials", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequestDTO.class)))

    @PostMapping("login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequestDTO user, HttpServletRequest request,
            HttpServletResponse response) {
        String role = authService.verify(user, request, response);
        return ResponseEntity.ok(role);
    }




    @Operation(summary = "Get user profile", description = "Get profile of user including information including not only user information in User but also user cart information.", tags = {
            "Authentication" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content())
    })

    @GetMapping("profile")
    public ResponseEntity<UserProfileDTO> getUserProfile(Principal principal) throws Exception {
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = principal.getName();
        User user = authService.getUserByUsername(username);
        Integer totalPrice = cartService.getTotalPrice(username);           // Total price of items in cart
        Integer cartItems = cartService.getTotalItems(username);            // Total number of items in cart
        return ResponseEntity.ok(new UserProfileDTO(user, totalPrice, cartItems));
    }



    @Operation(summary = "Log out a user", tags = { "Authentication" }, responses = {
        @ApiResponse(responseCode = "200", description = "Sign out successfully and return success message")
    })

    /**
     * Spring have a default logout handler (POST /logout) and 
     * it will redirect to user to the logout successful path so we need to handle it.
     */
    @GetMapping("logout-success")
    public ResponseEntity<String> logOut() {
        return ResponseEntity.ok("Logged out successfully!");
    }



    // TODO: Change password of user.
    // @PostMapping("password")
    // public void changePassword() {

    // }

}
