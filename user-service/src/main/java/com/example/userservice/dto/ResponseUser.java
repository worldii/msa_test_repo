package com.example.userservice.dto;

import com.example.userservice.model.UserEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUser {

    private Long id;
    private String email;
    private String name;
    private String userId;
    private String encryptedPwd;

    public static ResponseUser toUserDto(final UserEntity userEntity) {
        return new ResponseUser(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getName(),
                userEntity.getUserId(),
                userEntity.getEncryptedPwd()
        );
    }
}
