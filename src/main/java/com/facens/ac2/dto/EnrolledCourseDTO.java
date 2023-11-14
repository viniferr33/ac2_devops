package com.facens.ac2.dto;

import com.facens.ac2.domain.entities.EnrolledCourse;
import com.facens.ac2.domain.entities.EnrollmentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EnrolledCourseDTO {
    private long id;
    private StudentDTO student;
    private CourseDTO course;
    private EnrollmentStatus status;
    private LocalDate startDate;
    private LocalDate finishDate;
    private double finalGrade;

    public EnrolledCourseDTO(long id, StudentDTO student, CourseDTO course, EnrollmentStatus status, LocalDate startDate, LocalDate finishDate, double finalGrade) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.status = status;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.finalGrade = finalGrade;
    }

    public static List<EnrolledCourseDTO> fromEntity(List<EnrolledCourse> enrolledCourseList) {
        return enrolledCourseList.stream()
                .map(EnrolledCourseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static EnrolledCourseDTO fromEntity(EnrolledCourse enrolledCourse) {
        return new EnrolledCourseDTO(
                enrolledCourse.id,
                StudentDTO.fromEntity(enrolledCourse.student),
                CourseDTO.fromEntity(enrolledCourse.course),
                enrolledCourse.status,
                enrolledCourse.startDate,
                enrolledCourse.finishDate,
                enrolledCourse.finalGrade
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }
}
