package com.facens.ac2.domain.entities;

import java.util.List;

public record Student(
        String name,
        String email,
        List<EnrolledProject> projects,
        List<EnrolledCourse> courses
) {
}
