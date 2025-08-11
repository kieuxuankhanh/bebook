package com.example.demo.service;

import com.example.demo.dto.Request.AuthenticationRequest;
import com.example.demo.dto.Request.RegistrationRequest;
import com.example.demo.dto.Request.TokenRefreshRequest;
import com.example.demo.dto.Response.AuthenticationResponse;
import com.example.demo.dto.Response.RegistrationResponse;
import com.example.demo.dto.Response.TokenRefreshResponse;
import com.example.demo.dto.Response.UserResponse;
import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public RegistrationResponse register(@RequestBody RegistrationRequest request){
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Role.USER)
                .build();

        boolean created = userService.createUser(user);
        if (!created) {
            throw new RuntimeException("Failed to create user");
        }

        User savedUser = userService.searchUserByName(request.getUsername());
        if (savedUser == null) {
            throw new RuntimeException("User was not saved properly");
        }
        
        System.out.println("DEBUG: Found user with ID: " + savedUser.getId());
        System.out.println("DEBUG: User username: " + savedUser.getUsername());
        
        var userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        
        return RegistrationResponse.builder()
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .token(token)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpiration())
                .build();
    }

    public TokenRefreshResponse refreshToken(@RequestBody TokenRefreshRequest request){
         String refreshToken = request.getRefreshToken();
         try{
             String username = jwtService.extractUsername(refreshToken);
             UserDetails userDetails = userDetailsService.loadUserByUsername(username);
             if (jwtService.isTokenValid(refreshToken, userDetails) && jwtService.isRefreshTokenValid(refreshToken)) {
                 String newAccessToken = jwtService.generateAccessToken(userDetails);
                 return TokenRefreshResponse.builder()
                         .accessToken(newAccessToken)
                         .tokenType("Bearer")
                         .Success(true)
                         .build();
             }
             else {
                 return TokenRefreshResponse.builder().Success(false).build();
             }
         }catch (Exception e){
             return TokenRefreshResponse.builder().Success(false).build();
         }
    }

    public AuthenticationResponse login(@RequestBody AuthenticationRequest request){
        var user =  userService.searchUserByName(request.getUsername());
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        var userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String accessToken  = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
        return AuthenticationResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .userResponse(userResponse)
                .expiresIn(jwtService.getExpiration())
                .build();
    }
}
