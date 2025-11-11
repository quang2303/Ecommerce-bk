package com.ecommerce.be.Service;

import java.util.List;
import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.be.Dto.SellerRegistrationRequestDTO;
import com.ecommerce.be.Entity.User;
import com.ecommerce.be.ExceptionHandler.InvalidVerificationCodeException;
import com.ecommerce.be.Repository.UserRepository;
import com.ecommerce.be.Util.EmailUtil;
import com.ecommerce.be.Util.OtpUtil;

@Service
public class UserService {

    private String OTP_EMAIL_PREFIX = "email:otp:";

    private OtpUtil otpUtil;
    private EmailUtil emailUtil;
    private RedisTemplate<String, String> redisOperations;
    private SetOperations<String, String> setOperations;
    private UserRepository userRepo;

    UserService(UserRepository userRepo, OtpUtil otpUtil, EmailUtil emailUtil,
            RedisTemplate<String, String> redisOperations) {
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;
        this.userRepo = userRepo;
        this.redisOperations = redisOperations;
        this.setOperations = redisOperations.opsForSet();
    }

    // TODO: invalidate the existing session
    @Transactional
    public void registerSeller(SellerRegistrationRequestDTO sellerRegistrationRequestDTO, String username) {
        String key = OTP_EMAIL_PREFIX + sellerRegistrationRequestDTO.getEmail();
        if (setOperations.isMember(key, sellerRegistrationRequestDTO.getEmailCode())) {
            userRepo.updateSellerInfo(
                    sellerRegistrationRequestDTO.getShopName(),
                    sellerRegistrationRequestDTO.getName(),
                    sellerRegistrationRequestDTO.getEmail(),
                    sellerRegistrationRequestDTO.getAddress(),
                    sellerRegistrationRequestDTO.getNumPhone(),
                    sellerRegistrationRequestDTO.getNation(),
                    username);

            redisOperations.delete(key);
        } else {
            throw new InvalidVerificationCodeException();
        }
    }

    // Before changing user role to seller, we need verify emails they provide.
    public void verifySellerEmail(String email) {
        String otp = otpUtil.generateOtp();

        /**
         * Note that:
         * - Each time user send a request and there already exist an OTP for them in
         * redis, expiration time will be reset to 1 hour.
         * - One user can have many OTPs in redis.
         * Currently, we allow this behavoir but maybe in future we will change it.
         */
        String key = OTP_EMAIL_PREFIX + email;
        setOperations.add(key, otp);
        redisOperations.expire(key, Duration.ofHours(1));

        emailUtil.sendVerificationEmailToSeller(email, otp);
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    // Use getReferenceById create a proxy object instead of fetching from database.
    // More in here: https://vladmihalcea.com/spring-data-jpa-findbyid/
    public User getReferenceByUsername(String username) {
        return userRepo.getReferenceById(username);
    }
}
