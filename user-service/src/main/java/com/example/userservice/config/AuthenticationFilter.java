package com.example.userservice.config;

import com.example.userservice.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

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

            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
        final HttpServletRequest request, final HttpServletResponse response,
        final FilterChain chain, final Authentication authResult
    )
        throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
