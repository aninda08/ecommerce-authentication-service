package com.ecommerce.ecommerce_authentication_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce_authentication_service.config.UserInfoUserDetails;
import com.ecommerce.ecommerce_authentication_service.entity.UserInfo;
import com.ecommerce.ecommerce_authentication_service.repository.UserInfoRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);

        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }

}
