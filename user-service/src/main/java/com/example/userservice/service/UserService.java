package com.example.userservice.service;

import com.example.userservice.domain.UserEntity;
import com.example.userservice.domain.dto.CreateUserRequest;
import com.example.userservice.domain.dto.LoginSuccessResponse;
import com.example.userservice.domain.dto.OrderResponse;
import com.example.userservice.domain.dto.UserOrderResponse;
import com.example.userservice.domain.dto.UserResponse;
import com.example.userservice.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(final CreateUserRequest user) {
        final String newUserId = UUID.randomUUID().toString();
        final UserEntity userEntity = getUserDto(user, newUserId);

        UserEntity save = userRepository.save(userEntity);
        return UserResponse.toUserDto(save);
    }

    private UserEntity getUserDto(final CreateUserRequest user, final String newUserId) {
        return UserEntity.builder()
            .email(user.getEmail())
            .name(user.getName())
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

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        return getUserDetails(userEntity);
    }

    private User getUserDetails(final UserEntity userEntity) {
        return new User(
            userEntity.getEmail(),
            userEntity.getEncryptedPwd(),
            true,
            true,
            true,
            true,
            new ArrayList<>()
        );
    }

    public LoginSuccessResponse getUserDetailsByEmail(final String userEmail) {
        final UserEntity userEntity = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException(userEmail));
        return LoginSuccessResponse.toLoginUserSuccessResponse(userEntity);
    }
}
