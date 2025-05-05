package com.ecommerce.ecommerce_authentication_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce_authentication_service.dto.AuthRequest;
import com.ecommerce.ecommerce_authentication_service.entity.UserInfo;
import com.ecommerce.ecommerce_authentication_service.response.SuccessResponse;
import com.ecommerce.ecommerce_authentication_service.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@RequestBody UserInfo userInfo) {
        return authService.register(userInfo);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return authService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {

        if(authService.validateToken(token))
            return "Valid token";
        
        return "Invalid token";
    }
    
    
}
