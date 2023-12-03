package com.example.springrest.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String role;
}
