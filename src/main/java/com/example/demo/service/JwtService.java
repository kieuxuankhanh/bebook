package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.lettuce.core.cluster.event.ClusterTopologyChangedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String JWT_SECRET = "XIUMOY1zMZppH8gTKyXtLu7PJjG5zbI5N8vq28HbQFwO4+Av619325j6k9F3rMOG4pKwH0DIYYmSDorCex882/I3fz+vT4QDoF7YphgGm1J6O34bJJUqi0ZUBJtOzWw8";

    private static final long ACCESS_TOKEN_VALIDITY = 1000*60*60*24;
    private static final long REFRESH_TOKEN_VALIDITY = 1000*60*60*24*7;
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims= extractClaimsJWT(token);
        return claimsResolver.apply(claims);
    }
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, ACCESS_TOKEN_VALIDITY);
    }
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh_token");
        return generateToken(new HashMap<>(), userDetails, REFRESH_TOKEN_VALIDITY);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails, long validity) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isRefreshTokenValid(String token) {
        Claims claims = extractClaimsJWT(token);
        return "refresh_token".equals(claims.get("type"));
    }

    private Claims extractClaimsJWT(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getExpiration() {
        return ACCESS_TOKEN_VALIDITY/1000;
    }
}
