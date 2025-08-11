package com.example.demo.dto.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
    private UserResponse userResponse;
    private long expiresIn;
    private String tokenType;
}
