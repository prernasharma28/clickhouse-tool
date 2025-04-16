package com.example.dataingestion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .requestMatchers("/api/clickhouse/**").authenticated()  // Protect API paths
                .anyRequest().permitAll()  // Allow other paths without authentication
            .and()
            .httpBasic();  // Use Basic Authentication

        return http.build();
    }
}
