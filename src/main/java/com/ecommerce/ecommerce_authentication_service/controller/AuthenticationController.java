package com.ecommerce.ecommerce_authentication_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce_authentication_service.dto.AuthRequest;
import com.ecommerce.ecommerce_authentication_service.entity.UserInfo;
import com.ecommerce.ecommerce_authentication_service.response.SuccessResponse;
import com.ecommerce.ecommerce_authentication_service.service.JwtService;
import com.ecommerce.ecommerce_authentication_service.service.UserInfoService;

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
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class AuthenticationController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@RequestBody UserInfo userInfo) {
        return userInfoService.register(userInfo);
    }
    
    @GetMapping("/token")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String generateToken() {
        return "Generate Token";
    }

    @GetMapping("/validatetoken")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String validateToken() {
        return "Validate Token";
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            System.out.println("Generating token");
            return jwtService.generateToken(authRequest.getUsername());
        }
        else {
            throw new UsernameNotFoundException("Invalid user request");
        }
        
    }
}
