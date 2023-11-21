package com.facens.ac2.app.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateStudentRequest(
        @NotBlank String name,
        @NotBlank @Email String email
) {
}
