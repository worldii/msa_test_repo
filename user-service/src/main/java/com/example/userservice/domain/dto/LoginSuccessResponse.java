package com.example.userservice.domain.dto;

import com.example.userservice.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccessResponse {

    private String email;
    private String name;
    private String userId;
    private String encryptedPwd;

    public static LoginSuccessResponse toLoginUserSuccessResponse(final UserEntity userEntity) {
        return new LoginSuccessResponse(
            userEntity.getEmail(),
            userEntity.getName(),
            userEntity.getUserId(),
            userEntity.getEncryptedPwd()
        );
    }
}
