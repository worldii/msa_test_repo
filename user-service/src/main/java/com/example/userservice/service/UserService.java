package com.example.userservice.service;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.model.UserEntity;
import com.example.userservice.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long createUser(final RequestUser user) {
        final String newUserId = UUID.randomUUID().toString();
        final UserDto userDto = getUserDto(user, newUserId);

        final UserEntity userEntity = UserEntity.toEntity(userDto);
        return userRepository.save(userEntity).getId();
    }

    private UserDto getUserDto(final RequestUser user, final String newUserId) {
        return UserDto.builder()
            .email(user.getEmail())
            .name(user.getName())
            .pwd(user.getPwd())
            .userId(newUserId)
            .encryptedPwd("encryptedPwd")
            .build();
    }
}
