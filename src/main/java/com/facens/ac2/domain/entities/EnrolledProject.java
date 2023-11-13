package com.facens.ac2.domain.entities;

import java.time.LocalDate;

public record EnrolledProject(
        Student student,
        Project project,
        EnrollmentStatus status,
        LocalDate startDate,
        LocalDate finishDate
) {
}
