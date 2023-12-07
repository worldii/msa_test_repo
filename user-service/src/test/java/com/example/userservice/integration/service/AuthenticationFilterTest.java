package com.example.userservice.integration.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.userservice.config.AuthenticationFilter;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@DisplayName("AuthenticationFilter 테스트")
@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @InjectMocks
    private AuthenticationFilter authenticationFilter;
    @Mock
    private AuthenticationManager authenticationManager;


    @Test
    @DisplayName("attemptAuthentication 가 정상 작동한다.")
    void attemptAuthenticationWithSuccess() throws IOException {
        // given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        final String email = "test@example.com";
        final String password = "password123";
        final String json = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        final InputStream in = new ByteArrayInputStream(json.getBytes());
        final ServletInputStream inputStream = getMockingServletInputStream(in);

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            email, password
        );

        when(request.getInputStream()).thenReturn(inputStream);
        when(authenticationManager.authenticate(authentication)).thenReturn(authentication);

        // when && then
        assertThatCode(() -> authenticationFilter.attemptAuthentication(request, response))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("attemptAuthentication 가 예외를 던진다.")
    void attemptAuthenticationWithException() throws IOException {
        // given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final String email = "test@example.com";
        final String password = "password123";
        final String json = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        final InputStream in = new ByteArrayInputStream(json.getBytes());
        final ServletInputStream inputStream = getMockingServletInputStream(in);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            email, password
        );

        when(authenticationManager.authenticate(authentication)).thenThrow(RuntimeException.class);
        when(request.getInputStream()).thenReturn(inputStream);

        // when && then
        assertThatCode(() -> authenticationFilter.attemptAuthentication(request, response))
            .isInstanceOf(RuntimeException.class);
    }


    private ServletInputStream getMockingServletInputStream(final InputStream in) {
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return in.read();
            }
        };
    }
}
