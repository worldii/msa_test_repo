package com.example.userservice.config;

import com.example.userservice.domain.dto.LoginRequest;
import com.example.userservice.domain.dto.LoginSuccessResponse;
import com.example.userservice.service.TokenService;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(
        final HttpServletRequest request, final HttpServletResponse response
    ) throws AuthenticationException {
        try {
            final LoginRequest loginRequest = new ObjectMapper().readValue(
                request.getInputStream(), LoginRequest.class);
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            );
            return authenticationManager.authenticate(authentication);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed");
        } catch (IOException e) {
            throw new RuntimeException("IO exception");
        } catch (Exception e) {
            throw new RuntimeException("Exception");
        }
    }

    @Override
    protected void successfulAuthentication(
        final HttpServletRequest request, final HttpServletResponse response,
        final FilterChain chain, final Authentication authResult
    )
        throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        final String userEmail = authResult.getName();
        final LoginSuccessResponse loginInfo = userService.getUserDetailsByEmail(userEmail);
        final String token = tokenService.generateToken(loginInfo.getUserId());
        log.info("token: {}", token);
        addTokenToHeader(response, loginInfo.getUserId(), token);
    }

    private void addTokenToHeader(
        final HttpServletResponse response, final String userId, final String token
    ) {
        response.addHeader("token", token);
        response.addHeader("userId", userId);
    }
}
