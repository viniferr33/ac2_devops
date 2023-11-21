package com.facens.ac2.domain.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "TableCourse")
public final class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime createdAt;

    public String name;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Course(UUID id, LocalDateTime createdAt, String name) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
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
