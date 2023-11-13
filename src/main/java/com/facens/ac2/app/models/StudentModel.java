package com.facens.ac2.app.models;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "TableStudent")
public class StudentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    @ManyToMany
    private Set<CourseModel> courses;

    @ManyToMany
    private Set<ProjectModel> projects;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Set<CourseModel> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseModel> courses) {
        this.courses = courses;
    }

    public Set<ProjectModel> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectModel> projects) {
        this.projects = projects;
    }
}
