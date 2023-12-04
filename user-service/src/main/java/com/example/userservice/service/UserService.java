package com.example.userservice.service;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.ResponseUser;
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
    public ResponseUser createUser(final RequestUser user) {
        final String newUserId = UUID.randomUUID().toString();
        final UserDto userDto = getUserDto(user, newUserId);

        final UserEntity userEntity = UserEntity.toEntity(userDto);

        UserEntity save = userRepository.save(userEntity);
        return ResponseUser.toUserDto(save);
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
