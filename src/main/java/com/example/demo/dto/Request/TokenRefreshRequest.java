package com.example.demo.dto.Request;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
