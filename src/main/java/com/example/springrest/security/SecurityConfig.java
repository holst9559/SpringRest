package com.example.springrest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(
        jsr250Enabled = true
)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/register").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "api/v1/user/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories").hasRole("ADMIN")
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
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("User")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();
        UserDetails anton = User.builder()
                .username("Anton")
                .password(passwordEncoder().encode("anton"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("Admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin, anton);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
