package com.goods.shop.dto.response;

import com.goods.shop.exception.ApiException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class ApiResponseDTO<T> {
    public static final ApiResponseDTO<String> DEFAULT_OK = new ApiResponseDTO<>(ApiResponseCode.OK);
    public static final ApiResponseDTO<String> DEFAULT_UNAUTHORIZED = new ApiResponseDTO<>(ApiResponseCode.UNAUTHORIZED);

    private Integer status;
    private String message;
    private T data;
    
    private ApiResponseDTO(ApiResponseCode status) {
        this.bindStatus(status);
    }

    private ApiResponseDTO(ApiResponseCode status, T data) {
        this.bindStatus(status);
        this.data = data;
    }

    private ApiResponseDTO(ApiResponseCode code, String message, T data) {
        this.status = code.getStatus();
        this.message = message;
        this.data = data;
    }

    private ApiResponseDTO(ApiResponseCode code, ApiException e) {
        this.status = code.getStatus();
        this.message = e.getMessage();
    }

    private void bindStatus(ApiResponseCode code) {
        this.status = code.getStatus();
        this.message = code.getMessage();
    }
    
    public static <T> ApiResponseDTO<T> createOK() {
        return new ApiResponseDTO<>(ApiResponseCode.OK);
    }
    
    public static <T> ApiResponseDTO<T> createOK(T data) {
        return new ApiResponseDTO<>(ApiResponseCode.OK, data);
    }

    public static ApiResponseDTO<String> createException(ApiException e) {
        return new ApiResponseDTO<>(e.getStatus(), e);
    }

    public static ApiResponseDTO<String> createException(ApiResponseCode code, String message) {
        return new ApiResponseDTO<>(code, message, "");
    }

    public static <T> ApiResponseDTO<T> createException(ApiResponseCode code, T data) {
        return new ApiResponseDTO<>(code, data);
    }

}