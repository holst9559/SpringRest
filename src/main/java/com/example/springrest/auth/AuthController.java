package com.example.springrest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authenticationService;

    @Autowired
    public AuthController(AuthService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated  RegisterDto registerDto){
        authenticationService.register(registerDto);
        return new ResponseEntity<>("User created successfully!", HttpStatus.OK);
    }

}
