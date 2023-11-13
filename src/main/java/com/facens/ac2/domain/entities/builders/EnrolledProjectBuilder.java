package com.facens.ac2.domain.entities.builders;

import com.facens.ac2.domain.entities.EnrolledProject;
import com.facens.ac2.domain.entities.EnrollmentStatus;
import com.facens.ac2.domain.entities.Project;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.builders.exceptions.EnrolledProjectException;

import java.time.LocalDate;

public class EnrolledProjectBuilder {
    private Student student;
    private Project project;
    private EnrollmentStatus status;
    private LocalDate startDate;
    private LocalDate finishDate;

    public EnrolledProjectBuilder withStudent(Student student) {
        this.student = student;
        return this;
    }

    public EnrolledProjectBuilder withProject(Project project) {
        this.project = project;
        return this;
    }

    public EnrolledProjectBuilder withStatus(EnrollmentStatus status) {
        this.status = status;
        return this;
    }

    public EnrolledProjectBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public EnrolledProjectBuilder withFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public EnrolledProject build() throws EnrolledProjectException {
        if (student == null) {
            throw new EnrolledProjectException("Cannot create an Enrolled Project without a Student!");
        }

        if (project == null) {
            throw new EnrolledProjectException("Cannot create an Enrolled Project without a Project!");
        }

        if (status == null) {
            status = EnrollmentStatus.ACTIVE;
        }

        if (startDate == null) {
            startDate = LocalDate.now();
        }

        return new EnrolledProject(
                this.student,
                this.project,
                this.status,
                this.startDate,
                this.finishDate
        );
    }
}
