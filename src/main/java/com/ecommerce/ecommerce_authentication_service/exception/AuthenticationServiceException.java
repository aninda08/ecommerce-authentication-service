package com.ecommerce.ecommerce_authentication_service.exception;

import lombok.Getter;

public class AuthenticationServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Getter
    protected final String code;

    public AuthenticationServiceException(String message, String code) {
        super(message);
        this.code = code;
    }
}
