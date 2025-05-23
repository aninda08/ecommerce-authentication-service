package com.ecommerce.ecommerce_authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce_authentication_service.entity.UserInfo;
import com.ecommerce.ecommerce_authentication_service.repository.UserInfoRepository;
import com.ecommerce.ecommerce_authentication_service.response.SuccessResponse;

@Service
public class AuthService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<SuccessResponse> register(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return new ResponseEntity<>(new SuccessResponse("New User registered successfully", "success"), HttpStatus.OK);
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public boolean validateToken(String username) {
        return jwtService.validateToken(username);
    }
}
