package com.ecommerce.be.Service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import com.ecommerce.be.Dto.LoginRequestDTO;
import com.ecommerce.be.Dto.RegisterRequestDTO;
import com.ecommerce.be.Entity.User;
import com.ecommerce.be.Entity.UserPrincipal;
import com.ecommerce.be.ExceptionHandler.UserNameExistsException;
import com.ecommerce.be.Repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    private UserRepository userRepo;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder encoder;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    AuthService(UserRepository userRepo, AuthenticationManager authenticationManager, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
    }

    public void registerUser(RegisterRequestDTO registerRequest) {
        if (userRepo.existsById(registerRequest.username())) {
            throw new UserNameExistsException();
        }
        String encryptedPassword = encoder.encode(registerRequest.password());
        registerRequest.setPassword(encryptedPassword);
        User user = new User(registerRequest);
        userRepo.save(user);
    }

    public String verify(LoginRequestDTO loginRequest, HttpServletRequest request,
            HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        // Create a new context
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        // Update SecurityContextHolder and Strategy
        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);

        // Get role of user
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) user.getAuthorities();
        String role = authorities.get(0).getAuthority();
        return role;
    }

    public User getUserByUsername(String username) {
        return userRepo.findById(username).orElse(null);
    }

}
