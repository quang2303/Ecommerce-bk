package com.ecommerce.be.Util;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OtpUtil {
    public String generateOtp() {
        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        String otpString = Integer.toString(randomNumber);
    
        while (otpString.length() < 6) {
          otpString = "0" + otpString;
        }
        return otpString;
    }
}
