package com.facens.ac2.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "TableEnrolledProject")
public class EnrolledProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToOne
    @JoinColumn(name = "StudentId")
    public Student student;

    @ManyToOne
    @JoinColumn(name = "ProjectId")
    public Project project;
    @Enumerated(EnumType.STRING)
    public EnrollmentStatus status;
    public LocalDate startDate;
    public LocalDate finishDate;

    public EnrolledProject() {
    }

    public EnrolledProject(
            Student student,
            Project project,
            EnrollmentStatus status,
            LocalDate startDate,
            LocalDate finishDate
    ) {
        this.student = student;
        this.project = project;
        this.status = status;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
}
