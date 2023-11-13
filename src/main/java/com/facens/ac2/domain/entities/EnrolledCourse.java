package com.facens.ac2.domain.entities;

import java.time.LocalDate;

public record EnrolledCourse(
        Student student,
        Course course,
        EnrollmentStatus status,
        LocalDate startDate,
        LocalDate finishDate,
        double finalGrade
) {
}

