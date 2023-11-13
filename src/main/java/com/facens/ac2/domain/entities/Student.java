package com.facens.ac2.domain.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "TableStudent")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime createdAt;

    @LastModifiedDate
    public LocalDateTime updatedAt;

    public String name;
    public String email;
    @ManyToMany
    public List<EnrolledProject> projects;
    @ManyToMany
    public List<EnrolledCourse> courses;

    public Student(
            String name,
            String email,
            List<EnrolledProject> projects,
            List<EnrolledCourse> courses
    ) {
        this.name = name;
        this.email = email;
        this.projects = projects;
        this.courses = courses;
    }

    public Student(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String email, List<EnrolledProject> projects, List<EnrolledCourse> courses) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.email = email;
        this.projects = projects;
        this.courses = courses;
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

    public List<EnrolledProject> getProjects() {
        return projects;
    }

    public void setProjects(List<EnrolledProject> projects) {
        this.projects = projects;
    }

    public List<EnrolledCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<EnrolledCourse> courses) {
        this.courses = courses;
    }
}
