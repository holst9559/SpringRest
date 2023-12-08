package com.example.springrest.Exception;

public class ResourceNotFoundException extends RuntimeException{
    private final String id;
    public ResourceNotFoundException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
