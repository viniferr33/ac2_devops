package com.facens.ac2.dto;

import com.facens.ac2.domain.entities.EnrolledProject;
import com.facens.ac2.domain.entities.EnrollmentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EnrolledProjectDTO {
    private long id;
    private StudentDTO student;
    private ProjectDTO project;
    private EnrollmentStatus status;
    private LocalDate startDate;
    private LocalDate finishDate;

    public EnrolledProjectDTO(long id, StudentDTO student, ProjectDTO project, EnrollmentStatus status, LocalDate startDate, LocalDate finishDate) {
        this.id = id;
        this.student = student;
        this.project = project;
        this.status = status;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public static List<EnrolledProjectDTO> fromEntity(List<EnrolledProject> enrolledProjectList) {
        return enrolledProjectList.stream()
                .map(EnrolledProjectDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static EnrolledProjectDTO fromEntity(EnrolledProject enrolledProject) {
        return new EnrolledProjectDTO(
                enrolledProject.id,
                StudentDTO.fromEntity(enrolledProject.student),
                ProjectDTO.fromEntity(enrolledProject.project),
                enrolledProject.status,
                enrolledProject.startDate,
                enrolledProject.finishDate
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

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
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
