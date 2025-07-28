package com.blog.blognest.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {


    private final SecretKey secretKey = Keys.hmacShaKeyFor("YourSuperSecureJWTSecretKeyOfAtLeast32Chars!".getBytes());
    private final SecretKey refreshKey = Keys.hmacShaKeyFor("AnotherSuperSecureRefreshKeyAtLeast32Char!".getBytes());

    private final long accessTokenExpirationMs = 1000 * 60 * 15; // 15 minutes
    private final long refreshTokenExpirationMs = 1000 * 60 * 60 * 24 * 7; // 7 days

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmailFromAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractEmailFromRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(refreshKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        String email = extractEmailFromAccessToken(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token, secretKey);
    }

    public boolean isRefreshTokenValid(String token) {
        return !isTokenExpired(token, refreshKey);
    }

    private boolean isTokenExpired(String token, SecretKey key) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
