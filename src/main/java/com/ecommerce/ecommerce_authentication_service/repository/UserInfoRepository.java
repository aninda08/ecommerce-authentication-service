package com.ecommerce.ecommerce_authentication_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce_authentication_service.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
    public Optional<UserInfo> findByName(String username);
}
