package com.ecommerce.ecommerce_authentication_service.exception;

public class CustomErrorCode {
     private CustomErrorCode() {
        throw new IllegalStateException("CustomCode class");
    }

    //400 series
    public static final String GENERIC_REQUEST_VALIDATION_ERROR_CODE = "4001";

    //500 series
    public static final String GENERIC_INTERNAL_SERVER_ERROR_CODE = "5001";
    public static final String GENERIC_AUTH_REPOSITORY_EXCEPTION_CODE = "5002";
}
