package com.example.springrest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();;
        return http.httpBasic(Customizer.withDefaults()).
                authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/places").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/places").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/places/*").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/places/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/*").authenticated()
                        .anyRequest().denyAll())
                .build();
    }


}
