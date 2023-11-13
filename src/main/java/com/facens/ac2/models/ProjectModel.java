package com.facens.ac2.models;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "TableProject")
public class ProjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String category;

    @ManyToMany
    @JoinTable(
            name = "TableStudentProject",
            joinColumns = @JoinColumn(name = "TableProject_id"),
            inverseJoinColumns = @JoinColumn(name = "TableStudent_id")
    )
    private Set<StudentModel> students;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
