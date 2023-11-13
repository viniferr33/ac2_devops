package com.facens.ac2.app.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateStudentDTO(
        @NotBlank String name,
        @NotBlank @Email String email
) {
}
