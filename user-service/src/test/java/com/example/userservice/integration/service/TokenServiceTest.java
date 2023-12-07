package com.example.userservice.integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.userservice.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TokenService 단위 테스트")
class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void init() {
        tokenService = new TokenService("secretKey", 1000L);
    }

    @Test
    @DisplayName("토큰을 정상적으로 생성한다.")
    void createTokenWithSuccess() {
        // given
        final String userId = "jongha";
        // when
        final String generatedToken = tokenService.generateToken(userId);
        // then
        assertThat(generatedToken).isNotNull();
    }
}
