package com.example.springrest.place;

import java.io.Serializable;

public record PlaceRequest(
        String name,
        Boolean visible,
        Float lat,
        Float lon,
        String category,
        String description
) implements Serializable {
}
