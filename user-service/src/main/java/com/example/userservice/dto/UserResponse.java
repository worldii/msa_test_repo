package com.example.userservice.dto;

import com.example.userservice.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String userId;
    private String encryptedPwd;

    public static UserResponse toUserDto(final UserEntity userEntity) {
        return new UserResponse(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getName(),
                userEntity.getUserId(),
                userEntity.getEncryptedPwd()
        );
    }
}
