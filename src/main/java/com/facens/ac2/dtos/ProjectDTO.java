package com.facens.ac2.dtos;

import java.util.UUID;

public record ProjectDTO(
        UUID id,
        String name,
        String category
) {
}
