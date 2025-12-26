package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // CSRF disable (Swagger + APIs ke liye)
            .csrf(csrf -> csrf.disable())

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                // Swagger bhi secure rahega (login required)
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html"
                ).authenticated()

                // Baaki sab endpoints bhi secure
                .anyRequest().authenticated()
            )

            // Default Spring Security login page
            .formLogin(Customizer.withDefaults())

            // Basic auth (Swagger support ke liye)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
