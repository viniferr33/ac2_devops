package com.facens.ac2.dto;

import com.facens.ac2.domain.entities.Student;

import java.util.List;

public class StudentEnrolledCourseDTO {
    private StudentDTO student;
    private List<EnrolledCourseDTO> courses;

    public StudentEnrolledCourseDTO() {
    }

    public StudentEnrolledCourseDTO(StudentDTO student, List<EnrolledCourseDTO> courses) {
        this.student = student;
        this.courses = courses;
    }

    public static StudentEnrolledCourseDTO fromEntity(Student student) {
        return new StudentEnrolledCourseDTO(
                StudentDTO.fromEntity(student),
                EnrolledCourseDTO.fromEntity(student.getCourses())
        );
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public List<EnrolledCourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<EnrolledCourseDTO> courses) {
        this.courses = courses;
    }
}
