package com.example.apigatewayservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.example.apigatewayservice.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

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

    @Test
    @DisplayName("토큰을 정상적으로 검증한다.")
    void validateTokenWithSuccess() {
        // given
        final String userId = "jongha";
        final String generatedToken = tokenService.generateToken(userId);

        // when && then
        assertThatCode(() -> tokenService.validateToken(generatedToken))
            .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("토큰이 존재하지 않으면 예외를 던진다.")
    @NullAndEmptySource
    void validateTokenWithFail(String token) {
        // given
        // when && then
        assertThatCode(() -> tokenService.validateToken(token))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("토큰이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("토큰이 만료되면 예외를 던진다.")
    void validateTokenWithFail2() {
        // given
        tokenService = new TokenService("secretKey", 0L);
        final String userId = "jongha";
        final String generatedToken = tokenService.generateToken(userId);

        // when && then
        assertThatCode(() -> tokenService.validateToken(generatedToken))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("토큰이 만료되었습니다.");
    }
}
