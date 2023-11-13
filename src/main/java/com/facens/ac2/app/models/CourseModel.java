package com.facens.ac2.app.models;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "TableCourse")
public class CourseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "TableStudentCourse",
            joinColumns = @JoinColumn(name = "TableCourse_id"),
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

    public Set<StudentModel> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentModel> students) {
        this.students = students;
    }
}
