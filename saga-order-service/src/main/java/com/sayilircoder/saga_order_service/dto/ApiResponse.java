package com.sayilircoder.saga_order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class ApiResponse<T> {
    private String result;
    private String message;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }
}
