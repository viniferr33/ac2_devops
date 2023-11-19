package com.facens.ac2.domain.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "TableProject")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime createdAt;

    public String name;
    public String category;

    public Project() {
    }

    public Project(
            String name,
            String category
    ) {
        this.name = name;
        this.category = category;
    }

    public Project(UUID id, LocalDateTime createdAt, String name, String category) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.category = category;
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
