package com.example.school.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;



import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
import java.util.Collections;;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;
    public String generateToken(String school, String role, Long primeAccountId) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
    
        return Jwts.builder()
                .setSubject(school)
                .claim("role", role)
                .claim("id", primeAccountId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key)
                .compact();
    }
    
                    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
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
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

   public Authentication getAuthentication(String token) {
    Claims claims = extractAllClaims(token);
    String username = claims.getSubject();
    String role = claims.get("role", String.class);

    // Convert role string to GrantedAuthority
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

    return new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
}
}
