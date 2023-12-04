package com.example.userservice.service;

import com.example.userservice.dto.OrderResponse;
import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserOrderResponse;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.model.UserEntity;
import com.example.userservice.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(final UserRequest user) {
        final String newUserId = UUID.randomUUID().toString();
        final UserDto userDto = getUserDto(user, newUserId);

        final UserEntity userEntity = UserEntity.toEntity(userDto);

        UserEntity save = userRepository.save(userEntity);
        return UserResponse.toUserDto(save);
    }

    private UserDto getUserDto(final UserRequest user, final String newUserId) {
        return UserDto.builder()
            .email(user.getEmail())
            .name(user.getName())
            .pwd(user.getPwd())
            .userId(newUserId)
            .encryptedPwd(passwordEncoder.encode(user.getPwd()))
            .build();
    }

    public UserOrderResponse getUserByUserId(final String userId) {
        final UserEntity userEntity = userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        final List<OrderResponse> orders = new ArrayList<>();
        return UserOrderResponse.toUserOrderResponse(userEntity, orders);
    }

    public List<UserResponse> getAllUsers() {
        final List<UserEntity> users = userRepository.findAll();

        return users.stream()
            .map(UserResponse::toUserDto).
            collect(Collectors.toList());
    }

}
