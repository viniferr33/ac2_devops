package com.facens.ac2.dto;

import com.facens.ac2.domain.entities.Project;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProjectDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private String name;
    private String category;

    public ProjectDTO(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public ProjectDTO(UUID id, LocalDateTime createdAt, String name, String category) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.category = category;
    }

    public ProjectDTO() {

    }

    public static List<ProjectDTO> fromEntity(List<Project> projectList) {
        return projectList.stream()
                .map(ProjectDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static ProjectDTO fromEntity(Project project) {
        return new ProjectDTO(
                project.id,
                project.createdAt,
                project.name,
                project.category
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
