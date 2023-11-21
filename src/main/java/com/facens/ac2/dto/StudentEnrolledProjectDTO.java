package com.facens.ac2.dto;

import java.util.List;

public class StudentEnrolledProjectDTO {
    private StudentDTO student;
    private List<EnrolledProjectDTO> projects;

    public StudentEnrolledProjectDTO() {
    }

    public StudentEnrolledProjectDTO(StudentDTO student, List<EnrolledProjectDTO> projects) {
        this.student = student;
        this.projects = projects;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public List<EnrolledProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<EnrolledProjectDTO> projects) {
        this.projects = projects;
    }
}
