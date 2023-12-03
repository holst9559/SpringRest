package com.example.springrest.place;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record PlaceDto(
        @NotBlank
        Long id,
        @NotBlank
        String name,
        Boolean visible,
        @NotBlank
        Float lat,
        @NotBlank
        Float lon,
        @NotBlank
        String category,
        String description
) implements Serializable {
}
