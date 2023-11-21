package com.facens.ac2.domain.entities.builders;

import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.EnrolledProject;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.exceptions.StudentException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentBuilder {
    private UUID id;
    private String name;
    private String email;
    private List<EnrolledProject> projects;
    private List<EnrolledCourse> courses;

    public StudentBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public StudentBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public StudentBuilder withProjects(List<EnrolledProject> projects) {
        this.projects = projects;
        return this;
    }

    public StudentBuilder withCourses(List<EnrolledCourse> courses) {
        this.courses = courses;
        return this;
    }

    public Student build() throws StudentException {
        if (name == null) {
            throw new StudentException("Student Name cannot be null!");
        }

        if (email == null) {
            throw new StudentException("Student Email cannot be null!");
        }

        if (projects == null) {
            projects = new ArrayList<>();
        }

        if (courses == null) {
            courses = new ArrayList<>();
        }

        return new Student(
                this.id,
                this.name,
                this.email,
                this.projects,
                this.courses
        );
    }
}
