package com.example.springrest.auth;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String role;
}
