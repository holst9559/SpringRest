package com.example.springrest.Exception;

public class ResourceAlreadyExistException extends RuntimeException{
    private final String id;

    public ResourceAlreadyExistException(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
