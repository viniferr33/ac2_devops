package com.facens.ac2.app.dtos;

import java.util.UUID;

public record ProjectDTO(
        UUID id,
        String name,
        String category
) {
}
