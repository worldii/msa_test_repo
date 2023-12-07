package com.example.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserDetailsService userDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication)
        throws AuthenticationException {
        final UserDetails userDetails = userDetailService.loadUserByUsername(
            authentication.getName());

        if (!passwordEncoder.matches(authentication.getCredentials().toString(),
            userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        return getUsernamePasswordAuthenticationToken(userDetails);
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(
        final UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
            userDetails.getUsername(),
            userDetails.getPassword(),
            userDetails.getAuthorities()
        );
    }
}
