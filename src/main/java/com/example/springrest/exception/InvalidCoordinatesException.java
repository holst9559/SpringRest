package com.example.springrest.exception;

public class InvalidCoordinatesException extends IllegalArgumentException{
    private final Float lat;
    private final Float lon;

    public InvalidCoordinatesException(Float lat, Float lon){
        this.lat = lat;
        this.lon = lon;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
    }
}
