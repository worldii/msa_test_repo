package com.example.apigatewayservice.service;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtParser {

    public static final String BEARER = "Bearer";

    public String extractTokenFromHeader(final ServerHttpRequest servletRequest) {
        if (servletRequest.getHeaders().containsKey(AUTHORIZATION)) {
            final String bearerToken = servletRequest.getHeaders().getFirst(AUTHORIZATION);

            nullAndEmptyCheck(bearerToken);

            if (bearerToken.startsWith(BEARER)) {
                return bearerToken.substring(BEARER.length() + 1);
            }
        }
        throw new IllegalArgumentException("헤더에 토큰이 존재하지 않습니다.");
    }

    private void nullAndEmptyCheck(final String bearerToken) {
        if (bearerToken == null || bearerToken.isEmpty() || bearerToken.isBlank()) {
            throw new IllegalArgumentException("헤더에 토큰이 존재하지 않습니다.");
        }
    }
}
