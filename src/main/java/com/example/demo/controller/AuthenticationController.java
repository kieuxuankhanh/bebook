package com.example.demo.controller;

import com.example.demo.dto.Request.AuthenticationRequest;
import com.example.demo.dto.Request.RegistrationRequest;
import com.example.demo.dto.Request.TokenRefreshRequest;
import com.example.demo.dto.Response.AuthenticationResponse;
import com.example.demo.dto.Response.RegistrationResponse;
import com.example.demo.dto.Response.TokenRefreshResponse;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.UserService;
import com.example.demo.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegistrationRequest request){
        if (userService.getUserByEmail(request.getEmail()) != null){
            throw new RuntimeException("Email already exists");
        }
        if (userService.searchUserByName(request.getUsername()) != null){
            throw new RuntimeException("Username already exists");
        }
        try{
           RegistrationResponse response = authenticationService.register(request);
           return ResponseBuilder.<RegistrationResponse>create()
                   .status(HttpStatus.OK)
                   .body(response)
                   .contentType(MediaType.APPLICATION_JSON)
                   .build();
        }catch (Exception e){
            return ResponseBuilder.<String>create()
                    .status(HttpStatus.BAD_REQUEST)
                    .error("Dang ki that bai" + e.getMessage())
                    .build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request){
        try{
            AuthenticationResponse response = authenticationService.login(request);
            return ResponseBuilder.<AuthenticationResponse>create()
                    .status(HttpStatus.OK)
                    .body(response)
                    .message("Dang nhap thanh cong")
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }catch (Exception e){
            return ResponseBuilder.<String>create()
                    .status(HttpStatus.BAD_REQUEST)
                    .error("Dang nhap that bai"  + e.getMessage())
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(@RequestBody TokenRefreshRequest request){
        TokenRefreshResponse response = authenticationService.refreshToken(request);
        if (response.isSuccess()){
            return ResponseBuilder.<TokenRefreshResponse>create()
                    .status(HttpStatus.OK)
                    .body(response)
                    .message("lam moi token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }
        else {
            return ResponseBuilder.<String>create()
                    .status(HttpStatus.BAD_REQUEST)
                    .error("lam moi token that bai" + response.getError())
                    .contentType(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
