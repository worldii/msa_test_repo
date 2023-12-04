package com.example.userservice.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.example.userservice.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class UserOrderResponse {

    private String email;
    private String name;
    private String userId;
    private List<OrderResponse> orders;

    public static UserOrderResponse toUserOrderResponse(
        final UserEntity userEntity,
        final List<OrderResponse> orders
    ) {
        return new UserOrderResponse(
            userEntity.getEmail(),
            userEntity.getName(),
            userEntity.getUserId(),
            orders
        );
    }
}
