package com.facens.ac2.app.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateCourseDTO(
        @NotBlank String name
) {
}
