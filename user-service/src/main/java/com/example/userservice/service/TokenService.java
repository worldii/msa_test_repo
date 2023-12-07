package com.example.userservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.sql.Date;
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
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setSubject(userId)
            .compact();
    }
}
