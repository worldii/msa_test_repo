package com.example.userservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final Environment environment;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .headers(header -> header
                .frameOptions(FrameOptionsConfig::disable)
            )
            .authorizeHttpRequests(
                authorizeRequest -> authorizeRequest
                    .requestMatchers("/**").permitAll()
                    .requestMatchers(PathRequest.toH2Console()).permitAll()
            )
            .addFilter(getAuthenticationFilter())
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    private AuthenticationFilter getAuthenticationFilter() {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(customAuthenticationManager);
        authenticationFilter.setEnvironment(environment);

        return authenticationFilter;
    }
}
