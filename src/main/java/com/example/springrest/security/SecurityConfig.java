package com.example.springrest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .sessionManagement(ma -> ma.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/places").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/places/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/places").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/places/*").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/places/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/*").authenticated()
                        .anyRequest().denyAll())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder
                .withJwkSetUri("https://fungover.org/auth/.well-known/jwks.json")
                .jwsAlgorithm(SignatureAlgorithm.ES256)
                .build();
    }




}
