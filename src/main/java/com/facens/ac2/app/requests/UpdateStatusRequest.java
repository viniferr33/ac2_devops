package com.facens.ac2.app.requests;

public record UpdateStatusRequest(
        String operation,
        Double finalGrade
) {
}
