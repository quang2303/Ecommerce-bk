package com.ecommerce.be.ExceptionHandler;

public class InvalidVerificationCodeException extends RuntimeException {
    public InvalidVerificationCodeException() {
        super("Invalid email verification code");
    }
}
