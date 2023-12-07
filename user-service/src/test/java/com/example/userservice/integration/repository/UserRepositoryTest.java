package com.example.userservice.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.userservice.domain.UserEntity;
import com.example.userservice.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@DisplayName("UserRepository 클래스")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findByUserId 메소드는 유저 아이디로 유저 정보를 조회한다.")
    @Sql(scripts = {"/user.sql"})
    void findByUserId() {
        // given
        final String userId = "aaa";

        // when
        final Optional<UserEntity> id = userRepository.findByUserId(userId);

        // then
        assertThat(id).isNotEmpty();
    }

    @Test
    @DisplayName("findAll 메소드는 모든 유저 정보를 조회한다.")
    @Sql(scripts = {"/user.sql"})
    void findAll() {
        // given

        // when
        final List<UserEntity> users = userRepository.findAll();

        // then
        assertThat(users).isNotEmpty();
    }

    @Test
    @DisplayName("findByEmail 메소드는 이메일로 유저 정보를 조회한다.")
    @Sql(scripts = {"/user.sql"})
    void findByEmail() {
        // given
        final String email = "aaa@naver.com";

        // when
        final Optional<UserEntity> user = userRepository.findByEmail(email);

        // then
        assertThat(user).isNotEmpty();
    }
}
