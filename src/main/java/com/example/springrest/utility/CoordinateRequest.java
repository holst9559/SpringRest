package com.example.springrest.utility;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CoordinateRequest(
        @NotNull
        float lon,
        @NotNull
        float lat,

        @NotNull
        double radius
        ) {
}
