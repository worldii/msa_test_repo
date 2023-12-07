package com.example.userservice.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import com.example.userservice.domain.UserEntity;
import com.example.userservice.domain.dto.LoginSuccessResponse;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class UserServiceMockTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("getUserDetailsByEmail 메소드는 이메일로 유저 정보를 조회한다.")
    void getUserDetailsByEmailTest() {
        // given
        final String email = "jongha";
        final UserEntity userEntity = UserEntity.builder()
            .email(email)
            .userId("jongha")
            .name("jongha")
            .encryptedPwd("1234")
            .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // when
        LoginSuccessResponse userDetailsByEmail = userService.getUserDetailsByEmail(email);

        // then
        assertAll(
            () -> assertThat(userDetailsByEmail).extracting("email").isEqualTo(email),
            () -> assertThat(userDetailsByEmail).extracting("name").isEqualTo("jongha"),
            () -> assertThat(userDetailsByEmail).extracting("userId").isEqualTo("jongha"),
            () -> assertThat(userDetailsByEmail).extracting("encryptedPwd").isEqualTo("1234")
        );
    }


    @Test
    @DisplayName("loadUserByUsername 메소드는 유저 정보를 조회한다.")
    void loadUserByUsernameTest() {
        // given
        final String email = "jongha";
        final UserEntity userEntity = UserEntity.builder()
            .email(email)
            .userId("jongha")
            .name("jongha")
            .encryptedPwd("1234")
            .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        // when
        final UserDetails userDetails = userService.loadUserByUsername(email);

        // then
        assertAll(
            () -> assertThat(userDetails).extracting("username").isEqualTo(email),
            () -> assertThat(userDetails).extracting("password").isEqualTo("1234")
        );
    }
}
