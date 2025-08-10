package com.example.school.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class TokenService {

    
    @Value("${jwt.secret}")
    private String secretKey;
    private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
}

    @Autowired
    private JwtUtil jwtUtil; // or use your preferred signing mechanism

    public String generateStaffInviteToken(String email, Long primeAccountId) {
        // Optional: You could add expiration or role claims
        return jwtUtil.generateToken(email, "ROLE_INVITE", primeAccountId);
    }
    
    public String extractEmailFromToken(String token) {
        return jwtUtil.extractUsername(token); // jwtUtil should return the email from token
    }

    public boolean isTokenValid(String token) {
        return jwtUtil.validateToken(token); // Basic token validation
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey()) // must be SecretKey, not String
                .build()                       // build() must come before parsing
                .parseClaimsJws(token)
                .getBody();
    }
    
    
}