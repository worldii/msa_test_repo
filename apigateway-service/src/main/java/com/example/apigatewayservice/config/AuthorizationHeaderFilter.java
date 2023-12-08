package com.example.apigatewayservice.config;

import com.example.apigatewayservice.service.JwtParser;
import com.example.apigatewayservice.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends
    AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final JwtParser jwtParser;
    private final TokenService tokenService;

    public AuthorizationHeaderFilter(JwtParser jwtParser, TokenService tokenService) {
        super(Config.class);
        this.jwtParser = jwtParser;
        this.tokenService = tokenService;
    }

    @Override
    public GatewayFilter apply(AuthorizationHeaderFilter.Config config) {
        log.info("apply token filter");
        return ((exchange, chain) -> {
            try {
                final ServerHttpRequest request = exchange.getRequest();
                String token = jwtParser.extractTokenFromHeader(request);
                log.info("token: {}", token);
                tokenService.validateToken(token);
                return chain.filter(exchange);

            } catch (Exception e) {
                return onError(exchange, e.getMessage(), HttpStatus.UNAUTHORIZED);
            }
        });

    }

    private Mono<Void> onError(
        final ServerWebExchange exchange,
        final String message,
        final HttpStatus httpStatus
    ) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(message);
        return response.setComplete();
    }

    public static class Config {

    }
}
