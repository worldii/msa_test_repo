package com.example.userservice.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.userservice.config.PasswordConfig;
import com.example.userservice.dto.UserOrderResponse;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@DisplayName("User Service Integration Tests")
@Import({UserService.class, PasswordConfig.class})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("user를 정상적으로 생성하는 테스트")
    void createUserTestWithSuccess() {
        // given
        final UserRequest userRequest = UserRequest.builder()
            .email("aaa@naver.com")
            .name("aaa")
            .pwd("1234")
            .build();
        
        // when
        final UserResponse user = userService.createUser(userRequest);
        // then
        assertThat(user).extracting("email").isEqualTo(userRequest.getEmail());
    }

    @Test
    @DisplayName("users를 정상적으로 조회하는 테스트")
    @Sql(scripts = {"/user.sql"})
    void getUserByUserIdTestWithSuccess() {
        // given
        // when
        final List<UserResponse> allUsers = userService.getAllUsers();
        // then
        assertThat(allUsers).hasSize(3);
    }

    @Test
    @DisplayName("user를 정상적으로 조회하는 테스트")
    @Sql(scripts = {"/user.sql"})
    void getAllUsersTestWithSuccess() {
        // given
        final String USER_ID = "aaa";
        // when
        final UserOrderResponse userByUserId = userService.getUserByUserId(USER_ID);
        // then
        assertThat(userByUserId).extracting("userId").isEqualTo(USER_ID);
    }
}
