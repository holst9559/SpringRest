package com.example.springrest.place;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record PlaceDto(
        @NotBlank
        String name,
        Boolean visible,
        @NotNull
        Float lat,
        @NotNull
        Float lon,
        @NotBlank
        String category,
        String description
) implements Serializable {
}
