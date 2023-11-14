package com.facens.ac2.dto;

import com.facens.ac2.domain.entities.Course;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CourseDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private String name;

    public CourseDTO(String name) {
        this.name = name;
    }

    public CourseDTO(UUID id, LocalDateTime createdAt, String name) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
    }

    public static List<CourseDTO> fromEntity(List<Course> courseList) {
        return courseList.stream()
                .map(CourseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static CourseDTO fromEntity(Course course) {
        return new CourseDTO(
                course.id,
                course.createdAt,
                course.name
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
}
