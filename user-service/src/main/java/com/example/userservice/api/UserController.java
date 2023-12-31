package com.example.userservice.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.userservice.domain.dto.CreateUserRequest;
import com.example.userservice.domain.dto.Greeting;
import com.example.userservice.domain.dto.UserOrderResponse;
import com.example.userservice.domain.dto.UserResponse;
import com.example.userservice.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final Greeting greeting;
    private final UserService userService;
    private final Environment env;

    @GetMapping("/")
    public String loginDefaultSuccess() {
        return "login success";
    }

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service" +
            ", port(local.server.port)=" + env.getProperty("local.server.port")
            + ", port(server.port)=" + env.getProperty("server.port")
            + ", token secret=" + env.getProperty("token.secret")
            + ", token expiration time=" + env.getProperty("token.expiration_time");
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(final @RequestBody CreateUserRequest user) {
        return new ResponseEntity<>(userService.createUser(user), CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserOrderResponse> getUserByUserId(final @PathVariable String userId) {
        return new ResponseEntity<>(userService.getUserByUserId(userId), OK);
    }
}
