package com.example.userservice.integration.service;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.userservice.service.JwtParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;

@DisplayName("JwtUtilService 토큰 파싱 테스트")
@ExtendWith(MockitoExtension.class)
class JwtParserServiceTest {

    @InjectMocks
    private JwtParser jwtParser;

    @Test
    @DisplayName("헤더 토큰을 정상적으로 추출한다.")
    void extractTokenFromHeaderSuccess() {
        // given
        final ServerHttpRequest servletRequest = mock(ServerHttpRequest.class);
        when(servletRequest.getHeaders()).thenReturn(mock(HttpHeaders.class));
        when(servletRequest.getHeaders().containsKey(AUTHORIZATION)).thenReturn(true);
        when(servletRequest.getHeaders().getFirst(AUTHORIZATION)).thenReturn("Bearer token");

        // when
        final String token = jwtParser.extractTokenFromHeader(servletRequest);

        // then
        assertThat(token).isEqualTo("token");
    }

    @Test
    @DisplayName("헤더에 토큰이 존재하지 않으면 예외를 던진다.")
    void extractTokenFromHeaderFail() {
        // given
        final ServerHttpRequest servletRequest = mock(ServerHttpRequest.class);
        when(servletRequest.getHeaders()).thenReturn(mock(HttpHeaders.class));
        when(servletRequest.getHeaders().containsKey(AUTHORIZATION)).thenReturn(false);

        // when && then
        assertThatCode(() -> jwtParser.extractTokenFromHeader(servletRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("헤더에 토큰이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("토큰가 BEARER가  아니면 예외를 던진다.")
    void extractTokenFromHeaderFail2() {
        // given
        final ServerHttpRequest servletRequest = mock(ServerHttpRequest.class);
        when(servletRequest.getHeaders()).thenReturn(mock(HttpHeaders.class));
        when(servletRequest.getHeaders().containsKey(AUTHORIZATION)).thenReturn(true);
        when(servletRequest.getHeaders().getFirst(AUTHORIZATION)).thenReturn("token");

        // when && then
        assertThatCode(() -> jwtParser.extractTokenFromHeader(servletRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("헤더에 토큰이 존재하지 않습니다.");
    }

    @ParameterizedTest
    @DisplayName("토큰이 null이거나 빈 문자열이면 예외를 던진다.")
    @NullAndEmptySource
    void extractTokenFromHeaderFail3(final String token) {
        // given
        final ServerHttpRequest servletRequest = mock(ServerHttpRequest.class);
        when(servletRequest.getHeaders()).thenReturn(mock(HttpHeaders.class));
        when(servletRequest.getHeaders().containsKey(AUTHORIZATION)).thenReturn(true);
        when(servletRequest.getHeaders().getFirst(AUTHORIZATION)).thenReturn(token);

        // when && then
        assertThatCode(() -> jwtParser.extractTokenFromHeader(servletRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("헤더에 토큰이 존재하지 않습니다.");
    }
}
