package com.hanghae.settlement_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/batch/settlement").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable); // 필요한 경우 CSRF 비활성화

        return http.build();
    }
}
