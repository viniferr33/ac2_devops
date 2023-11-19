package com.facens.ac2.domain.entities.builders;

import com.facens.ac2.domain.entities.Course;
import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.EnrollmentStatus;
import com.facens.ac2.domain.entities.Student;
import com.facens.ac2.domain.entities.exceptions.EnrolledCourseException;

import java.time.LocalDate;

public class EnrolledCourseBuilder {
    private Student student;
    private Course course;
    private EnrollmentStatus status;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Double finalGrade;

    public EnrolledCourseBuilder withStudent(Student student) {
        this.student = student;
        return this;
    }

    public EnrolledCourseBuilder withCourse(Course course) {
        this.course = course;
        return this;
    }

    public EnrolledCourseBuilder withStatus(EnrollmentStatus status) {
        this.status = status;
        return this;
    }

    public EnrolledCourseBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public EnrolledCourseBuilder withFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public EnrolledCourseBuilder withFinalGrade(Double finalGrade) {
        this.finalGrade = finalGrade;
        return this;
    }

    public EnrolledCourse build() throws EnrolledCourseException {
        if (student == null) {
            throw new EnrolledCourseException("Cannot create an Enrolled Course without a Student!");
        }

        if (course == null) {
            throw new EnrolledCourseException("Cannot create an Enrolled Course without a Course!");
        }

        if (status == EnrollmentStatus.COMPLETED && finalGrade == null) {
            throw new EnrolledCourseException("Cannot create an Enrolled Course with STATUS = COMPLETED without Final Grade!");
        }

        if (finalGrade != null) {
            if (finalGrade.isNaN() || (finalGrade < 0.0 || finalGrade > 10)) {
                throw new EnrolledCourseException("Cannot create an Enrolled Course with an invalid Final Grade!");
            }
        }

        if (status == null) {
            status = EnrollmentStatus.ACTIVE;
        }

        if (startDate == null) {
            startDate = LocalDate.now();
        }

        return status != EnrollmentStatus.COMPLETED
                ? new EnrolledCourse(
                this.student,
                this.course,
                this.status
        )
                : new EnrolledCourse(
                this.student,
                this.course,
                this.status,
                this.startDate,
                this.finishDate,
                this.finalGrade
        );
    }
}
