package com.example.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final String secretKey;
    private final Long expireTime;

    public TokenService(
        @Value("${token.secret}") final String secretKey,
        @Value("${token.expiration_time}") final Long expireTime
    ) {
        this.secretKey = secretKey;
        this.expireTime = expireTime;
    }

    public String generateToken(final String userId) {
        return Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + expireTime))
            .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
            .setSubject(userId)
            .compact();
    }

    public void tokenValidate(final String token) {
        if (token == null || token.isEmpty() || token.isBlank()) {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
        try {
            final Claims body = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
            final String userId = body.getSubject();

            if (userId == null || userId.isEmpty() || userId.isBlank()) {
                throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
            }
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        }
    }
}
