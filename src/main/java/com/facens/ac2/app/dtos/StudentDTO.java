package com.facens.ac2.app.dtos;

import java.util.UUID;

public record StudentDTO(
        UUID id,
        String name,
        String email
) {
}
