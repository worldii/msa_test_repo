package com.example.userservice.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import com.example.userservice.config.CustomAuthenticationManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("CustomAuthenticationManagerTest 통합 테스트")
@ExtendWith(MockitoExtension.class)
class CustomAuthenticationManagerTest {

    @InjectMocks
    private CustomAuthenticationManager customAuthenticationManager;

    @Mock
    private UserDetailsService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("authenticate() 메소드가 정상적으로 동작하는 경우")
    void authenticateTestSuccess() {
        // given
        final String email = "jongha@naver.com";
        final String password = "1234";
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            email, password
        );
        final UserDetails userDetails = User.builder()
            .username(email)
            .password(password)
            .build();
        when(userService.loadUserByUsername(email)).thenReturn(userDetails);
        when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(true);

        // when
        Authentication result = customAuthenticationManager.authenticate(authentication);

        // then
        assertAll(
            () -> assertThat(result).isNotNull(),
            () -> assertThat(result).extracting("principal").isEqualTo(userDetails.getUsername())
        );
    }


    @Test
    @DisplayName("authenticate메소드에서 비밀번호가 틀린 경우")
    void authenticateTestFail() {
        // given
        final String email = "jongha@naver.com";
        final String password = "1234";
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            email, password
        );
        final String wrongPassword = "12345";
        final UserDetails userDetails = User.builder()
            .username(email)
            .password(wrongPassword)
            .build();
        when(userService.loadUserByUsername(email)).thenReturn(userDetails);
        when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(false);

        // when && then
        assertThatCode(() -> customAuthenticationManager.authenticate(authentication))
            .isInstanceOf(Exception.class)
            .hasMessageContaining("Wrong password");
    }
}
