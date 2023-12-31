package com.facens.ac2.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "TableEnrolledCourse")
public class EnrolledCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToOne
    @JoinColumn(name = "StudentId")
    public Student student;

    @ManyToOne
    @JoinColumn(name = "CourseId")
    public Course course;
    @Enumerated(EnumType.STRING)
    public EnrollmentStatus status;
    public LocalDate startDate;
    public LocalDate finishDate;
    public double finalGrade;

    public EnrolledCourse() {
    }

    public EnrolledCourse(Student student, Course course, EnrollmentStatus status, LocalDate startDate, LocalDate finishDate, double finalGrade) {
        this.student = student;
        this.course = course;
        this.status = status;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.finalGrade = finalGrade;
    }

    public EnrolledCourse(Student student, Course course, EnrollmentStatus status) {
        this.student = student;
        this.course = course;
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
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

