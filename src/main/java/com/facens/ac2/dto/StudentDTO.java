package com.facens.ac2.dto;

import com.facens.ac2.domain.entities.Student;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StudentDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String email;
    private List<EnrolledCourseDTO> courses;
    private List<EnrolledProjectDTO> projects;

    public StudentDTO(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String email, List<EnrolledCourseDTO> courses, List<EnrolledProjectDTO> projects) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.email = email;
        this.courses = courses;
        this.projects = projects;
    }

    public StudentDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public StudentDTO() {

    }

    public static List<StudentDTO> fromEntity(List<Student> studentList) {
        return studentList.stream()
                .map(StudentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static StudentDTO fromEntity(Student student) {
        return new StudentDTO(
                student.id,
                student.createdAt,
                student.updatedAt,
                student.name,
                student.email,
                EnrolledCourseDTO.fromEntity(student.courses),
                EnrolledProjectDTO.fromEntity(student.projects)
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<EnrolledCourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<EnrolledCourseDTO> courses) {
        this.courses = courses;
    }

    public List<EnrolledProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<EnrolledProjectDTO> projects) {
        this.projects = projects;
    }
}
