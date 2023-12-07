package com.example.userservice.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.userservice.config.PasswordConfig;
import com.example.userservice.domain.dto.CreateUserRequest;
import com.example.userservice.domain.dto.UserOrderResponse;
import com.example.userservice.domain.dto.UserResponse;
import com.example.userservice.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
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
        final CreateUserRequest createUserRequest = CreateUserRequest.builder()
            .email("aaa@naver.com")
            .name("aaa")
            .pwd("1234")
            .build();

        // when
        final UserResponse user = userService.createUser(createUserRequest);
        // then
        assertThat(user).extracting("email").isEqualTo(createUserRequest.getEmail());
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
        assertAll(
            () -> assertThat(userByUserId).extracting("email")
                .isEqualTo(userByUserId.getEmail()),
            () -> assertThat(userByUserId).extracting("name")
                .isEqualTo(userByUserId.getName()),
            () -> assertThat(userByUserId).extracting("orders")
                .isEqualTo(userByUserId.getOrders())
        );
    }
}
