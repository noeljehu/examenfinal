package com.example.Examenfinal.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ApiKeyFilter apiKeyFilter;

    // Constructor para inyectar el filtro personalizado
    public SecurityConfig(ApiKeyFilter apiKeyFilter) {
        this.apiKeyFilter = apiKeyFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/countries/**").permitAll() // Permite el acceso sin autenticación para este endpoint
                        .anyRequest().permitAll() // Permite el acceso a cualquier otra solicitud
                )
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class); // Inserta ApiKeyFilter antes del filtro de autenticación

        return http.build();
    }
}