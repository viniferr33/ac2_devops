package com.facens.ac2.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateCourseDTO(
        @NotBlank String name
) {
}
