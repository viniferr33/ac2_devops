package com.facens.ac2.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectDTO(
        @NotBlank String name,
        @NotBlank String category
) {
}
