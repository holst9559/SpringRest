package com.example.springrest.exception;

public class AuthorizationException extends IllegalAccessException{
    private final String username;

    public AuthorizationException(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
