package com.example.springrest.exception;

public class ResourceNotFoundException extends RuntimeException{
    private final String id;
    public ResourceNotFoundException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
