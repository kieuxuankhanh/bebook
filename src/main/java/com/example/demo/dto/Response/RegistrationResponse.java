package com.example.demo.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponse {
    private Long userId;
    private String username;
    private String token;
    private String refreshToken;
    private long expiresIn;
    private String tokenType;
}
